package scalawithcats.chapter2MonoidsSemigroups.bool

import cats.Monoid

object BoolMonoids {
  implicit val booleanAndMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      def combine(a: Boolean, b: Boolean) = a && b

      def empty = true
    }

  implicit val booleanOrMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      def combine(a: Boolean, b: Boolean) = a || b

      def empty = false
    }

  implicit val booleanEitherMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    def combine(a: Boolean, b: Boolean) =
      (a && !b) || (!a && b)

    def empty = false
  }

  implicit val booleanXnorMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      def combine(a: Boolean, b: Boolean) =
        (!a || b) && (a || !b)

      def empty = true
    }
}
