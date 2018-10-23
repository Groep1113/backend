package nl.ooot.wms.networking

import java.io._
import java.net.Socket
import java.io.OutputStreamWriter
import java.io.PrintWriter

object HelloWorld extends Dispatcher {
  var signature = "/hello"
  def dispatch(input: InputStream, s: Socket): Unit = {
    println("Hello world console")
    var out = new PrintWriter(s.getOutputStream(), true);
    out.print("Hello World!!")
//    println("Hello world!")
  }
}