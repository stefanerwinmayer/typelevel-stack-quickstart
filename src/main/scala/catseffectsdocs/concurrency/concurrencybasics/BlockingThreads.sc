import java.util.concurrent.Executors

import cats.effect.{ContextShift, IO}

import scala.concurrent.ExecutionContext

implicit val contextShift: ContextShift[IO] =
  IO.contextShift(ExecutionContext.global)
val blockingEC =
  ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

def blockingOp(): IO[Unit] =
  IO(println(s" ${Thread.currentThread().getName}: Do blocking op"))
def doSth(): IO[Unit] =
  IO(println(s"${Thread.currentThread().getName}: Do something"))

val prog =
  for {
    _ <- contextShift.evalOn(blockingEC)(blockingOp) // executes on blockingEC
    _ <- doSth() // executes on contextShift
  } yield ()

prog.unsafeRunSync()
