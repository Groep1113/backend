package nl.ooot.wms

import nl.ooot.wms.core.Weather
import nl.ooot.wms.networking.WebServer

import scala.concurrent._
import scala.concurrent.duration._

object IT_WMS_Backend extends App {
  // Fetch weather example
  val w = Await.result(Weather.weather, 10.seconds)
  println(s"Hello! The weather in New York is $w.")
  Weather.http.close()
  // --

  // example GraphQL query
  import io.circe.Json
  import sangria.execution._
  import sangria.macros._

  import graphql.CharacterRepo
  import graphql.SchemaDefinition.StarWarsSchema

  import ExecutionContext.Implicits.global
  val query =
  graphql"""
    query {
      humans(limit: 3) {
        id name homePlanet
      }

      human(id: "1003") {
        name homePlanet appearsIn
      }

      droids(limit: 1) {
        id name primaryFunction
      }
    }
  """
//  val result: Future[Json] =
//    Executor.execute(StarWarsSchema, query, new CharacterRepo)

//  result.onComplete(println)
  // --

  // Akka HTTP webserver
  WebServer.listen()
//  Server.listen()
}
