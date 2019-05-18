package catsEffectTutorial

import java.io._

import cats.effect.concurrent.Semaphore
import cats.effect.{ExitCode, IO, IOApp, Resource}
import cats.implicits._

object CopyFile extends IOApp {

  def inputStream(f: File, guard: Semaphore[IO]): Resource[IO, FileInputStream] =
    Resource.make {
      IO(new FileInputStream(f)) // create the resource
    } { inputStream =>
      // close the resource
      guard.withPermit {
        IO(inputStream.close()).handleErrorWith(_ => IO.unit) // what do do when there is an error
      }
    }

  def outputStream(f: File, guard: Semaphore[IO]): Resource[IO, FileOutputStream] =
    Resource.make {
      IO(new FileOutputStream(f))
    } { outStream =>
      guard.withPermit {
        IO(outStream.close()).handleErrorWith(_ => IO.unit)
      }
    }

  def inputOutputStreams(in: File, out: File, guard: Semaphore[IO]): Resource[IO, (FileInputStream, FileOutputStream)] =
    for {
      inStream <- inputStream(in, guard)
      outStream <- outputStream(out, guard)
    } yield (inStream, outStream)

  // alternative for resources that implement java.lang.AutoClosable but does not allow logging, add custom error handling, etc.
  def inputStreamAuto(f: File): Resource[IO, FileInputStream] =
    Resource.fromAutoCloseable(IO(new FileInputStream(f)))

  def transmit(origin: InputStream, destination: OutputStream, buffer: Array[Byte], acc: Long): IO[Long] =
    for {
      amount <- IO(origin.read(buffer, 0, buffer.size)) // read returns number of bytes read, or -1 if nothing left
      // >> is the same as first.flatMap(_ => second)
      count <- if (amount > -1)
        IO(destination.write(buffer, 0, amount)) >> transmit(origin, destination, buffer, acc + amount)
      else IO.pure(acc)
    } yield count

  def transfer(origin: InputStream, destination: OutputStream): IO[Long] =
    for {
      buffer <- IO(new Array[Byte](1024 * 10))
      total <- transmit(origin, destination, buffer, 0)
    } yield total

  def copy(origin: File, destination: File): IO[Long] =
    for {
      guard <- Semaphore[IO](1)
      count <- inputOutputStreams(origin, destination, guard).use {
        case (in, out) =>
          guard.withPermit(transfer(in, out))
      }
    } yield count

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- if (args.length < 2) IO.raiseError(new IllegalArgumentException("Need origin and destination first"))
      else IO.unit
      orig = new File(args(0))
      dest = new File(args(1))
      count = copy(orig, dest)
      _ <- IO(println(s"$count bytes copied from ${orig.getPath} to ${dest.getPath}"))
    } yield ExitCode.Success
}
