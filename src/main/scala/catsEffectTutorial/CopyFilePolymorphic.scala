package catsEffectTutorial

import java.io._

import cats.effect.concurrent.Semaphore
import cats.effect._
import cats.implicits._

object CopyFilePolymorphic extends IOApp {

  def inputStream[F[_]: Sync](f: File, guard: Semaphore[F]): Resource[F, FileInputStream] =
    Resource.make {
      Sync[F].delay(new FileInputStream(f)) // create the resource
    } { inputStream =>
      // close the resource
      guard.withPermit {
        Sync[F].delay(inputStream.close()).handleErrorWith(_ => Sync[F].unit) // what do do when there is an error
      }
    }

  def outputStream[F[_]: Sync](f: File, guard: Semaphore[F]): Resource[F, FileOutputStream] =
    Resource.make {
      Sync[F].delay(new FileOutputStream(f))
    } { outStream =>
      guard.withPermit {
        Sync[F].delay(outStream.close()).handleErrorWith(_ => Sync[F].unit)
      }
    }

  def inputOutputStreams[F[_]: Sync](in: File,
                                     out: File,
                                     guard: Semaphore[F]): Resource[F, (FileInputStream, FileOutputStream)] =
    for {
      inStream <- inputStream(in, guard)
      outStream <- outputStream(out, guard)
    } yield (inStream, outStream)

  // alternative for resources that implement java.lang.AutoClosable but does not allow logging, add custom error handling, etc.
  def inputStreamAuto(f: File): Resource[IO, FileInputStream] =
    Resource.fromAutoCloseable(IO(new FileInputStream(f)))

  def transmit[F[_]: Sync](origin: InputStream, destination: OutputStream, buffer: Array[Byte], acc: Long): F[Long] =
    for {
      amount <- Sync[F].delay(origin.read(buffer, 0, buffer.size)) // read returns number of bytes read, or -1 if nothing left
      // >> is the same as first.flatMap(_ => second)
      count <- if (amount > -1)
        Sync[F].delay(destination.write(buffer, 0, amount)) >> transmit(origin, destination, buffer, acc + amount)
      else Sync[F].pure(acc)
    } yield count

  def transfer[F[_]: Sync](origin: InputStream, destination: OutputStream): F[Long] =
    for {
      buffer <- Sync[F].delay(new Array[Byte](1024 * 10))
      total <- transmit(origin, destination, buffer, 0)
    } yield total

  def copy[F[_]: Concurrent](origin: File, destination: File): F[Long] =
    for {
      guard <- Semaphore[F](1)
      count <- inputOutputStreams(origin, destination, guard).use {
        case (in, out) =>
          guard.withPermit(transfer(in, out))
      }
    } yield count

  def verifyInputs(args: List[String]): IO[Unit] =
    if (args.length < 2) IO.raiseError(new IllegalArgumentException("Need origin and destination first"))
    else if (args(0) == args(1)) IO.raiseError(new IllegalArgumentException("Origin and destination must be different"))
    else IO.unit

  def openFile(pathName: String): IO[File] =
    IO(new File(pathName)).handleErrorWith(error => IO.raiseError(error))

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- verifyInputs(args)
      orig <- openFile(args(0))
      dest <- openFile(args(1))
      count = copy[IO](orig, dest)
      _ <- IO(println(s"$count bytes copied from ${orig.getPath} to ${dest.getPath}"))
    } yield ExitCode.Success
}
