package nl.ooot.wms.auth

import play.api.mvc.Request

object BearerAuth {

  def authenticateFromHeader(request: Request[Any]): String = {
    val authHeader = request.headers.get("authorization")
    if (authHeader.isEmpty) return null

    val authString = authHeader.get
    val str = authString.toLowerCase
    if (str.length < "bearer ".length || str.substring(0, "bearer".length) != "bearer") return null

    val token = authString.substring("bearer ".length)
    val optUser = TokenManager.getUser(token)
    if (optUser.isDefined) return token
    null
  }

}
