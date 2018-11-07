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

  // Akka HTTP webserver
  WebServer.listen()
//  Server.listen()
}
