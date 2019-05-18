object Math {

  trait NumberLike[T] {
    def plus(x: T, y: T): T

    def divide(x: T, y: Int): T

    def minus(x: T, y: T): T

  }

  object NumberLike {

    implicit object NumberLikeDouble extends NumberLike[Double] {
      override def plus(x: Double, y: Double) = x + y

      override def divide(x: Double, y: Int) = x / y

      override def minus(x: Double, y: Double) = x - y
    }

    implicit object NumberLikeInt extends NumberLike[Int] {
      override def plus(x: Int, y: Int) = x + y

      override def divide(x: Int, y: Int) = x / y

      override def minus(x: Int, y: Int) = x - y
    }

  }

}

object Statistics {

  import Math.NumberLike

  def mean[T](xs: Vector[T])(implicit ev: NumberLike[T]): T =
    ev.divide(xs.reduce(ev.plus(_, _)), xs.size)

  def median[T: NumberLike](xs: Vector[T]): T = xs(xs.size / 2)

  def quartiles[T: NumberLike](xs: Vector[T]): (T, T, T) =
    (xs(xs.size / 4), median(xs), xs(xs.size / 4 * 3))

  def iqr[T: NumberLike](xs: Vector[T]): T = quartiles(xs) match {
    case (lowerQuartile, _, upperQuartile) =>
      implicitly[NumberLike[T]].minus(upperQuartile, lowerQuartile)
  }
}

//object JodaImplicits {
//
//  import Math.NumberLike
//  import org.joda.time.Duration
//
//  implicit object NumberLikeDuration extends NumberLike[Duration] {
//    override def plus(x: Duration, y: Duration) = x.plus(y)
//
//    override def divide(x: Duration, y: Int) = Duration.millis(x.getMillis / y)
//
//    override def minus(x: Duration, y: Duration) = x.minus(y)
//  }
//
//}

//val durations = Vector(standardSeconds(20), standardSeconds(57), standardMinutes(2),
//  standardMinutes(17), standardMinutes(30), standardMinutes(58), standardHours(2),
//  standardHours(5), standardHours(8), standardHours(17), standardDays(1),
//  standardDays(4))
//println(Statistics.mean(durations).getStandardHours)

def double[T](x: T)(implicit ev: Numeric[T]) = ev.times(x, x)
double(2)
