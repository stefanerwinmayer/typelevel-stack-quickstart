import cats.Functor
import cats.instances.list._
import cats.instances.option._
import cats.syntax.functor._

import scala.language.higherKinds

val func1 = (a: Int) => a + 1
val func2 = (a: Int) => a * 2
val func3 = (a: Int) => a + "!"
//val func4 = func1.map(func2).map(func3)
//func4(123)

def doMath[F[_]](start: F[Int])
                (implicit functor: Functor[F]): F[Int] =
  start.map(n => n + 1 * 2)

doMath(Option(20))
doMath(List(1, 2, 3))

implicit val optionFunctor: Functor[Option] =
  new Functor[Option] {
    def map[A, B](value: Option[A])(func: A => B): Option[B] =
      value.map(func)
  }
