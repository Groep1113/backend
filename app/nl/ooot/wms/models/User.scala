package nl.ooot.wms.models

import java.sql.Date
import java.util

import javax.persistence._
import nl.ooot.wms.dao._
import nl.ooot.wms.auth._
import com.github.t3hnar.bcrypt._

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

  @Column(unique = true)
  @BeanProperty
  var email: String = _

  @Column
  @BeanProperty
  var password: String = _

  @Override
  def setAndHashPassword(password: String): Unit = {
    this.setPassword(User.hashPassword(password))
  }

  @Column
  @BeanProperty
  var dateOfBirth: Date = _

  @ManyToMany(mappedBy = "users")
  var roles: util.List[Role] = new util.ArrayList[Role]()
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

  // @TODO: figure out how to load secret key from production.conf in object..
//  private val SALT: String = "SomeSalt"
  private val ROUNDS: Int = 10

  def hashPassword(password: String): String = {
    // This could throw an exception if the password can not be bcrypted safely?
    // @TODO not sure what to do in such a case
    password.bcryptSafe(ROUNDS).get
  }

  def authenticate(username: String, password: String): Option[String] = {
    // find the user
    val user: User = find().where().eq("email", username).findOne()
    if (user == null) return None

    // return None if the password is not a match, or a token if it is a match
    if (!password.isBcrypted(user.getPassword)) return None
    Option(TokenManager.generateToken(user))
  }

  def byToken(token: String): Option[User] = {
    TokenManager.getUser(token)
  }
}