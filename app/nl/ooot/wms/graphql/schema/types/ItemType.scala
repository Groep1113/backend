package nl.ooot.wms.graphql.schema.types

import nl.ooot.wms.models.Item
import sangria.schema._

object ItemType {
  val ItemType: ObjectType[Unit, Item] = ObjectType(
    "Item",
    "A product item",
    () ⇒ fields[Unit, Item](
      Field("id", IntType, resolve = _.value.id),
      Field("supplier", OptionType(SupplierType.SupplierType), resolve = _.value.supplier),
      Field("category", OptionType(CategoryType.CategoryType), resolve = _.value.category),
      Field("name", StringType, resolve = _.value.name)))
}
