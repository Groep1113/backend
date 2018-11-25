package nl.ooot.wms.core

import java.sql.Date

import controllers.AssetsFinder
import io.ebean.Ebean
import javax.inject.Inject
import nl.ooot.wms.models.{Role, User}
import play.api.mvc._

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
    val auth = User.authenticate("admin@bs-htg.nl", "habbo123")
    var u: User = null

    // @TODO: MAKE ACTUAL SEEDER THIS SUCKS
    if (auth.isDefined) {
      u = User.byToken(auth.get).get
    } else {
      u = new User()
      u.setDateOfBirth(Date.valueOf("1993-05-16"))
      u.setFirstName("Admin")
      u.setLastName("1113")
      u.setEmail("admin@bs-htg.nl")
      u.setAndHashPassword("habbo123")
      Ebean.save(u)

      val role = Role.findOrCreate("admin")
      u.roles.add(role)
      Ebean.save(role)
      Ebean.save(u)
    }

    // Loop over many to many field (java.util.List)
    val rIterator = u.roles.iterator()
    while (rIterator.hasNext) {
      println(rIterator.next().name)
    }

    // getters for fields follow camelcase, field firstName becomes getFirstName
    println(u.getFirstName)
    
    Ok(nl.ooot.wms.views.html.index("Come on and slam and welcome to the jam!"))
  }

}