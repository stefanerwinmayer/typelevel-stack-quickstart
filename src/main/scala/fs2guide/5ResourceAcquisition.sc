import cats.effect.IO
import fs2.Stream

val err = Stream.raiseError[IO](new Exception("oh noes!"))
val count = new java.util.concurrent.atomic.AtomicLong(0)
val acquire = IO { println("incremented: " + count.incrementAndGet); () }
val release = IO { println("decremented: " + count.decrementAndGet); () }

Stream.bracket(acquire)(_ => release).flatMap(_ => Stream(1, 2, 3) ++ err).compile.drain.unsafeRunSync()

count.get
