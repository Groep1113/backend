package nl.ooot.wms.core

import java.sql.Date

import controllers.AssetsFinder
import io.ebean.Ebean
import javax.inject.Inject
import nl.ooot.wms.models.{Item, Role, User, Location}
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
    val u: User = User.authenticate("admin@bs-htg.nl", "357111317232731")

    // @TODO: MAKE ACTUAL SEEDER THIS SUCKS
    if (u == null) {
      var u = new User()
      u.setDateOfBirth(Date.valueOf("1993-05-16"))
      u.setFirstName("Admin")
      u.setLastName("1113")
      u.setEmail("admin@bs-htg.nl")
      u.setPassword("357111317232731")
      Ebean.save(u)

      val role = new Role()
      role.setName("admin")
      u.roles.add(role)
      Ebean.save(role)
      Ebean.save(u)

      u = User.authenticate("admin@bs-htg.nl", "357111317232731")
    }

    val item = new Item()
    item.setName("Lenovo Yoga")
    item.setCode("123456")
    item.setRecommended_stock(5)
    Ebean.save(item)

    val location = new Location()
    location.setCode("A1")
    Ebean.save(location)

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