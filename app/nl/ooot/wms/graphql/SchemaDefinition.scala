package nl.ooot.wms.graphql

import nl.ooot.wms.graphql.schema.types.{LoginType, RoleType, UserType}
import nl.ooot.wms.models
import nl.ooot.wms.models.User
import sangria.schema._

import scala.collection.JavaConverters._

/**
  * Defines a GraphQL schema for the current project
  */
//noinspection ForwardReference
object SchemaDefinition {

  // Arguments
  val ID = Argument("id", IntType, description = "id of the object")
  val LimitArg = Argument("limit", OptionInputType(IntType), defaultValue = 20)
  val OffsetArg = Argument("offset", OptionInputType(IntType), defaultValue = 0)

  val EmailArg = Argument("email", StringType)
  val PasswordArg = Argument("password", StringType)

  // Top level Query and Mutation graphql types
  val AuthRequiredQueryType: ObjectType[Any, User] = ObjectType(
    "Secure", fields[Any, User](
      Field("currentUser", UserType.UserType, resolve = _.value),
      Field("user", UserType.UserType,
        arguments = ID :: Nil,
        resolve = c => models.User.find(c arg ID)),
      Field("users", ListType(UserType.UserType),
        resolve = _ => models.User.find().findList().asScala),
      Field("roles", ListType(RoleType.RoleType),
        resolve = _ => models.Role.find().findList().asScala)
    )
  )

  val QueryType = ObjectType(
    "Query", fields[SecureContext, Any](
      Field("secure", AuthRequiredQueryType, resolve = ctx => ctx.ctx.authorized()),
    )
  )

  val MutationType = ObjectType(
    "Mutation", fields[SecureContext, Any](
      Field("login", LoginType.LoginType,
        arguments = List(EmailArg, PasswordArg),
        resolve = c => {
          val authToken = models.User.authenticate(c arg EmailArg, c arg PasswordArg)
          if (authToken.isDefined) authToken.get
          else "invalid login"
        }
      )
    )
  )

  // the eventual schema
  val schema = Schema(QueryType, Some(MutationType))
}