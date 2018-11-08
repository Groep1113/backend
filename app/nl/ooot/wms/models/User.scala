package nl.ooot.wms.models

import java.sql.Date
import java.util

import io.ebean
import javax.persistence._
import nl.ooot.wms.dao._

import scala.beans.BeanProperty

@Entity
class User {
  @Id
  var id: Int = 0

  @Column
  @BeanProperty
  var firstName: String = _

  @Column
  @BeanProperty
  var lastName: String = _

  @Column(unique=true)
  @BeanProperty
  var email: String = _

  @Column
  @BeanProperty
  var password: String = _

  @Column
  @BeanProperty
  var dateOfBirth: Date = _

  @ManyToMany
  var roles : util.List[Role] = new util.ArrayList[Role]()
}

object User extends Dao(classOf[User]) {
  def apply(firstName: String, lastName: String, dob: Date, email: String, password: String): User = {
    val user = new User()
    user.setFirstName(firstName)
    user.setLastName(lastName)
    user.setDateOfBirth(dob)
    user.setPassword(password)
    user.setEmail(email)
    user
  }

  def unapply(user: User): Option[(String, String, Date, String)] = Some((user.getFirstName, user.getLastName, user.getDateOfBirth, user.getEmail))

  def authenticate(username: String, password: String): User = {
    var user : User = find().where().eq("email", username).eq("password", password).findOne()
    user
  }
}