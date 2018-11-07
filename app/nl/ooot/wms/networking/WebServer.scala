package nl.ooot.wms.networking

import scala.io.StdIn
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import nl.ooot.wms.graphql.GraphQLDispatcher

import scala.concurrent.ExecutionContextExecutor

object WebServer {
  /*
    Implicit means its passed to function that would require such a type but aren't provided one.
    TODO: Figure out what this magic means.
  */
  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val dispatchers = Array(HelloWorld, SmartAssistant, GraphQLDispatcher)

  /*
    A little confusing... ~ is a method on objects used as sequential parser combinator
   */
  var route: Route = path("/") { complete("") }
  for (d <- dispatchers) {
    route = route.~(d.routes())
  }

  def listen() {
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    /* TODO: Fix this */
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}

//
//object APIGateway{
//  def listen(): Unit = {
//
//    val dispatchers = Array(HelloWorld, SmartAssistant)
//
//    val server = new ServerSocket(9000)
//
//    while (true) {
//      println("____________________")
//      val s = server.accept()
//      val input = s.getInputStream()
//      var sc: Scanner = new Scanner(input)
//      var lines = 0
//      var scanning = true
//      while(sc.hasNext() && scanning) {
//        lines = lines + 1
//        var next = sc.next()
//        if (lines == 2) {
//          for (d <- dispatchers) {
//            if (next.startsWith(d.signature)) {
//              d.dispatch(input, s)
//            }
//          }
//          s.close()
//          scanning = false
//        }
//      }
//      s.close()
//    }
//  }
//  def convert(is: InputStream): String = {
//    try {
//
//      return sc.useDelimiter("\\A").next()
//    } catch {
//      case e: Exception => return ""
//    }
//  }
// }