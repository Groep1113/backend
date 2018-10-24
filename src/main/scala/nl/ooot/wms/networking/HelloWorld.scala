package nl.ooot.wms.networking

import java.io._
import java.net.Socket
import java.io.OutputStreamWriter
import java.io.PrintWriter

object HelloWorld extends Dispatcher {
  var signature = "/hello"
  def dispatch(input: InputStream, s: Socket): Unit = {
    println("Hello world console")
    var out = new PrintWriter(s.getOutputStream(), false);
    out.print("HTTP/1.1 200 \r\n"); // Version & status code
    out.print("Content-Type: text/plain\r\n"); // The type of data
    out.print("Connection: close\r\n"); // Will close stream
    out.print("\r\n");
    out.print("Hello World!!")
    out.flush()
//    println("Hello world!")
  }
}