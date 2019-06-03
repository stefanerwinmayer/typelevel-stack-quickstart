import cats.effect.IO
import cats.effect.concurrent._

import scala.concurrent.ExecutionContext

implicit val cs = IO.contextShift(ExecutionContext.global)

def sum(state: MVar[IO, Int], list: List[Int]): IO[Int] =
  list match {
    case Nil => state.take
    case x :: xs =>
      state.take.flatMap { current =>
        state.put(current + x).flatMap(_ => sum(state, xs))
      }
  }

MVar.of[IO, Int](0).flatMap(sum(_, (0 until 100).toList))