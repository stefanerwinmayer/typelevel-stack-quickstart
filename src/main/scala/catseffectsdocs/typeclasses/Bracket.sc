import cats.MonadError

sealed abstract class ExitCase[+E]

trait Bracket[F[_], E] extends MonadError[F, E] {
  def bracketCase[A, B](acquire: F[A])(use: A => F[B])(
      release: (A, ExitCase[E]) => F[Unit]): F[B]

  // Simpler version, doesn't distinguish b/t exit conditions
  def bracket[A, B](acquire: F[A])(use: A => F[B])(release: A => F[Unit]): F[B]
}
