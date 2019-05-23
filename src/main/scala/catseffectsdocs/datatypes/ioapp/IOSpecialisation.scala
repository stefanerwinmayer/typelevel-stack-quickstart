package catseffectsdocs.datatypes.ioapp

import cats.data.EitherT
import cats.effect.{ConcurrentEffect, ExitCode, IO, IOApp}

object Main3 extends IOApp {

  type F[A] = EitherT[IO, Throwable, A]
  val F = implicitly[ConcurrentEffect[F]]

  override def run(args: List[String]): IO[ExitCode] =
    F.toIO {
      EitherT
        .right(IO(println("Hello from EitherT")))
        .map(_ => ExitCode.Success)
    }
}
