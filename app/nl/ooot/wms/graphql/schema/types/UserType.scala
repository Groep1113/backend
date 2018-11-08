package nl.ooot.wms.graphql.schema.types

import nl.ooot.wms.models.User
import sangria.schema._
import scala.collection.JavaConverters._

object UserType {
  val UserType: ObjectType[Unit, User] = ObjectType(
    "User",
    "A user object",
    () ⇒ fields[Unit, User](
      Field("id", IntType, resolve = _.value.id),
      Field("firstName", StringType, resolve = _.value.firstName),
      Field("lastName", StringType, resolve = _.value.lastName),
      Field("email", StringType, resolve = _.value.email),
      Field("roles", OptionType(ListType(RoleType.RoleType)), resolve = _.value.roles.asScala),
      Field("dateOfBirth", StringType, resolve = _.value.dateOfBirth.toString())))
}
