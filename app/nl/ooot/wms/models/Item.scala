package nl.ooot.wms.models

import java.util

import javax.persistence._
import nl.ooot.wms.dao._

import scala.beans.BeanProperty

@Entity
class Item extends BaseModel {
  @ManyToOne
  var supplier: Supplier = _

  @ManyToOne
  var category: Category = _

  @ManyToMany(mappedBy = "items")
  var locations: util.List[Location] = new util.ArrayList[Location]()

  @OneToMany(mappedBy = "item")
  var attributes: util.List[ItemAttribute] = new util.ArrayList[ItemAttribute]()

  @Column
  @BeanProperty
  var name: String = _

  @Column
  @BeanProperty
  var code: String = _

  @Column
  @BeanProperty
  var recommendedStock: Int = _
}

object Item extends Dao(classOf[Item]) {
  def apply(name: String, code: String, recommendedStock: Int): Item = {
    val item = new Item()
    item.setName(name)
    item.setCode(code)
    item.setRecommendedStock(recommendedStock)
    item
  }

  def unapply(i: Item): Option[(String, String, Int)] = Some((i.getName, i.getCode, i.getRecommendedStock))
}