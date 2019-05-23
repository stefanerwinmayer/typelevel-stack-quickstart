package catseffectsdocs.datatypes.ioapp

import cats.effect._
import cats.syntax.all._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] =
    args.headOption match {
      case Some(name) =>
        IO(println(s"Hello, $name.")).as(ExitCode.Success)
      case None =>
        IO(System.err.println("Usage: MyApp name")).as(ExitCode(2))
    }
}
