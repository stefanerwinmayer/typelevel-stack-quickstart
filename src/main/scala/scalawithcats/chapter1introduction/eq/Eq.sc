import java.util.Date

import cats.Eq
import cats.instances.int._
import cats.instances.long._
import cats.instances.option._
import cats.syntax.eq._
import cats.syntax.option._
import sandbox.chapter1introduction.Cat

val eqInt = Eq[Int]
eqInt.eqv(123, 123)
eqInt.eqv(123, 234)
//eqInt.eqv(123, "234")
123 === 123
123 =!= 234
//123 === "123"
Option(1) === Option.empty[Int]
1.some === none[Int]
1.some =!= none[Int]

implicit val dateEq: Eq[Date] =
  Eq.instance { (date1, date2) =>
    date1.getTime === date2.getTime
  }

val x = new Date()
val y = new Date()
x === x
x === y

val cat1 = Cat("Garfield", 38, "orange and black")
val cat2 = Cat("Heathcliff", 33, "orange and black")
cat1 === cat2
cat1 =!= cat2

val optionCat1 = Option(cat1)
val optionCat2 = Option.empty[Cat]
optionCat1 === optionCat2
optionCat1 =!= optionCat2
