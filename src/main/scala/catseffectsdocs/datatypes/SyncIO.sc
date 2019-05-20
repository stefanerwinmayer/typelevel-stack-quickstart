import cats.Eval
import cats.effect.{IO, SyncIO}

def putStrLn(str: String): SyncIO[Unit] = SyncIO(println(str))
SyncIO.pure("Cats!").flatMap(putStrLn).unsafeRunSync()

val eval: Eval[String] = Eval.now("hey!")
SyncIO.eval(eval).unsafeRunSync()

val ioa: SyncIO[Unit] = SyncIO(println("Hello world!"))
val iob: IO[Unit] = ioa.to[IO]
iob.unsafeRunAsync(_ => ())
