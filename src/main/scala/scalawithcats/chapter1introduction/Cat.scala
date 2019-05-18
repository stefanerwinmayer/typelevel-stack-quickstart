package scalawithcats.chapter1introduction

import cats.{Eq, Show}
import cats.instances.int._
import cats.instances.string._
import cats.syntax.eq._
import cats.syntax.show._
import scalawithcats.chapter1introduction.printable.Printable

final case class Cat(name: String, age: Int, color: String)

object Cat {

  import scalawithcats.chapter1introduction.printable.PrintableInstances._

  implicit val catPrintable = new Printable[Cat] {
    def format(cat: Cat): String = {
      val name = Printable.format(cat.name)
      val age = Printable.format(cat.age)
      val color = Printable.format(cat.color)
      s"$name is a $age year-old $color cat."
    }
  }

  implicit val catShow = Show.show[Cat] { cat =>
    val name = cat.name.show
    val age = cat.age.show
    val color = cat.color.show
    s"$name is a $age year-old $color cat."
  }

  implicit val catEqual: Eq[Cat] =
    Eq.instance[Cat] { (cat1, cat2) =>
      (cat1.name === cat2.name) &&
      (cat1.age === cat2.age) &&
      (cat1.color === cat2.color)
    }
}
