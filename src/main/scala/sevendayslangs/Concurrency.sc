

case object Poke

case object Feed

class Kid extends Actor {
  override def receive = {
    case Poke => {
      println("Ow...")
      println("Quit it...")
    }
    case Feed => {
      println("Gurgle...")
      println("Burp...")
    }
  }
}

val system = ActorSystem("Kids")
val bart = system.actorOf(Props[Kid])
val lisa = system.actorOf(Props[Kid])

println("Ready to poke and feed...")
bart ! Poke
lisa ! Poke
bart ! Feed
lisa ! Feed