import cats.Functor

import scala.concurrent.{Await, Future}

List(1, 2, 3).map(n => n + 1)

List(1, 2, 3).
  map(n => n + 1).
  map(n => n * 2).
  map(n => n + "!")

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
val future: Future[String] = {
  Future(123).
    map(n => n + 1).
    map(n => n * 2).
    map(n => n + "!")
}
Await.result(future, 1.second)

import cats.instances.function._ // for Functor
import cats.syntax.functor._     // for map
val func1: Int => Double =
  (x: Int) => x.toDouble
val func2: Double => Double =
  (y: Double) => y * 2
//(func1 map func2)(1)
(func1 andThen func2)(1)
func2(func1(1))

//val func =
//  ((x: Int) => x.toDouble).
//    map(x => x + 1).
//    map(x => x * 2).
//    map(x => x + "!")
//func(123)

import cats.instances.list._
import cats.instances.option._
val list1 = List(1, 2, 3)
val list2 = Functor[List].map(list1)(_ * 2)
val option1 = Option(123)
val option2 = Functor[Option].map(option1)(_.toString)
val func = (x: Int) => x + 1
val liftedFunc = Functor[Option].lift(func)
liftedFunc(Option(1))
