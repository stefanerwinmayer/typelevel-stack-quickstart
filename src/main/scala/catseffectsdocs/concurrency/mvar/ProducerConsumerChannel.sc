import cats.effect.IO
import cats.effect.concurrent.MVar

// Signaling option, because we need to detect completion
type Channel[A] = MVar[IO, Option[A]]

def producer(ch: Channel[Int], list: List[Int]): IO[Unit] =
  list match {
    case Nil =>
      ch.put(None) // we are done!
    case head :: tail =>
      // next please
      ch.put(Some(head)).flatMap(_ => producer(ch, tail))
  }

def consumer(ch: Channel[Int], sum: Long): IO[Long] =
  ch.take.flatMap {
    case Some(x) =>
      // next please
      consumer(ch, sum + x)
    case None =>
      IO.pure(sum) // we are done!
  }

// ContextShift required for
// 1) MVar.empty
// 2) IO.start
// for ContextShift documentation see https://typelevel.org/cats-effect/datatypes/contextshift.html
import scala.concurrent.ExecutionContext
implicit val cs = IO.contextShift(ExecutionContext.Implicits.global)

for {
  channel <- MVar[IO].empty[Option[Int]]
  count = 10000
  producerTask = producer(channel, (0 until count).toList)
  consumerTask = consumer(channel, 0L)

  fp <- producerTask.start
  fc <- consumerTask.start
  _ <- fp.join
  sum <- fc.join
} yield sum
