package nl.ooot.wms.models

import java.util

import javax.persistence._
import nl.ooot.wms.dao._

import scala.beans.BeanProperty

@Entity
class Supplier extends BaseModel {
  @Column
  @BeanProperty
  var name: String = _

  @OneToMany
  var items: util.List[Item] = new util.ArrayList[Item]()
}

object Supplier extends Dao(classOf[Supplier]) {
  def apply(name: String): Supplier = {
    val supplier = new Supplier()
    supplier.setName(name)
    supplier
  }

  def unapply(s: Supplier): Option[String] = Some(s.getName)
}