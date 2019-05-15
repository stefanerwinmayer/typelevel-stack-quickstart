package org.typelevel.stack.typelevelstackquickstart.service

import cats.data.EitherT
import org.scalatest.{FlatSpecLike, Matchers}
import org.typelevel.stack.typelevelstackquickstart.IOAssertion
import org.typelevel.stack.typelevelstackquickstart.TestUsers._
import org.typelevel.stack.typelevelstackquickstart.model.{UserName, UserNotFound}

class UserServiceSpec extends FlatSpecLike with Matchers {

  it should "retrieve an user" in IOAssertion {
    EitherT(TestUserService.service.findUser(users.head.username)).map { user =>
      user should be (users.head)
    }.value
  }

  it should "fail retrieving an user" in IOAssertion {
    EitherT(TestUserService.service.findUser(UserName("xxx"))).leftMap { error =>
      error shouldBe a [UserNotFound]
    }.value
  }

}
