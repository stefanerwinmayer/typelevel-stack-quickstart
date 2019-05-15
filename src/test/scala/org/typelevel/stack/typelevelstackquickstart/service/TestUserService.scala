package org.typelevel.stack.typelevelstackquickstart.service

import cats.effect.IO
import org.typelevel.stack.typelevelstackquickstart.TestUsers.users
import org.typelevel.stack.typelevelstackquickstart.model.UserName
import org.typelevel.stack.typelevelstackquickstart.repository.algebra.UserRepository

object TestUserService {

  private val testUserRepo: UserRepository[IO] =
    (username: UserName) => IO {
      users.find(_.username.value == username.value)
    }

  val service: UserService[IO] = new UserService[IO](testUserRepo)

}
