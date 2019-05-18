package essentialscala

final case class Rational(numerator: Int, denominator: Int)

object Rational {
  implicit val ordering = Ordering.fromLessThan[Rational]((x, y) =>
    (x.numerator.toDouble / x.denominator.toDouble) <
      (y.numerator.toDouble / y.denominator.toDouble))
}

object RationalGreaterThanOrdering {
  implicit val ordering = Ordering.fromLessThan[Rational]((x, y) =>
    (x.numerator.toDouble / x.denominator.toDouble) >
      (y.numerator.toDouble / y.denominator.toDouble))
}

object Example {
  def example =
    assert(List(Rational(1, 2), Rational(3, 4), Rational(1, 3)).sorted ==
      List(Rational(1, 3), Rational(1, 2), Rational(3, 4)))

  import RationalGreaterThanOrdering._

  def example2 =
    assert(List(Rational(1, 2), Rational(3, 4), Rational(1, 3)).sorted ==
      List(Rational(3, 4), Rational(1, 2), Rational(1, 3)))
}

object Test extends App {
  Example.example
  Example.example2
}