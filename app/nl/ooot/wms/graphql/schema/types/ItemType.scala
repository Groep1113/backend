package nl.ooot.wms.graphql.schema.types

import nl.ooot.wms.models.Item
import sangria.schema._
import scala.collection.JavaConverters._

object ItemType {
  val ItemType: ObjectType[Unit, Item] = ObjectType(
    "Item",
    "An item object",
    () ⇒ fields[Unit, Item](
      Field("id", IntType, resolve = _.value.id),
      Field("name", StringType, resolve = _.value.name),
      Field("code", StringType, resolve = _.value.code),
      Field("locations", OptionType(ListType(LocationType.LocationType)), resolve = _.value.locations.asScala),
      Field("recommended_stock", IntType, resolve = _.value.recommended_stock)
    )
  )
}
