val greeting: Option[String] = Some("hello world")
val greetingNone: Option[String] = None
// Factory for calling Java code:
val absentGreeting: Option[String] = Option(null)
val presentGreeting: Option[String] = Option("Hello!")

case class User(
                 id: Int,
                 firstName: String,
                 lastName: String,
                 age: Int,
                 gender: Option[String]
               )

object UserRepository {
  private val users = Map(1 -> User(1, "John", "Doe", 32, Some("male")),
    2 -> User(2, "Johanna", "Doe", 30, None))

  def findById(id: Int): Option[User] = users.get(id)

  def findAll() = users.values
}

val user = User(2, "Johanna", "Doe", 30, None)
println("Gender: " + user.gender.getOrElse("not specified"))

// too verbose:
val gender = user.gender match {
  case Some(gender) => gender
  case None => "not specified"
}
println("Gender: " + gender)

// better ways:
UserRepository.findById(2).foreach(user => println(user.firstName))

val age = UserRepository.findById(1).map(_.age)

val gender2 = UserRepository.findById(1).map(_.gender)
val gender3 = UserRepository.findById(1).flatMap(_.gender)

val names: List[List[String]] =
  List(List("John", "Johanna", "Daniel"), List(), List("Doe", "Westheide"))
names.map(_.map(_.toUpperCase()))
names.flatMap(_.map(_.toUpperCase()))

val names2: List[Option[String]] = List(Some("Johanna"), None, Some("Daniel"))
names2.map(_.map(_.toUpperCase()))
names2.flatMap(xs => xs.map(_.toUpperCase()))

UserRepository.findById(1).filter(_.age > 30)
UserRepository.findById(2).filter(_.age > 30)
UserRepository.findById(3).filter(_.age > 30)

for {
  user <- UserRepository.findById(1)
  gender <- user.gender
} yield gender

for {
  user <- UserRepository.findAll()
  gender <- user.gender
} yield gender

for {
  User(_, _, _, _, Some(gender)) <- UserRepository.findAll()
} yield gender

case class Resource(content: String)

val resourceFromConfigDir: Option[Resource] = None
val resourceFromClasspath: Option[Resource] = Some(Resource("I was found on the classpath"))
val resource = resourceFromConfigDir orElse resourceFromClasspath

