package catseffectsdocs.datatypes.contextshift

import java.util.concurrent.Executors

import cats.effect.{ExitCode, IO, IOApp, Resource, Sync}
import catseffectsdocs.datatypes.contextshift.EvalOn.{
  blockingThreadPool,
  readName
}

import scala.concurrent.ExecutionContext

object EvalOn {
  def blockingThreadPool[F[_]](
      implicit F: Sync[F]): Resource[F, ExecutionContext] =
    Resource(F.delay {
      val executor = Executors.newCachedThreadPool()
      val ec = ExecutionContext.fromExecutor(executor)
      (ec, F.delay(executor.shutdown()))
    })

  def readName[F[_]](implicit F: Sync[F]): F[String] =
    F.delay {
      println("Enter your name: ")
      scala.io.StdIn.readLine()
    }
}

object MyApp extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val name = blockingThreadPool[IO].use { ec =>
      // Blocking operation, executed on special thread-pool
      contextShift.evalOn(ec)(readName[IO])
    }

    for {
      n <- name
      _ <- IO(println(s"Hello, $n!"))
    } yield ExitCode.Success
  }
}
