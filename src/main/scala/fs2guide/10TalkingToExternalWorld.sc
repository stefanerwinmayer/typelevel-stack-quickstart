import cats.effect.{IO, Sync}
import fs2.Stream

def destroyUniverse(): Unit = println("BOOOOM!!!")

val s = Stream.eval_(IO { destroyUniverse() }) ++ Stream("...moving on")
s.compile.toVector.unsafeRunSync()

val T = Sync[IO]
val s2 = Stream.eval_(T.delay { destroyUniverse() }) ++ Stream("...moving on")
s2.compile.toVector.unsafeRunSync()

trait Connection {
  def readBytes(onSuccess: Array[Byte] => Unit, onFailure: Throwable => Unit): Unit

  // or perhaps
  def readBytesE(onComplete: Either[Throwable, Array[Byte]] => Unit): Unit =
    readBytes(bs => onComplete(Right(bs)), e => onComplete(Left(e)))

  override def toString = "<connection>"
}

val c = new Connection {
    def readBytes(onSuccess: Array[Byte] => Unit, onFailure: Throwable => Unit): Unit = {
      Thread.sleep(200)
      onSuccess(Array(0, 1, 2))
    }
  }

val bytes =
    cats.effect.Async[IO].async[Array[Byte]] { (cb: Either[Throwable, Array[Byte]] => Unit) =>
      c.readBytesE(cb)
    }

Stream.eval(bytes).map(_.toList).compile.toVector.unsafeRunSync()
