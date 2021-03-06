import java.util.concurrent.ScheduledExecutorService

import cats.effect.{Clock, IO, Timer}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration.{
  FiniteDuration,
  MILLISECONDS,
  NANOSECONDS,
  TimeUnit
}

final class MyTimer(ec: ExecutionContext, sc: ScheduledExecutorService)
    extends Timer[IO] {
  override def clock: Clock[IO] =
    new Clock[IO] {
      override def realTime(unit: TimeUnit): IO[Long] =
        IO(unit.convert(System.currentTimeMillis(), MILLISECONDS))

      override def monotonic(unit: TimeUnit): IO[Long] =
        IO(unit.convert(System.nanoTime(), NANOSECONDS))
    }

  override def sleep(timespan: FiniteDuration): IO[Unit] =
    IO.cancelable { cb =>
      val tick = new Runnable {
        override def run() =
          ec.execute(new Runnable {
            override def run(): Unit = cb(Right())
          })
      }
      val f = sc.schedule(tick, timespan.length, timespan.unit)
      IO(f.cancel(false))
    }
}
