import cats._
import cats.data._
import cats.implicits._

Functor[List].map(List(1, 2, 3, 4)) ({(_: Int) * (_:Int)}.curried)

(3.some, 5.some) mapN { _ - _ }

1.some *> 2.some <* 3.some

1.some product 2.some

1 |+| 2

Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6)

val source = List("Cats", "is", "awesome")
val product = Functor[List].fproduct(source)(_.length).toMap

val composed = Functor[List] compose Functor[Option]
composed.map(List(Some(1)))(_ + 1)

val addArity2 = (a: Int, b: Int) => a + b
val applyBuilder = Option(1) |@| Option(2)
applyBuilder map addArity2

Monad[Option].pure(1)

(List(1, 2, 3), List(10, 20, 30)) mapN (_ * _)