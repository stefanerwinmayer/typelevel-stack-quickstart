import cats.effect.IO

// Introduction
val ioa = IO { println("hey!") }
val program: IO[Unit] =
  for {
    _ <- ioa
    _ <- ioa
  } yield ()
program.unsafeRunSync()
