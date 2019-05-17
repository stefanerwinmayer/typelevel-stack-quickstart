import cats.effect.{ContextShift, Fiber, IO}
import cats.implicits._

import scala.concurrent.ExecutionContext

implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

val launchMissles = IO.raiseError(new Exception("boom!"))
val runToBunker = IO(println("To the bunker!!!!"))

val program = for {
  fiber <- launchMissles.start
  _ <- runToBunker.handleErrorWith { error =>
    fiber.cancel *> IO.raiseError(error)
  }
  aftermath <- fiber.join
} yield {
  aftermath
}
program.unsafeRunSync()
