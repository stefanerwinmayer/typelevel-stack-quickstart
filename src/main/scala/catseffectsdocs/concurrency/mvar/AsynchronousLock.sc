import cats.effect.IO
import cats.effect.concurrent.MVar

import scala.concurrent.ExecutionContext

implicit val cs = IO.contextShift(ExecutionContext.global)

final class MLock(mvar: MVar[IO, Unit]) {
  def acquire: IO[Unit] =
    mvar.take

  def release: IO[Unit] =
    mvar.put(())

  def greenLight[A](fa: IO[A]): IO[A] =
    acquire.bracket(_ => fa)(_ => release)
}

object MLock {
  def apply(): IO[MLock] =
    MVar[IO].empty[Unit].map(ref => new MLock(ref))
}
