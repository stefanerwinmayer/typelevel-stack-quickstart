import cats.effect.IO
import fs2.Stream

Stream(1, 2).repeat.take(6).toList

Stream(1, 2, 3).drain.toList

Stream.eval_(IO(println("!!"))).compile.toVector.unsafeRunSync()
(Stream(1, 2) ++ (throw new Exception("nooo!!!"))).attempt.toList
