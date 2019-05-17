import cats.effect.IO

// Pure values. Pure evaluates eagerly
val pure = IO.pure(25)
val unit = pure.flatMap(n => IO(println(s"Number is: $n")))
val unitIsPureEmpty = IO.unit == IO.pure()
