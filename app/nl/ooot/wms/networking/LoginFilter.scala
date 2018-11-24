package nl.ooot.wms.networking

import javax.inject.Inject
import nl.ooot.wms.models.User
import play.api.http.{HttpErrorHandler, Status}
import play.api.libs.streams.Accumulator
import play.api.mvc.{EssentialAction, RequestHeader, _}

case class LoginFilter @Inject() (errorHandler: HttpErrorHandler)
  extends play.api.mvc.EssentialFilter {
  private val blacklist = List(
    ("/graphql", List("POST", "GET"))
  )
  private val basicString = "basic "

  def isWhitelist(requestHeader: RequestHeader): Boolean = {
    if(requestHeader.uri.length() > 0) {
      for(bl <- blacklist) {
        if(requestHeader.uri.startsWith(bl._1)) {
          for(method <- bl._2) {
            var m: String = method
            if(m.equals(requestHeader.method)) {
              return false
            }
          }
        }
      }
    }
    true
  }

  private def getBasicAuth(auth: String): Option[(String, String)] = {

    if (auth.length() < basicString.length()) {
      return None
    }
    val basicReqSt = auth.substring(0, basicString.length())
    if (basicReqSt.toLowerCase() != basicString) {
      return None
    }
    val basicAuthSt = auth.replaceFirst(basicReqSt, "")

    val decodedString = new String(java.util.Base64.getDecoder.decode(basicAuthSt.getBytes("UTF-8")))

    val usernamePassword = decodedString.split(":")
    if (usernamePassword.length >= 2) {
      //account for ":" in passwords
      return Some(usernamePassword(0), usernamePassword.splitAt(1)._2.mkString)
    }
    None
  }

  override def apply(next: EssentialAction) = EssentialAction { req =>

    var weGood = false
    if (isWhitelist(req)) {
      weGood = true
    }
    req.headers.get("authorization").map { basicAuth =>
      getBasicAuth(basicAuth) match {
        case Some((user, pass)) => {
          var userObject = User.authenticate(user, pass)
          if(userObject != null){
            weGood = true
          }
          null
        }
        case _ => null;
      }
    }

    if(!weGood){
      Accumulator.done(errorHandler.onClientError(req, Status.FORBIDDEN, s"Login required: ${req.host}"))
    } else {
      next(req)
    }
  }
}
