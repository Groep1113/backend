package nl.ooot.wms.networking

trait Dispatcher {
  def routes(): akka.http.scaladsl.server.Route
}