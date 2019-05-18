object TrueRing {
  def rule = println("To rule them all")
}
TrueRing.rule

class Person(val name: String) {
  def talk(message: String) = println(name + " says " + message)
  def id(): String = name
}

class Employee(override val name: String,
               val number: Int) extends Person(name) {
  override def talk(message: String): Unit = {
    println(name + " with number " + number + " says " + message)
  }

  override def id(): String = number.toString
}

val employee = new Employee("Yoda", 4)
employee.talk("Extend or extend not. There is no try.")
