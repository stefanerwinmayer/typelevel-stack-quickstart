import cats.instances.int._
import cats.instances.option._
import cats.instances.string._
import cats.syntax.monoid._
import cats.{Monoid, Semigroup}

def associativeLaw[A](x: A, y: A, z: A)(implicit m: Monoid[A]): Boolean = {
  m.combine(x, m.combine(y, z)) ==
    m.combine(m.combine(x, y), z)
}

def identityLaw[A](x: A)(implicit m: Monoid[A]) =
  (m.combine(x, m.empty) == x) &&
    (m.combine(m.empty, x) == x)

associativeLaw("a", "b", "c")
identityLaw("a")

Monoid[String].combine("Hi ", "there")
Monoid[String].empty
Semigroup[String].combine("Hi ", "there")
Monoid[Int].combine(32, 10)
val a = Option(22)
val b = Option(20)
Monoid[Option[Int]].combine(a, b)


val stringResult = "Hi " |+| "there" |+| Monoid[String].empty
val intResult = 1 |+| 2 |+| Monoid[Int].empty

def add[A](items: List[A])(implicit monoid: Monoid[A]): A =
  items.foldLeft(monoid.empty)(_ |+| _)
def add2[A: Monoid](items: List[A]): A =
  items.foldLeft(Monoid[A].empty)(_ |+| _)

add(List(1, 2, 3))
add(List(Some(1), None, Some(2), None, Some(3)))

case class Order(totalCost: Double, quantity: Double)

implicit val monoid: Monoid[Order] = new Monoid[Order] {
  def combine(o1: Order, o2: Order) =
    Order(
      o1.totalCost + o2.totalCost,
      o1.quantity + o2.quantity
    )
  def empty = Order(0, 0)
}

add(List(Order(1, 1), Order(2, 2)))
