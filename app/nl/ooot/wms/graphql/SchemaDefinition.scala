package nl.ooot.wms.graphql

import nl.ooot.wms.graphql.schema.types.{ItemType, RoleType, UserType}
import nl.ooot.wms.models
import sangria.schema._

import scala.collection.JavaConverters._

/**
  * Defines a GraphQL schema for the current project
  */
//noinspection ForwardReference
object SchemaDefinition {

  val ID = Argument("id", IntType, description = "id of the object")
  val LimitArg = Argument("limit", OptionInputType(IntType), defaultValue = 20)
  val OffsetArg = Argument("offset", OptionInputType(IntType), defaultValue = 0)

  val QueryType = ObjectType(
    "Query", fields[Any, Unit](
      Field("user", UserType.UserType,
        arguments = ID :: Nil,
        resolve = c ⇒ models.User.find(c arg ID)),
      Field("users", ListType(UserType.UserType),
        arguments = Nil,
        resolve = _ ⇒ models.User.find().findList().asScala),
      Field("roles", ListType(RoleType.RoleType),
        arguments = Nil,
        resolve = _ ⇒ models.Role.find().findList().asScala),
      Field("items", ListType(ItemType.ItemType),
        arguments = Nil,
        resolve = _ ⇒ models.Item.find().findList().asScala),
    ))

  val schema = Schema(QueryType)
}