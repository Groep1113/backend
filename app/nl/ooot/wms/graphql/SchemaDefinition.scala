package nl.ooot.wms.graphql

import sangria.schema._

/**
  * Defines a GraphQL schema for the current project
  */
object SchemaDefinition {
  /**
    * Resolves the lists of characters. These resolutions are batched and
    * cached for the duration of a query.
    */

  val User =
    ObjectType(
      "User",
      "A humanoid creature. Highly illogical.",
      fields[UserRepo, User](
        Field("id", StringType,
          Some("The id of the human."),
          resolve = _.value.id),
        Field("firstName", StringType,
          Some("first name"),
          resolve = _.value.firstName),
        Field("lastName", StringType,
          Some("last name"),
          resolve = _.value.lastName),
        Field("role", StringType,
          Some("user role"),
          resolve = _.value.role),
        Field("email", StringType,
          Some("User email"),
          resolve = _.value.email),
      ))

  val ID = Argument("id", StringType, description = "id of the user")
  val LimitArg = Argument("limit", OptionInputType(IntType), defaultValue = 20)
  val OffsetArg = Argument("offset", OptionInputType(IntType), defaultValue = 0)

  val Query = ObjectType(
    "Query", fields[UserRepo, Unit](
      Field("user", OptionType(User),
        arguments = ID :: Nil,
        resolve = ctx ⇒ ctx.ctx.getUser(ctx arg ID)),
      Field("users", ListType(User),
        arguments = LimitArg :: OffsetArg :: Nil,
        resolve = ctx ⇒ ctx.ctx.getUsers(ctx arg LimitArg, ctx arg OffsetArg)),
    ))

  val schema = Schema(Query)
}