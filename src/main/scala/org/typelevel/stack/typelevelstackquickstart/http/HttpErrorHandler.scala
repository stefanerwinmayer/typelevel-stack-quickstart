package org.typelevel.stack.typelevelstackquickstart.http

import cats.Monad
import org.http4s.Response
import org.http4s.dsl.Http4sDsl
import org.typelevel.stack.typelevelstackquickstart.model.{ApiError, UserNotFound}

class HttpErrorHandler[F[_]: Monad] extends Http4sDsl[F] {

  // Map your business errors to responses here
  val handle: ApiError => F[Response[F]] = {
    case UserNotFound(u) => NotFound(s"User not found ${u.value}")
  }

}
