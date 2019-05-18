import cats.effect.IO
import fs2.Stream

val appendEx1 = Stream(1, 2, 3) ++ Stream.emit(42)
val appendEx2 = Stream(1, 2, 3) ++ Stream.eval(IO.pure(4))
appendEx1.toVector
appendEx2.compile.toVector.unsafeRunSync()
appendEx1.map(_ + 1).toList
appendEx1.flatMap(i => Stream.emits(List(i ,i))).toList
