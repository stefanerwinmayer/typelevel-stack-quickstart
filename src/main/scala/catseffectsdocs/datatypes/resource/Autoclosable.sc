import java.io.{BufferedReader, File, FileReader}

import collection.JavaConverters._
import cats.effect.{IO, Resource, Sync}

val acquire: IO[scala.io.Source] = IO {
  scala.io.Source.fromString("Hello world")
}
val resource: Resource[IO, scala.io.Source] =
  Resource.fromAutoCloseable(acquire)
val effect: IO[Unit] = resource.use(source => IO(println(source.mkString)))
effect.unsafeRunSync()

def readAllLines(bufferedReader: BufferedReader): IO[List[String]] = IO {
  bufferedReader.lines().iterator().asScala.toList
}
def reader(file: File): Resource[IO, BufferedReader] =
  Resource.fromAutoCloseable(IO {
    new BufferedReader(new FileReader(file))
  })
def readLinesFromFile(file: File): IO[List[String]] = {
  reader(file).use(readAllLines)
}

def readerGeneric[F[_]](file: File)(
    implicit F: Sync[F]): Resource[F, BufferedReader] =
  Resource.fromAutoCloseable(F.delay {
    new BufferedReader(new FileReader(file))
  })
def dumpResource[F[_]](res: Resource[F, BufferedReader])(
    implicit F: Sync[F]): F[Unit] = {
  def loop(in: BufferedReader): F[Unit] =
    F.suspend {
      val line = in.readLine()
      if (line != null) {
        System.out.println(line)
        loop(in)
      } else {
        F.unit
      }
    }
  res.use(loop)
}
def dumpFile[F[_]](file: File)(implicit F: Sync[F]): F[Unit] =
  dumpResource(readerGeneric(file))
