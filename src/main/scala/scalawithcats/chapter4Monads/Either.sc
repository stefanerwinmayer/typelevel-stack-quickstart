val either1: Either[String, Int] = Right(10)
val either2: Either[String, Int] = Right(32)

for {
  a <- either1
  b <- either2
} yield a + b

import cats.syntax.either._

val a = 3.asRight[String]
val b = 4.asRight[String]

for {
  x <- a
  y <- b
} yield x * x + y * y

def countPositive(nums: List[Int]) =
  nums.foldLeft(0.asRight[String]) { (accumulator, num) =>
    if (num > 0) {
      accumulator.map(_ + 1)
    } else {
      Left("Negative. Stopping!")
    }
  }
countPositive(List(1, 2, 3))
countPositive(List(1, -2, 3))

import cats.syntax.either._

Either.catchOnly[NumberFormatException]("foo".toInt)
Either.catchNonFatal(sys.error("Badness"))
Either.fromTry(scala.util.Try("foo".toInt))
Either.fromOption[String, Int](None, "Badness")

"Error".asLeft[Int].getOrElse(0)
"Error".asLeft[Int].orElse(2.asRight[String])
-1.asRight[String].ensure("Must be non-negative")(_ > 0)
"error".asLeft[Int].recover {
  case str: String => -1
}
"error".asLeft[Int].recoverWith {
  case str: String => Right(-1)
}
"foo".asLeft[Int].leftMap(_.reverse)
6.asRight[String].bimap(_.reverse, _ * 7)
"bar".asLeft[Int].bimap(_.reverse, _ * 7)
123.asRight[String].swap

for {
  a <- 1.asRight[String]
  b <- 0.asRight[String]
  c <- if (b == 0) "DIV0".asLeft[Int]
  else (a / b).asRight[String]
} yield c * 100

sealed trait LoginError extends Product with Serializable

final case class UserNotFound(username: String) extends LoginError

final case class PasswordIncorrect(username: String) extends LoginError

case object UnexpectedError extends LoginError

case class User(username: String, password: String)

type LoginResult = Either[LoginError, User]

def handleError(error: LoginError): Unit =
  error match {
    case UserNotFound(u) => println(s"User not found: $u")
    case PasswordIncorrect(u) => println(s"Password incorrect: $u")
    case UnexpectedError => println(s"Unexpected error")
  }

val result1: LoginResult = User("dave", "passw0rd").asRight
val resutl2: LoginResult = UserNotFound("dave").asLeft
result1.fold(handleError, println)
resutl2.fold(handleError, println)
