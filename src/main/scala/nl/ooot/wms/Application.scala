package nl.ooot.wms

import nl.ooot.wms.core.Weather
import nl.ooot.wms.graphql.ProductRepo

import scala.concurrent._
import scala.concurrent.duration._

object Application extends App {
  val w = Await.result(Weather.weather, 10.seconds)
  println(s"Hello! The weather in New York is $w.")
  Weather.http.close()


  // example GraphQL query
  import sangria.macros._
  val query =
    graphql"""
    query {
      product(id: "2") {
        name
        description
        id

        picture(size: 500) {
          width, height, url
        }
      }

      products {
        name
      }
    }
  """
  import io.circe.Json
  import graphql.GraphQLSchema.schema
  import sangria.marshalling.circe._
  import sangria.execution._
  import ExecutionContext.Implicits.global

  val result: Future[Json] =
    Executor.execute(schema, query, new ProductRepo)

  result.onComplete(println)
  // --
}
