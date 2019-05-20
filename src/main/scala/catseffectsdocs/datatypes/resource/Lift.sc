import cats.effect.{IO, Resource}

val greet: String => IO[Unit] = x => IO(println("Hello " ++ x))

val resource: Resource[IO, String] = Resource.liftF(IO.pure("World"))

val effect: IO[Unit] = resource.use(greet)

effect.unsafeRunSync()
