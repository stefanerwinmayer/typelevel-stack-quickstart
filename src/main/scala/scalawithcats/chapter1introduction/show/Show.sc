import java.util.Date

import cats.Show
import cats.instances.int._
import cats.instances.string._
import cats.syntax.show._
import sandbox.chapter1introduction.Cat

val showInt: Show[Int] = Show.apply[Int]
val showString: Show[String] = Show.apply[String]

val intAsString: String = showInt.show(123)
val stringAsString: String = showString.show("abc")

val shownInt = 123.show
val shownString = "abs".show

//implicit val dateShow: Show[Date] =
//  new Show[Date] {
//    def show(date: Date): String =
//      s"${date.getTime}ms since the epoch."
//  }
implicit val dateShow: Show[Date] =
Show.show(date => s"${date.getTime}ms since the epoch.")

new Date().show

Cat("Felix", 2, "Brown").show