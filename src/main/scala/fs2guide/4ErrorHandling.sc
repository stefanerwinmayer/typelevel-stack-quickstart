import cats.effect.IO
import fs2.Stream

val err = Stream.raiseError[IO](new Exception("oh noes!"))
val err2 = Stream(1, 2, 3) ++ (throw new Exception("!@#$"))
val err3 = Stream.eval(IO(throw new Exception("error in effect!!!")))
try err.compile.toList.unsafeRunSync
  catch { case e: Exception => println(e) }
try err2.toList
  catch { case e: Exception => println(e) }
try err3.compile.drain.unsafeRunSync()
  catch { case e: Exception => println(e) }

// don't use handleErrorWith for resource cleanup
err.handleErrorWith { e => Stream.emit(e.getMessage) }.compile.toList.unsafeRunSync()
