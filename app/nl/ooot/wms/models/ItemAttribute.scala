package nl.ooot.wms.models

import javax.persistence._
import nl.ooot.wms.dao._

import scala.beans.BeanProperty

@Entity
class ItemAttribute extends BaseModel {
  @Column
  @BeanProperty
  @ManyToOne
  var item: Item = _

  @Column
  @BeanProperty
  var key: String = _

  @BeanProperty
  var value: String = _
}

object ItemAttribute extends Dao(classOf[ItemAttribute]) {
  def apply(item: Item, key: String, value: String): ItemAttribute = {
    val itemattribute = new ItemAttribute()
    itemattribute.setItem(item)
    itemattribute.setKey(key)
    itemattribute.setValue(value)
    itemattribute
  }

  def unapply(iA: ItemAttribute): Option[(Item, String, String)] = Some((iA.getItem, iA.getKey, iA.getValue))
}