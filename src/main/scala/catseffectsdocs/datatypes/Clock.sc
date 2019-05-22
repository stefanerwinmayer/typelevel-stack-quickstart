import cats.effect.{Clock, Sync}
import scala.concurrent.duration.MILLISECONDS
import cats.implicits._

def measure[F[_], A](fa: F[A])(implicit F: Sync[F],
                               clock: Clock[F]): F[(A, Long)] = {
  for {
    start <- clock.monotonic(MILLISECONDS)
    result <- fa
    finish <- clock.monotonic(MILLISECONDS)
  } yield (result, finish - start)
}
