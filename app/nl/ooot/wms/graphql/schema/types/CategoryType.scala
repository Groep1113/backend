package nl.ooot.wms.graphql.schema.types

import nl.ooot.wms.models.Category
import sangria.schema._

object CategoryType {
  val CategoryType: ObjectType[Unit, Category] = ObjectType(
    "Category",
    "A product category",
    () ⇒ fields[Unit, Category](
      Field("id", IntType, resolve = _.value.id),
      Field("name", StringType, resolve = _.value.name)))
}
