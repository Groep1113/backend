package nl.ooot.wms.models

import java.sql.Timestamp

import javax.persistence._

@MappedSuperclass
abstract class BaseModel {

  @Id
  var id: Int = 0

  @Column(columnDefinition = "datetime")
  var createdAt: Timestamp = _

  @Column(columnDefinition = "datetime")
  var updatedAt: Timestamp = _

}
