package nl.ooot.wms.graphql

import sangria.schema._
import sangria.macros.derive._
import nl.ooot.wms.models
import collection.JavaConverters._

/**
  * Defines a GraphQL schema for the current project
  */
object SchemaDefinition {

  implicit val UserType: ObjectType[Unit, models.User] = ObjectType(
    "User",
    "A user object",
    fields[Unit, models.User](
      Field("id", IntType, resolve = _.value.id),
      Field("firstName", StringType, resolve = _.value.firstName),
      Field("lastName", StringType, resolve = _.value.lastName),
      Field("email", StringType, resolve = _.value.email),
      Field("dateOfBirth", StringType, resolve = _.value.dateOfBirth.toString())))

  val ID = Argument("id", IntType, description = "id of the object")
  val LimitArg = Argument("limit", OptionInputType(IntType), defaultValue = 20)
  val OffsetArg = Argument("offset", OptionInputType(IntType), defaultValue = 0)

  val QueryType = ObjectType(
    "Query", fields[Any, Unit](
      Field("user", UserType,
        arguments = ID :: Nil,
        resolve = c ⇒ models.User.find(c arg ID)),
      Field("users", ListType(UserType),
        arguments = Nil,
        resolve = _ ⇒ models.User.find().findList().asScala),
    ))

  val schema = Schema(QueryType)
}