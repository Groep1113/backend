package nl.ooot.wms

import nl.ooot.wms.user.UserController
import nl.ooot.wms.networking.APIGateway
import scala.concurrent._, duration._
import core.Weather

object Application extends App {
  val w = Await.result(Weather.weather, 10.seconds)
  println(s"Hello! The weather in New York is $w.")
  Weather.http.close()
}
