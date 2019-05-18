import cats.Monoid
import sandbox.chapter2MonoidsSemigroups.set.SetMonoids._

val intSetMonoid = Monoid[Set[Int]]
val strSetMonoid = Monoid[Set[String]]
intSetMonoid.combine(Set(1, 2), Set(2, 3))
strSetMonoid.combine(Set("A", "B"), Set("B", "C"))