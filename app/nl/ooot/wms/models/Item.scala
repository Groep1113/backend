package nl.ooot.wms.models

import java.beans.BeanProperty

import javax.persistence._
import nl.ooot.wms.dao.Dao

@Entity
class Item {
  @Id
  var id: Int = 0

  @Column
  @BeanProperty
  var name: String = _

}

object Item extends Dao(classOf[Item]) {
  def apply(name: String): Item = {
    val item = new Item()
    item.setName(name)
    item
  }

  def unapply(item: Item): Option[String] = Some(item.name)
}
