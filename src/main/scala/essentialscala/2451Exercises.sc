object Oswald {
  val colour = "Black"
  val food = "Milk"
}

object Henderson {
  val colour = "Ginger"
  val food = "Chips"
}

object Quentin {
  val colour = "Tabby and white"
  val food = "Curry"
}

object calc {
  def square(x: Double): Double = x * x

  def cube(x: Double): Double = x * square(x)
}

object calc2 {
  def square(value: Double) = value * value

  def cube(value: Double) = value * square(value)

  def square(value: Int) = value * value

  def cube(value: Int) = value * square(value)
}

object argh {
  def a = {
    println("a")
    1
  }

  val b = {
    println("b")
    a + 2
  }

  def c = {
    println("c")
    a
    b + "c"
  }
}

argh.c + argh.b + argh.a

object person {
  val firstName = "Stefan"
  val lastName = "Mayer"
}

object alien {
  def greet(p: person.type): String = "Greetings, " + person.firstName + "!"
}

alien.greet(person)



