package nl.ooot.wms.models

import java.sql.Date
import javax.persistence._
import nl.ooot.wms.dao.Dao
import scala.beans.BeanProperty


@Entity
class Category extends BaseModel {
  @Column
  @BeanProperty
  var name: String = _
}

object Category extends Dao(classOf[Category]) {
  def apply(name: String): Category = {
    val category = new Category()
    category.setName(name)
    category
  }

  def unapply(user: User): Option[(String, String, Date, String)] = Some((user.getFirstName, user.getLastName, user.getDateOfBirth, user.getEmail))
}