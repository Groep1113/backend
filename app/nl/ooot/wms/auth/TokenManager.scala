package nl.ooot.wms.auth

import com.github.t3hnar.bcrypt._
import nl.ooot.wms.models.User

import scala.collection.mutable
import scala.util.Random

//@TODO: invalidating tokens and expiring tokens
object TokenManager {
  // key: User object, value: token
  private val tokens: mutable.HashMap[User, String] = mutable.HashMap()

  def generateToken(user: User): String = {
    val optionToken = getToken(user)
    if (optionToken.isDefined) return optionToken.get

    val random: Random = new Random()
    val token = random.nextString(32).bcrypt(5)
    tokens.put(user, token)
    token
  }

  def getToken(user: User): Option[String] = {
    tokens.get(user)
  }

  def getUser(token: String): Option[User] = {
    tokens.keys.find(u => tokens(u).equals(token))
  }

  def invalidateToken(user: User): Unit = {
    tokens.remove(user)
  }

}
