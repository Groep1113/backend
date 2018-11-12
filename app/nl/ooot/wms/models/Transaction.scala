package nl.ooot.wms.models

import javax.persistence._
import nl.ooot.wms.dao.Dao

import scala.beans.BeanProperty


@Entity
class Transaction extends BaseModel {
  @ManyToOne
  var fromAccount: Account = _

  @ManyToOne
  var toAccount: Account = _

  @Column
  @BeanProperty
  var deletedDate: String = _

  @Column
  @BeanProperty
  var receivedDate: String = _

  @Column
  @BeanProperty
  var isLocked: Boolean = _
}

object Transaction extends Dao(classOf[Transaction]) {
  def apply(deletedDate: String, receivedDate: String, isLocked: Boolean): Transaction = {
    val transaction = new Transaction()
    transaction.setDeletedDate(deletedDate)
    transaction.setReceivedDate(receivedDate)
    transaction.setIsLocked(isLocked)
    transaction
  }

  def unapply(transaction: Transaction): Option[String] = Some(transaction.getName)
}