package nl.ooot.wms.graphql.schema.types

import nl.ooot.wms.models.Role
import sangria.schema._
import scala.collection.JavaConverters._

object RoleType {
  val RoleType: ObjectType[Unit, Role] = ObjectType(
    "Role",
    "A user assigned company role",
    () ⇒ fields[Unit, Role](
      Field("id", IntType, resolve = _.value.id),
      Field("users", OptionType(ListType(UserType.UserType)), resolve = _.value.users.asScala),
      Field("name", StringType, resolve = _.value.name)))
}
