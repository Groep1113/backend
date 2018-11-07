/**
  * THIS IS NOT A DEPLOYMENT CLASS
  * this is meant to show some basic syntax of  scala.
  */

package nl.ooot.wms.user

/**
  * The params below act as a constructor. This sets the specified values as fields.
  * Adding params like this also generates getters and setters
  * @param name
  * @param age
  */
class UserController(var name: String, var age: Integer) {
  // Just a static field.
  var married = true
  // A constant field
  val cannot_be_changed = true
  // Strict types
  var a_text: String = "Hello world"
  /**
    * No scope means its public.
    * Colon (:) Type defines return type. If it's void, it's Unit.
    * @return String
    */
  def getOccupation(): String = {
    if (age > 25) {
      return name + " is a worker"
    } else {
      return name + " is a student"
    }
  }

  /**
    * If your function is a single line long, you can leave the {} out
    * @return Boolean
    */
  def isMarried(): Boolean = married
}