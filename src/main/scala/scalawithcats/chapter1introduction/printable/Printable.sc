import sandbox.chapter1introduction.Cat
import sandbox.chapter1introduction.printable.Printable
import sandbox.chapter1introduction.printable.PrintableSyntax._

val cat = Cat("Garfield", 38, "ginger and black")
Printable.print(cat)
cat.print