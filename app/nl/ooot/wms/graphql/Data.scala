package nl.ooot.wms.graphql

case class User(id: String,
                firstName: String,
                lastName: String,
                role: String,
                email: String) extends UserTrait

trait UserTrait {
  def id: String
  def firstName: String
  def lastName: String
  def role: String
  def email: String
}

object UserRepo {
  val users = List(
    User(
      id = "1",
      firstName = "Peter",
      lastName = "Schriever",
      role = "een role",
      email = "p.schriever@st.hanze.nl"),
    User(
      id = "1",
      firstName = "Bob",
      lastName = "Jansen",
      role = "een role",
      email = "b.jansen@st.hanze.nl"),
    User(
      id = "1",
      firstName = "Woutie",
      lastName = "Houtie",
      role = "een role",
      email = "w.houtie@st.hanze.nl")
  )
}