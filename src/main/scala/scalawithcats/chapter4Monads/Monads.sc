def parseInt(str: String): Option[Int] =
  scala.util.Try(str.toInt).toOption
def divide(a: Int, b: Int): Option[Int] =
  if (b == 0) None else Some(a / b)
def stringDivideBy(aStr: String, bStr: String): Option[Int] =
  parseInt(aStr).flatMap { aNum =>
    parseInt(bStr).flatMap { bNum =>
      divide(aNum, bNum)
    }
  }

stringDivideBy("6", "2")
stringDivideBy("6", "0")
stringDivideBy("6", "foo")
stringDivideBy("bar", "2")

def stringDivideBy2(aStr: String, bStr: String): Option[Int] =
  for {
    aNum <- parseInt(aStr)
    bNum <- parseInt(bStr)
    ans <- divide(aNum, bNum)
  } yield ans

stringDivideBy2("6", "2")

for {
  x <- (1 to 3).toList
  y <- (4 to 5).toList
} yield (x, y)

trait Monad[F[_]] {
  def pure[A](value: A): F[A]

  def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

  def map[A, B](value: F[A])(func: A => B): F[B] =
    flatMap(value)(a => pure(func(a)))
}