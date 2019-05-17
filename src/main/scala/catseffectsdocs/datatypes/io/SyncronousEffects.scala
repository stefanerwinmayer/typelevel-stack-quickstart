package catseffectsdocs.datatypes.io

import cats.effect.IO

import scala.io.StdIn

object SyncronousEffects extends App {

  // Syncronous effects

  // An example would be reading / writing from / to the console,
  // which on top of the JVM uses blocking I/O, so their execution is immediate:
  def putStrLn(value: String) = IO(println(value))
  val readLn = IO(StdIn.readLine())
  val sameAsReadLn = IO.delay(StdIn.readLine())

  val commandLineProgram = for {
    _ <- putStrLn("What's your name?")
    n <- readLn
    _ <- putStrLn(s"Hello, $n!")
  } yield ()

  commandLineProgram.unsafeRunSync()
}
