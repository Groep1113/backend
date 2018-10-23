package nl.ooot.wms.networking

import java.net.ServerSocket
import java.util.Scanner

import scala.io._
import java.io._
import java.util.ArrayList

object APIGateway{
  def listen(): Unit = {

    val dispatchers = Array(HelloWorld, SmartAssistant)

    val server = new ServerSocket(9000)

    while (true) {
      println("____________________")
      val s = server.accept()
      val input = s.getInputStream()
      var sc: Scanner = new Scanner(input)
      var lines = 0
      var scanning = true
      while(sc.hasNext() && scanning) {
        lines = lines + 1
        if (lines == 2) {
          for (d <- dispatchers) {
            if (sc.next().startsWith(d.signature)) {
              d.dispatch(input, s)
            }
          }
          s.close()
          scanning = false
        }
      }
      s.close()
    }
  }
//  def convert(is: InputStream): String = {
//    try {
//
//      return sc.useDelimiter("\\A").next()
//    } catch {
//      case e: Exception => return ""
//    }
//  }
}