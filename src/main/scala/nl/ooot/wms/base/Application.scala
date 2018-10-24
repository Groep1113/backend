package nl.ooot.wms.base

import nl.ooot.wms.networking.WebServer

object Application extends App {
  WebServer.listen()
}
