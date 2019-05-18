val xs = 3 :: 6 :: 12 :: Nil
xs match {
  case List(a, b) => a * b
  case List(a, b, c) => a + b + c
  case _ => 0
}
xs match {
  case List(a, b, _*) => a * b
  case _ => 0
}

object GivenNames {
  def unapplySeq(name: String): Option[Seq[String]] = {
    val names = name.trim.split(" ")
    if (names.forall(_.isEmpty)) None else Some(names)
  }
}
def greetWithFirstName(name: String) = name match {
  case GivenNames(firstName, _*) => "Good morning, " + firstName + "!"
  case _ => "Welcome! Please make sure to fill in your name!"
}
greetWithFirstName("Daniel")
greetWithFirstName(" Catherina Johanna")
greetWithFirstName("")

object Names {
  def unapplySeq(name: String): Option[(String, String, Seq[String])] = {
    val names = name.trim.split(" ")
    if (names.size < 2) None
    else Some((names.last, names.head, names.drop(1).dropRight(1)))
  }
}
def greet(fullName: String) = fullName match {
  case Names(lastName, firstName, _*) => "Good morning, " + firstName + " " + lastName + "!"
  case _ => "Welcome! Please make sure to fill in your name!"
}
greet("Catherina Johanna")
greet("Tic Tric Trac")
greet("Daniel")

