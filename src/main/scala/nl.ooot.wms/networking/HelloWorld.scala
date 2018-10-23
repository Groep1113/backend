package nl.ooot.wms.networking

import java.io._
import java.net.Socket
import java.io.OutputStreamWriter
import java.io.Writer

object HelloWorld extends Dispatcher {
  var signature = "/hello"
  def dispatch(input: InputStream, s: Socket): Unit = {
    var stream = s.getOutputStream()

    try {
      val w = new OutputStreamWriter(stream, "UTF-8")
      try
        w.write("Hello, World!")
      finally if (w != null) w.close()
    }
//    println("Hello world!")
  }
}