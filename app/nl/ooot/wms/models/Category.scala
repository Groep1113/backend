package nl.ooot.wms.models

import java.util

import javax.persistence._
import nl.ooot.wms.dao.Dao

import scala.beans.BeanProperty


@Entity
class Category extends BaseModel {
  @Column
  @BeanProperty
  var name: String = _

  @OneToMany(mappedBy = "category")
  var items: util.List[Item] = new util.ArrayList[Item]()

  @ManyToMany(mappedBy = "categories")
  var locations: util.List[Location] = new util.ArrayList[Location]()
}

object Category extends Dao(classOf[Category]) {
  def apply(name: String): Category = {
    val category = new Category()
    category.setName(name)
    category
  }

  def unapply(category: Category): Option[String] = Some(category.getName)
}