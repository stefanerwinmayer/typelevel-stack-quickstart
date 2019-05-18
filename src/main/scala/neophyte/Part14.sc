

def doIt(list: Seq[Int], n: Int): Seq[Int] = {
  if (list.size >= n) {
    list.take(n)
  }
  else {
    list.dropRight(1) ++ doIt(list.reverse, n - list.size + 1)
  }
}
val list = List(1, 2, 3)

doIt(list, 1)
doIt(list, 2)
doIt(list, 3)
doIt(list, 4)
doIt(list, 5)
doIt(list, 6)
doIt(list, 7)


object Barista extends App {
  val system = ActorSystem("Barista")
  val barista = system.actorOf(Props[Barista], "Barista")
  barista ! CappuccinoRequest
}
sealed trait CoffeeRequest
case object CappuccinoRequest extends CoffeeRequest
case object EspressoRequest extends CoffeeRequest

class Barista extends Actor {
  override def receive = {
    case CappuccinoRequest => println("I have to prepare a cappuccino!")
    case EspressoRequest => println("Let's prepare an espresso.")
  }
}

val system = ActorSystem("Barista")
val barista = system.actorOf(Props[Barista], "Barista")
barista ! CappuccinoRequest
