package nl.ooot.wms.graphql.schema.types

import nl.ooot.wms.models.User
import sangria.schema._

object LoginType {
  val LoginType: ObjectType[Unit, String] = ObjectType(
    "Login",
    fields[Unit, String](
      Field("token", StringType, resolve = _.value),
      Field("user", UserType.UserType, resolve = c => User.byToken(c.value).orNull),
    )
  )
}
