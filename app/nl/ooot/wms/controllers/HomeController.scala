package nl.ooot.wms.controllers

import java.sql.Date
import java.util

import controllers.AssetsFinder
import io.ebean.Ebean
import javax.inject.Inject
import play.api.mvc._
import nl.ooot.wms.models.{Role, User}

import scala.concurrent.JavaConversions

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
class HomeController @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

  def index = Action {

    // Get user example
    var u : User = User.authenticate("wouter@wouter.nl", "wouter")

    // @TODO: MAKE ACTUAL SEEDER THIS SUCKS
    if(u == null) {
      var u = new User()
      u.setDateOfBirth(Date.valueOf("1993-05-16"))
      u.setFirstName("Woutie")
      u.setLastName("Houtie")
      u.setEmail("wouter@wouter.nl")
      u.setPassword("wouter")
      Ebean.save(u)

      var role = new Role()
      role.setName("held")
      u.roles.add(role)
      Ebean.save(role)
      Ebean.save(u)

      u = User.authenticate("wouter@wouter.nl", "wouter")
    }

    // Loop over many to many field (java.util.List)
    val rIterator = u.roles.iterator()
    while(rIterator.hasNext()){
      println(rIterator.next().name)
    }

    // getters for fields follow camelcase, field firstName becomes getFirstName
    println(u.getFirstName)


    Ok(nl.ooot.wms.views.html.index("Your app is ready."))
  }

}