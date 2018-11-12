package nl.ooot.wms.models

import java.util

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

  @ManyToMany
  var items: util.List[Item] = new util.ArrayList[Item]()

  @ManyToMany
  var categories: util.List[Category] = new util.ArrayList[Category]()
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

  def unapply(l: Location): Option[(String, String, String, String)] = Some((l.getCode, l.getDepth, l.getHeight, l.getWidth))
}