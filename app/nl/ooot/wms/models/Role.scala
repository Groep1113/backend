package nl.ooot.wms.models

import java.util

import io.ebean
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

}

object Role extends Dao(classOf[Role]) {
  def apply(name: String): Role = {
    val role = new Role()
    role.setName(name)
    role
  }

  def unapply(role: Role): Option[String] = Some(role.getName)
}