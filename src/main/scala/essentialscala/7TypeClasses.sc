val minOrdering = Ordering.fromLessThan[Int](_ < _)
val maxOrdering = Ordering.fromLessThan[Int](_ > _)
List(3, 4, 2).sorted(minOrdering)
List(3, 4, 2).sorted(maxOrdering)

implicit val absOrdering = Ordering.fromLessThan[Int] { (x, y) =>
  Math.abs(x) < Math.abs(y)
}
assert(List(-4, -1, 0, 2, 3).sorted(absOrdering) == List(0, -1, 2, 3, -4))
assert(List(-4, -3, -2, -1).sorted(absOrdering) == List(-1, -2, -3, -4))
assert(List(-4, -1, 0, 2, 3).sorted == List(0, -1, 2, 3, -4))
assert(List(-4, -3, -2, -1).sorted == List(-1, -2, -3, -4))

final case class Rational(numerator: Int, denominator: Int)

implicit val ordering = Ordering.fromLessThan[Rational] { (x, y) =>
  (x.numerator.toDouble / x.denominator.toDouble) <
    (y.numerator.toDouble / y.denominator.toDouble)
}
assert(List(Rational(1, 2), Rational(3, 4), Rational(1, 3)).sorted == List(Rational(1, 3), Rational(1, 2), Rational(3, 4)))

final case class Order(units: Int, unitPrice: Double) {
  val totalPrice: Double = units * unitPrice
}

object Order {
  implicit val lessThanOrdering = Ordering.fromLessThan[Order] { (x, y) =>
    x.totalPrice < y.totalPrice
  }
}

object OrderUnitPriceOrdering {
  implicit val unitPriceOrdering = Ordering.fromLessThan[Order] { (x, y) =>
    x.unitPrice < y.unitPrice
  }
}

object OrderUnitsOrdering {
  implicit val unitsOrdering = Ordering.fromLessThan[Order] { (x, y) =>
    x.units < y.units
  }
}

final case class Person(name: String, email: String)

trait HtmlWriter[A] {
  def toHtml(in: A): String
}

object HtmlWriter {
  def apply[A](implicit writer: HtmlWriter[A]): HtmlWriter[A] =
    writer
}

object PersonWriter extends HtmlWriter[Person] {
  def toHtml(person: Person) = s"${person.name} (${person.email})"
}

object ObfuscatedPersonWriter extends HtmlWriter[Person] {
  def toHtml(person: Person) =
    s"${person.name} (${person.email.replaceAll("@", " at ")})"
}

trait Equal[A] {
  def equal(v1: A, v2: A): Boolean
}

implicit object EmailEqual extends Equal[Person] {
  def equal(v1: Person, v2: Person): Boolean =
    v1.email == v2.email
}

object NameEmailEqual extends Equal[Person] {
  def equal(v1: Person, v2: Person): Boolean =
    v1.email == v2.email && v1.name == v2.name
}

object Eq {
  def apply[A](v1: A, v2: A)(implicit equal: Equal[A]) = {
    equal.equal(v1, v2)
  }
}

val samePerson =  Eq(Person("Noel", "noel@example.com"), Person("Noel", "noel@example. com"))

implicit object ApproximationWriter extends HtmlWriter[Int] {
  def toHtml(in: Int): String =
    s"It's definitely less than ${((in / 10) + 1) * 10}"
}

object HtmlUtil {
  def htmlify[A](data: A)(implicit writer: HtmlWriter[A]): String = {
    writer.toHtml(data)
  }
}

HtmlUtil.htmlify(Person("John", "john@example.com"))(ObfuscatedPersonWriter)
HtmlUtil.htmlify(2)

HtmlWriter[Int].toHtml(2)