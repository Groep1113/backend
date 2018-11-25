package nl.ooot.wms.models

import java.beans.BeanProperty
import java.util

import javax.persistence._
import nl.ooot.wms.dao.Dao

@Entity
class Location {
  @Id
  var id: Int = 0

  @Column
  @BeanProperty
  var code: String = _

  @ManyToMany
  var items : util.List[Item] = new util.ArrayList[Item]()
}

object Location extends Dao(classOf[Location]) {
  def apply(code: String): Location = {
    val location = new Location()
    location.setCode(code)
    location
  }

  def unapply(location: Location): Option[String] = Some(location.code)
}
