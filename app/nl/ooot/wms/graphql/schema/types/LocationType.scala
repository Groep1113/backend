package nl.ooot.wms.graphql.schema.types

import nl.ooot.wms.models.Location
import sangria.schema._

object LocationType {
  val LocationType: ObjectType[Unit, Location] = ObjectType(
    "Location",
    "An location object",
    () ⇒ fields[Unit, Location](
      Field("id", IntType, resolve = _.value.id),
      Field("code", StringType, resolve = _.value.code),
    )
  )
}
