package nl.ooot.wms.models

import java.beans.BeanProperty

import javax.persistence._
import nl.ooot.wms.dao.Dao

@Entity
class Location {
  @Id
  var id: Int = 0

  @Column
  @BeanProperty
  var code: String = _
}

object Location extends Dao(classOf[Location]) {
  def apply(code: String): Location = {
    val location = new Location()
    location.setCode(code)
    location
  }

  def unapply(location: Location): Option[String] = Some(location.code)
}
