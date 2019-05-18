case class Player(name: String, score: Int)

def message(player: Player) = player match {
  case Player(_, score) if score > 100000 => "Get a job, dude!"
  case Player(name, _) => "Hey " + name + ", nice to see you again"
}
message(Player("Stefan", 0))
message(Player("Alex", 1000000000))

def currentPlayer(): Player = Player("Daniel", 3500)
def doSomethingWithTheName(name: String) = println(name)
val player = currentPlayer()
doSomethingWithTheName(player.name)

val Player(name, _) = currentPlayer()
doSomethingWithTheName(name)
def scores: List[Int] = List(500, 100)
val best :: rest = scores
println("The score of our champion is " + best)
def gameResult(): (String, Int) = ("Daniel", 3500)
val (name2, score) = gameResult()
println(name2 + ": " + score)

def gameResults(): Seq[(String, Int)] =
  ("Daniel", 3500) :: ("Melissa", 13000) :: ("John", 7000) :: Nil

def hallOfFame = for {
  (name, score) <- gameResults()
  if (score > 5000)
} yield name
hallOfFame

val lists = List(1, 2, 3) :: List.empty :: List(5, 3) :: Nil

for {
  list@head :: _ <- lists
} yield list.size

lazy val fibs: Stream[Int] = 0 #:: fibs.scanLeft(1)(_ + _)
fibs take 10 toList