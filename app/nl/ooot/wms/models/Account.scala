package nl.ooot.wms.models

import javax.persistence._
import nl.ooot.wms.dao.Dao

import scala.beans.BeanProperty


@Entity
class Account extends BaseModel {
  @Column
  @BeanProperty
  var name: String = _
}

object Account extends Dao(classOf[Account]) {
  def apply(name: String): Account = {
    val account = new Account()
    account.setName(name)
    account
  }

  def unapply(account: Account): Option[String] = Some(account.getName)
}