sealed trait TrafficLight

case object Red extends TrafficLight

case object Yellow extends TrafficLight

case object Green extends TrafficLight

sealed trait Calculation

case class Fail(message: String) extends Calculation

case class Success(value: Int) extends Calculation

sealed trait Source

case object Well extends Source

case object Spring extends Source

case object Tap extends Source

case class BottledWater(size: Int, source: Source, carbonated: Boolean)