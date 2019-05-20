import cats.effect.{IO, Resource}
import cats.implicits._

val acquire: IO[String] = IO(println("Acquire cats...")) *> IO("cats")
val release: String => IO[Unit] = x => IO(println("...release everything"))
val addDogs: String => IO[String] = x =>
  IO(println("...more animals...")) *> IO.pure(x ++ " and dogs")
val report: String => IO[String] = x =>
  IO(println("...produce weather report...")) *> IO("It's raining " ++ x)

val resource: Resource[IO, String] = Resource.make(acquire)(release)
val transformedResource: Resource[IO, String] = resource.evalMap(addDogs)
val effect: IO[String] = transformedResource.use(report)
effect.unsafeRunSync()
