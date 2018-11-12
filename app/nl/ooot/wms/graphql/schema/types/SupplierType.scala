package nl.ooot.wms.graphql.schema.types

import nl.ooot.wms.models.Supplier
import sangria.schema._

object SupplierType {
  val SupplierType: ObjectType[Unit, Supplier] = ObjectType(
    "Supplier",
    "A product supplier",
    () ⇒ fields[Unit, Supplier](
      Field("id", IntType, resolve = _.value.id),
      Field("name", StringType, resolve = _.value.name)))
}
