package nl.ooot.wms.networking

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

object SmartAssistant extends Dispatcher {

  def routes(): akka.http.scaladsl.server.Route = {
      path("smart") {
        get {
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "Get it smartguy?"))
        } ~
        post {
          complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "This would smartly be the result of a post"))
        }
      }
    }
}