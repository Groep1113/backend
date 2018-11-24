package nl.ooot.wms.graphql

import nl.ooot.wms.graphql.schema.types.{RoleType, UserType}
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

  val EmailArg = Argument("email", StringType)
  val PasswordArg = Argument("password", StringType)

  val QueryType = ObjectType(
    "Query", fields[Any, Unit](
      Field("user", UserType.UserType,
        arguments = ID :: Nil,
        resolve = c => models.User.find(c arg ID)),
      Field("users", ListType(UserType.UserType),
        resolve = _ => models.User.find().findList().asScala),
      Field("roles", ListType(RoleType.RoleType),
        resolve = _ => models.Role.find().findList().asScala)))

  val LoginType = ObjectType(
    name = "Login",
    () => fields[Unit, String](
      Field("token", StringType, resolve = _.value)))

  val MutationType = ObjectType(
    "Mutation", fields[Any, Unit](
      Field("login", LoginType,
        arguments = List(EmailArg, PasswordArg),
        resolve = c =>
          if (models.User.authenticate(c arg EmailArg, c arg PasswordArg) != null)
            "SomeValidTokenHere"
          else "invalid login"
  )))

  val schema = Schema(QueryType, Some(MutationType))
}