package catseffectsdocs.datatypes.ioapp
import cats.effect.ExitCase.Canceled
import cats.syntax.all._
import cats.effect.{ExitCode, IO, IOApp}

import scala.concurrent.duration._

object Main2 extends IOApp {

  def loop(n: Int): IO[ExitCode] =
    IO.suspend {
      if (n < 10) {
        IO.sleep(1.second) *> IO(println(s"Tick: $n")) *> loop(n + 1)
      } else {
        IO.pure(ExitCode.Success)
      }
    }

  override def run(args: List[String]): IO[ExitCode] =
    loop(0).guaranteeCase {
      case Canceled =>
        IO(println("Interrupted: releasing and exiting"))
      case _ =>
        IO(println("Normal exit!"))
    }
}
