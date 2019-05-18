/*
package neophyte

object Part14 {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Barista")
    val barista = system.actorOf(Props[Barista], "Barista")
    barista ! CappuccinoRequest
    barista ! EspressoRequest
    system.terminate()
  }

  object Barista extends App {
    override def main(args: Array[String]): Unit = {
      val system = ActorSystem("Barista")
      val barista = system.actorOf(Props[Barista], "Barista")
      barista ! CappuccinoRequest
    }
  }

  sealed trait CoffeeRequest

  case object CappuccinoRequest extends CoffeeRequest

  case object EspressoRequest extends CoffeeRequest

  case class Bill(cents: Int)

  case object ClosingTime

  class Barista extends Actor {
    override def receive: PartialFunction[Any, Unit] = {
      case CappuccinoRequest =>
        sender ! Bill(250)
        println("I have to prepare a cappuccino!")
      case EspressoRequest =>
        sender ! Bill(200)
        println("Let's prepare an espresso.")
      case ClosingTime => context.system.terminate()
    }
  }

  case object CaffeineWithdrawalWarning

  class Customer(caffeineSource: ActorRef) extends Actor {
    def receive = {
      case CaffeineWithdrawalWarning => caffeineSource ! EspressoRequest
      case Bill(cents) => println(s"I have to pay $cents cents, or else!")
    }
  }

}


*/
