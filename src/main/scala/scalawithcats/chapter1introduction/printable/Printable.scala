package scalawithcats.chapter1introduction.printable

trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val stringPrintable: Printable[String] = new Printable[String] {
    def format(input: String): String = input
  }

  implicit val intPrintable: Printable[Int] = new Printable[Int] {
    def format(input: Int): String = input.toString
  }
}

object Printable {
  def format[A](input: A)(implicit p: Printable[A]): String =
    p.format(input)

  def print[A](input: A)(implicit p: Printable[A]): Unit =
    println(format(input))
}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String =
      Printable.format(value)

    def print(implicit p: Printable[A]): Unit =
      Printable.print(value)
  }
}
