package nl.ooot.wms.models

import scala.beans.BeanProperty
import java.util

import javax.persistence._
import nl.ooot.wms.dao.Dao

@Entity
class Item {
  @Id
  var id: Int = 0

  @Column
  @BeanProperty
  var name: String = _

  @Column
  @BeanProperty
  var code: String = _

  @Column
  @BeanProperty
  var recommended_stock: Int = _

  @ManyToMany(mappedBy = "items")
  var locations : util.List[Location] = new util.ArrayList[Location]()
}

object Item extends Dao(classOf[Item]) {
  def apply(name: String): Item = {
    val item = new Item()
    item.setName(name)
    item
  }

  def unapply(item: Item): Option[String] = Some(item.name)
}
