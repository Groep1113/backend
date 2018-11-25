package nl.ooot.wms.graphql

import akka.actor.ActorSystem
import controllers.AssetsFinder
import javax.inject.Inject
import nl.ooot.wms.auth.BearerAuth
import nl.ooot.wms.graphql.ErrorHandling.AuthorizationException
import nl.ooot.wms.models.User
import play.api.Configuration
import play.api.http.HttpErrorHandler
import play.api.libs.json._
import play.api.libs.streams.Accumulator
import play.api.mvc._
import sangria.execution._
import sangria.marshalling.ResultMarshaller
import sangria.marshalling.playJson._
import sangria.parser.{QueryParser, SyntaxError}
import sangria.renderer.SchemaRenderer
import sangria.slowlog.SlowLog

import scala.concurrent.Future
import scala.util.{Failure, Success}

class GraphQLController @Inject()(
                                   system: ActorSystem,
                                   config: Configuration,
                                   errorHandler: HttpErrorHandler
                                 )(implicit assetsFinder: AssetsFinder)
  extends InjectedController {

  import system.dispatcher

  val defaultGraphQLUrl: String = config.getOptional[String]("defaultGraphQLUrl")
    .getOrElse(s"http://localhost:${config.getOptional[Int]("http.port").getOrElse(9000)}/graphql")

  def playground = Action {
    Ok(nl.ooot.wms.views.html.playground(assetsFinder))
  }

  def graphql(query: String, variables: Option[String], operation: Option[String]): Action[AnyContent] = Action.async { request =>
    val token = BearerAuth.authenticateFromHeader(request)
    executeQuery(query, variables map parseVariables, operation, isTracingEnabled(request), token)
  }

  def graphqlBody: Action[JsValue] = Action.async(parse.json) { request =>
    val query = (request.body \ "query").as[String]
    val operation = (request.body \ "operationName").asOpt[String]

    val variables = (request.body \ "variables").toOption.flatMap {
      case JsString(vars) => Some(parseVariables(vars))
      case obj: JsObject => Some(obj)
      case _ => None
    }

    val token = BearerAuth.authenticateFromHeader(request);
    executeQuery(query, variables, operation, isTracingEnabled(request), token)
  }

  //  private def authCheck(query: String, variables: Option[JsObject], operation: Option[String], tracing: Boolean, request: Request[Any]) = {
  //    val token = BearerAuth.authenticateFromHeader(request)
  //    val pattern = raw"""login\(.+\)"""".r
  //    if (
  //      operation.get != "login"
  //        && pattern.findFirstIn(query).isEmpty
  //        && token != null
  //    ) {
  //      Accumulator.done(errorHandler.onClientError(request, 401, s"Login required: ${request.host}"))
  //    }
  //
  //    executeQuery(query, variables, operation, tracing, token)
  //  }

  private def parseVariables(variables: String) =
    if (variables.trim == "" || variables.trim == "null") Json.obj() else Json.parse(variables).as[JsObject]

  private def executeQuery(query: String, variables: Option[JsObject], operation: Option[String], tracing: Boolean, token: String) =
    QueryParser.parse(query) match {

      // query parsed successfully, time to execute it!
      case Success(queryAst) => {
        Executor.execute(SchemaDefinition.schema, queryAst,
          userContext = SecureContext(token),
          operationName = operation,
          variables = variables getOrElse Json.obj(),
          exceptionHandler = ErrorHandling.errorHandler,
          middleware = if (tracing) SlowLog.apolloTracing :: Nil else Nil)
          .map(Ok(_))
          .recover {
            case error: QueryAnalysisError => BadRequest(error.resolveError)
            case error: ErrorWithResolver => InternalServerError(error.resolveError)
          }
      }

      // can't parse GraphQL query, return error
      case Failure(error: SyntaxError) =>
        Future.successful(BadRequest(Json.obj(
          "syntaxError" → error.getMessage,
          "locations" → Json.arr(Json.obj(
            "line" → error.originalError.position.line,
            "column" → error.originalError.position.column)))))

      case Failure(error) =>
        throw error
    }

  def isTracingEnabled(request: Request[_]): Boolean = request.headers.get("X-Apollo-Tracing").isDefined

  def renderSchema = Action {
    Ok(SchemaRenderer.renderSchema(SchemaDefinition.schema))
  }
}

//@TODO: we should probably implement more role based permissions in this
// This context class is used within SchemaDefinition to determine authorized graphql actions
case class SecureContext(token: String) {

  def authorized(): User = {
    val user = User.byToken(token)
    if (user.isEmpty) throw AuthorizationException("Unauthorized.")
    user.get
  }
}