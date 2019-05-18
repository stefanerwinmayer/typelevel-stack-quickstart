import cats.instances.list._
import cats.instances.option._
import cats.{Id, Monad}

val opt1 = Monad[Option].pure(3)
val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))
val opt3 = Monad[Option].map(opt2)(a => 100 * a)

val list1 = Monad[List].pure(3)
val list2 = Monad[List].flatMap(List(1, 2, 3))(a => List(a, a * 10))
val list3 = Monad[List].map(list2)(a => a + 123)

Monad[Option].flatMap(Option(1))(a => Option(a * 2))
Monad[List].flatMap(List(1, 2, 3))(a => List(a, a * 10))

import cats.instances.vector._

Monad[Vector].flatMap(Vector(1, 2, 3))(a => Vector(a, a * 10))

import cats.syntax.applicative._

1.pure[Option]
1.pure[List]

import cats.syntax.flatMap._
import cats.syntax.functor._

def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
  a.flatMap(x => b.map(y => x * x + y * y))
sumSquare(Option(3), Option(4))
sumSquare(List(1, 2, 3), List(4, 5))

def sumSquare2[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] = for {
  x <- a
  y <- b
} yield x * x + y * y
sumSquare2(Option(3), Option(4))
sumSquare2(List(1, 2, 3), List(4, 5))
sumSquare2(3: Id[Int], 4: Id[Int])
"Dave": Id[String]
123: Id[Int]
List(1, 2, 3): Id[List[Int]]

val a = Monad[Id].pure(3)
val b = Monad[Id].flatMap(a)(_ + 1)
for {
  x <- a
  y <- b
} yield x + y

def pure[A](value: A): Id[A] = value
pure(123)

def map[A, B](initial: Id[A])(func: A => B): Id[B] = func(initial)
def flatMap[A, B](initial: Id[A])(func: A => Id[B]): Id[B] = func(initial)