package nl.ooot.wms.base

import nl.ooot.wms.user.UserController
import nl.ooot.wms.networking.APIGateway

object Application extends App {
//  var user = new UserController("Piet", 28)
//  var user2 = new UserController("Henk", 23)
//  println(user.getOccupation())
//  println(user2.getOccupation())

//  while(true){
//    try{
      val server = APIGateway
      server.listen()
//    }catch {
//      case e: Exception => println("Exception!!!")
//    }
//  }
}
