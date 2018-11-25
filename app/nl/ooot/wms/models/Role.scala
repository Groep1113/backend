package nl.ooot.wms.models

import java.util

import javax.persistence._
import nl.ooot.wms.dao._

import scala.beans.BeanProperty

@Entity
class Role {
  @Id
  var id: Int = 0

  @Column(unique=true)
  @BeanProperty
  var name: String = _

  @ManyToMany
  var users : util.List[User] = new util.ArrayList[User]()
}

object Role extends Dao(classOf[Role]) {
  def apply(name: String): Role = {
    val role = new Role()
    role.setName(name)
    role
  }

  def unapply(role: Role): Option[String] = Some(role.getName)

  def findOrCreate(name: String): Role = {
    var role = Role.find().where().eq("name", name).findOne()
    if (role == null) {
      role = new Role()
      role.setName(name)
    }
    role
  }
}