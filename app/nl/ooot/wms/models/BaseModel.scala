package nl.ooot.wms.models

import java.sql.Timestamp

import io.ebean.annotation.{CreatedTimestamp, UpdatedTimestamp}
import javax.persistence._

@MappedSuperclass
abstract class BaseModel {

  @Id
  var id: Int = 0

  @CreatedTimestamp
  var createdAt: Timestamp = _

  @UpdatedTimestamp
  var updatedAt: Timestamp = _

}
