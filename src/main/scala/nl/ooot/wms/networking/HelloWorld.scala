package nl.ooot.wms.networking

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

object HelloWorld extends Dispatcher {

  def routes(): akka.http.scaladsl.server.Route = {
    path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Get it?"))
      } ~
      post {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "This would be the result of a post"))
      }
    }
  }
}