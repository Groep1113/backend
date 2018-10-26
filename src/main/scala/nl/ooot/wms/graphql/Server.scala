package nl.ooot.wms.graphql

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._
import io.circe._
import io.circe.optics.JsonPath._
import io.circe.parser._
import nl.ooot.wms.graphql.GraphQLRequestUnmarshaller._
import sangria.ast.Document
import sangria.execution.deferred.DeferredResolver
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.marshalling.circe._
import sangria.parser.DeliveryScheme.Try
import sangria.parser.{QueryParser, SyntaxError}

import scala.io.StdIn
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

object Server extends CorsSupport {
  def listen() = {
    implicit val system: ActorSystem = ActorSystem("sangria-server")
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    import system.dispatcher

    def executeGraphQL(query: Document, operationName: Option[String], variables: Json) =
      complete(Executor.execute(SchemaDefinition.StarWarsSchema, query, new CharacterRepo,
        variables = if (variables.isNull) Json.obj() else variables,
        operationName = operationName,
        deferredResolver = DeferredResolver.fetchers(SchemaDefinition.characters))
        .map(OK → _)
        .recover {
          case error: QueryAnalysisError ⇒ BadRequest → error.resolveError
          case error: ErrorWithResolver ⇒ InternalServerError → error.resolveError
        })

    def formatError[T](error: T) = error match {
      case _: String => formatErrorString(error.asInstanceOf[String])
      case _: Throwable => formatErrorThrowable(error.asInstanceOf[Throwable])
    }

    def formatErrorThrowable(error: Throwable): Json = error match {
      case syntaxError: SyntaxError ⇒
        Json.obj("errors" → Json.arr(
          Json.obj(
            "message" → Json.fromString(syntaxError.getMessage),
            "locations" → Json.arr(Json.obj(
              "line" → Json.fromBigInt(syntaxError.originalError.position.line),
              "column" → Json.fromBigInt(syntaxError.originalError.position.column))))))
      case NonFatal(e) ⇒
        formatError(e.getMessage)
      case e ⇒
        throw e
    }

    def formatErrorString(message: String): Json =
      Json.obj("errors" → Json.arr(Json.obj("message" → Json.fromString(message))))

    val route: Route =
      path("graphql") {
        get {
          getFromResource("assets/graphiql.html")
        } ~
          post {
            parameters('query.?, 'operationName.?, 'variables.?) { (queryParam, operationNameParam, variablesParam) ⇒
              entity(as[Json]) { body ⇒
                val query = queryParam orElse root.query.string.getOption(body)
                val operationName = operationNameParam orElse root.operationName.string.getOption(body)
                val variablesStr = variablesParam orElse root.variables.string.getOption(body)

                query.map(QueryParser.parse(_)) match {
                  case Some(Success(ast)) ⇒
                    variablesStr.map(parse) match {
                      case Some(Left(error)) ⇒ complete(BadRequest, formatError(error))
                      case Some(Right(json)) ⇒ executeGraphQL(ast, operationName, json)
                      case None ⇒ executeGraphQL(ast, operationName, root.variables.json.getOption(body) getOrElse Json.obj())
                    }
                  case Some(Failure(error)) ⇒ complete(BadRequest, formatError(error))
                  case None ⇒ complete(BadRequest, formatError("No query to execute"))
                }
              } ~
                entity(as[Document]) { document ⇒
                  variablesParam.map(parse) match {
                    case Some(Left(error)) ⇒ complete(BadRequest, formatError(error))
                    case Some(Right(json)) ⇒ executeGraphQL(document, operationNameParam, json)
                    case None ⇒ executeGraphQL(document, operationNameParam, Json.obj())
                  }
                }
            }
          }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println("Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
    //    Http().bindAndHandle(corsHandler(route), "0.0.0.0", sys.props.get("http.port").fold(8080)(_.toInt))
  }
}