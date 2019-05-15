package org.typelevel.stack.typelevelstackquickstart.http

import cats.effect._
import cats.syntax.flatMap._
import cats.syntax.functor._
import org.typelevel.stack.typelevelstackquickstart.model._
import org.typelevel.stack.typelevelstackquickstart.service.UserService
import org.typelevel.stack.typelevelstackquickstart.validation.UserValidation
import io.circe.Decoder
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

class UserHttpRoutes[F[_]: Sync](userService: UserService[F])
                                (implicit H: HttpErrorHandler[F]) extends Http4sDsl[F] {

  implicit def createUserDecoder[A : Decoder]: EntityDecoder[F, A] = jsonOf[F, A]

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {

    // Find all users
    case GET -> Root =>
      for {
        users    <- userService.findAll
        response <- users.fold(H.handle, x => Ok(x.asJson))
      } yield response

    // Find user by username
    case GET -> Root / username =>
      for {
        user     <- userService.findUser(UserName(username))
        response <- user.fold(H.handle, x => Ok(x.asJson))
      } yield response

    // Create a user
    case req @ POST -> Root =>
      req.decode[CreateUser] { createUser =>
        UserValidation.validateCreateUser(createUser).fold(
          errors  => BadRequest(errors.toList.asJson),
          user    => userService.addUser(user) flatMap { either =>
            either.fold(H.handle, _ => Created())
          }
        )
      }

    // Update a user
    case req @ PUT -> Root / username =>
      req.decode[UpdateUser] { updateUser =>
        UserValidation.validateUpdateUser(updateUser).fold(
          errors  => BadRequest(errors.toList.asJson),
          email   => userService.updateUser(User(UserName(username), email)) flatMap { either =>
            either.fold(H.handle, _ => Ok())
          }
        )
      }

    // Delete a user
    case DELETE -> Root / username =>
      for {
        result   <- userService.deleteUser(UserName(username))
        response <- result.fold(H.handle, _ => NoContent())
      } yield response

  }

}

