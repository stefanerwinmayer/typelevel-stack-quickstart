class Person(firstName: String, lastName: String)
val gump = new Person("Forrest", "Gump")

class Compass {

  val directions = List("north", "east", "south", "west")
  var bearing = 0

  print("Initial bearing: ")
  println(direction)

  def direction() = directions(bearing)

  def inform(turnDirection: String) {
    println("Turning " + turnDirection + ". Now bearing " + direction)
  }

  def turnRight() {
    bearing = (bearing + 1) % directions.size
    inform("right")
  }

  def turnLeft() {
    bearing = (bearing + (directions.size - 1)) % directions.size
    inform("left")
  }
}

val myCompass = new Compass
myCompass.turnRight()
myCompass.turnRight()
myCompass.turnLeft()
myCompass.turnLeft()
myCompass.turnLeft()

class Person2(firstName: String) {
  println("Outer constructor")
  def this(firstName: String, lastName: String) {
    this(firstName)
    println("Inner constructor")
  }
  def talk() = println("Hi")
}
val bob = new Person2("Bob")
val bobTate = new Person2("Bob", "Tate")

