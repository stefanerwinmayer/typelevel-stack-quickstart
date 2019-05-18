import cats.effect.{ContextShift, IO}

implicit val ioContextShift: ContextShift[IO] =
    IO.contextShift(scala.concurrent.ExecutionContext.Implicits.global)
fs2.Stream(1, 2, 3).merge(fs2.Stream.eval(IO { Thread.sleep(200); 4 })).compile.toVector.unsafeRunAsync(println)
