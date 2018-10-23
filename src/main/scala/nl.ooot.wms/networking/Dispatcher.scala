package nl.ooot.wms.networking

import java.net.Socket

trait Dispatcher {
  var signature: String
  def dispatch(inputstream: java.io.InputStream, s: Socket): Unit
}