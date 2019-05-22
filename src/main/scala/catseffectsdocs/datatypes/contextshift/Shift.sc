import cats.effect.{ContextShift, Sync}
import cats.implicits._

def fib[F[_]](n: Int, a: Long = 0, b: Long = 1)(
    implicit F: Sync[F],
    cs: ContextShift[F]): F[Long] = {
  F.suspend {
    val next =
      if (n > 0) fib(n - 1, b, a + b)
      else F.pure(a)

    // Triggering a logical fork every 100 iterations
    if (n % 100 == 0)
      cs.shift *> next
    else
      next
  }
}
