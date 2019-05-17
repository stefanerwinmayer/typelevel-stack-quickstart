import java.util.concurrent.ScheduledExecutorService

import cats.effect.IO

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.FiniteDuration
import scala.util.{Failure, Success}

// Fabio said to deal with async later

// Asyncronous effects
def convert[A](fa: => Future[A])(implicit ex: ExecutionContext): IO[A] =
  IO.async { cb =>
    // This triggers evaluation of the by-name param and of onComplete,
    // so it's OK to have side effects in this callback
    fa.onComplete {
      case Success(a) => cb(Right(a))
      case Failure(e) => cb(Left(e))
    }
  }
def delayedTick(d: FiniteDuration)(
    implicit sc: ScheduledExecutorService): IO[Unit] = {
  IO.cancelable { cb =>
    val r = new Runnable { def run() = cb(Right()) }
    val f = sc.schedule(r, d.length, d.unit)
    // Returning a function that can cancel our scheduling
    IO(f.cancel(false))
  }
}
// IO.never represents a non-terminating IO defined in terms of async,
// useful as shortcut and as a reusable reference:
val never = IO.never
