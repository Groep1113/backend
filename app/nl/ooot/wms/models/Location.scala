package nl.ooot.wms.models

import java.sql.Date
import javax.persistence._
import nl.ooot.wms.dao.Dao
import scala.beans.BeanProperty


@Entity
class Location extends BaseModel {
  @Column
  @BeanProperty
  var code: String = _

  @Column
  @BeanProperty
  var depth: String = _

  @Column(unique = true)
  @BeanProperty
  var width: String = _

  @Column
  @BeanProperty
  var height: String = _
}

object Location extends Dao(classOf[Location]) {
  def apply(code: String, depth: String, width: String, height: String): Location = {
    val location = new Location()
    location.setCode(code)
    location.setDepth(depth)
    location.setHeight(height)
    location.setWidth(width)
    location
  }

  def unapply(user: User): Option[(String, String, Date, String)] = Some((user.getFirstName, user.getLastName, user.getDateOfBirth, user.getEmail))
}