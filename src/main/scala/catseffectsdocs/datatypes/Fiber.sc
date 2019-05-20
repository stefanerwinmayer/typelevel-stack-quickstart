import cats.effect.{ContextShift, Fiber, IO}

import scala.concurrent.ExecutionContext.global
import cats.implicits._

implicit val ctx: ContextShift[IO] = IO.contextShift(global)

val io: IO[Unit] = IO(println("Hello!"))
val fiber: IO[Fiber[IO, Unit]] = io.start

val launchMissiles = IO.raiseError(new Exception("boom!"))
val runToBunker: IO[Unit] = IO(println("To the bunker!!!"))
for {
  fiber <- launchMissiles.start
  _ <- runToBunker.handleErrorWith { error =>
    // Retreat failed, cancel launch (maybe we should
    // have retreated to our bunker before the launch?)
    fiber.cancel *> IO.raiseError(error)
  }
  aftermath <- fiber.join
} yield aftermath