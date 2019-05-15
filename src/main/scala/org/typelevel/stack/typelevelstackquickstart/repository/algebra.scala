package org.typelevel.stack.typelevelstackquickstart.repository

import org.typelevel.stack.typelevelstackquickstart.model.{User, UserName}

object algebra {

  trait UserRepository[F[_]] {
    def findUser(username: UserName): F[Option[User]]
  }

}
