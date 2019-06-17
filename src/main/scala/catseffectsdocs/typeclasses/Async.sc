import cats.effect.{Async, IO}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

val apiCall = Future.successful("I come from the Future!")

val ioa: IO[String] =
  Async[IO].async { cb =>
    import scala.util.Failure

    apiCall.onComplete {
      case scala.util.Success(value) => cb(Right(value))
      case Failure(error)            => cb(Left(error))
    }
  }

ioa.unsafeRunSync()
