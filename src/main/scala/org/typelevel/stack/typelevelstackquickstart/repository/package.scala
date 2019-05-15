package org.typelevel.stack.typelevelstackquickstart

import org.typelevel.stack.typelevelstackquickstart.model._

package object repository {

  type UserDTO = (Int, String, String)

  implicit class UserConversions(dto: UserDTO) {
    def toUser: User = User(
      username = UserName(dto._2),
      email = Email(dto._3)
    )
  }

}
