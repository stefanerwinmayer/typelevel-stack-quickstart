import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

// Some type aliases, just for getting more meaningful method signatures:
type CoffeeBeans = String
type GroundCoffee = String

case class Water(temperature: Int)

type Milk = String
type FrothedMilk = String
type Espresso = String
type Cappuccino = String

// some exceptions for things that might go wrong in the individual steps
// (we'll need some of them later, use the others when experimenting
// with the code):
case class GrindingException(msg: String) extends Exception(msg)

case class FrothingException(msg: String) extends Exception(msg)

case class WaterBoilingException(msg: String) extends Exception(msg)

case class BrewingException(msg: String) extends Exception(msg)

def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
  println("start grinding...")
  Thread.sleep(Random.nextInt(2000))
  if (beans == "baked beans") throw GrindingException("are you joking?")
  println("finished grinding...")
  s"ground coffee of $beans"
}
grind.curr





