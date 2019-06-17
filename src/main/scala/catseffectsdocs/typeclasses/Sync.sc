import cats.effect.{IO, Sync}
import cats.laws._


val F = Sync[IO]

lazy val stackSafetyOnRepeatedRightBinds = {
  val result = (0 until 10000).foldRight(F.delay(())) { (_, acc) =>
    F.delay(()).flatMap(_ => acc)
  }
}