package nl.ooot.wms.graphql

import sangria.execution.{ExceptionHandler, HandledException, MaxQueryDepthReachedError}

object ErrorHandling {

  case class AuthenticationException(message: String) extends Exception(message)
  case class AuthorizationException(message: String) extends Exception(message)
  case object TooComplexQueryError extends Exception("Query is too expensive.")

  lazy val errorHandler = ExceptionHandler {
    case (_, error@TooComplexQueryError) => HandledException(error.getMessage)
    case (_, error@MaxQueryDepthReachedError(_)) => HandledException(error.getMessage)
    case (_, AuthenticationException(message)) => HandledException(message)
    case (_, AuthorizationException(message)) => HandledException(message)
  }

}
