package nl.ooot.wms.networking

import java.io._
import java.net.Socket

object SmartAssistant extends Dispatcher {
  var signature = "/smart"
  def dispatch(input: InputStream, s: Socket): Unit = {
    println("2 + 2 is 4 minus 1 is 3 quick matHS")
  }
}