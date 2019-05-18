import scala.collection.immutable.Queue

val sequence = Seq(1, 2, 3)

sequence.apply(0)
sequence(0)
//sequence(3)

sequence.head
sequence.tail
sequence.tail.head
//Seq().head
//Seq().tail
sequence.headOption
Seq().headOption

sequence.length

sequence.contains(2)
sequence.find(_ == 3)
sequence.find(_ > 4)
sequence.filter(_ > 1)

sequence.sortWith(_ > _)

sequence :+ 4
0 +: sequence
sequence ++ Seq(4, 5, 6)

def someMethod = {
  import scala.collection.immutable.Vector.empty
  empty[Int]
}

Vector(1, 2, 3)
Queue(1, 2, 3)

Seq(1, 2, 3).mkString(",")
Seq(1, 2, 3).mkString("[ ", ", ", " ]")

"dog".permutations.toList
Seq("a", "wet", "dog").flatMap(_.permutations)
Seq(1, 2, 3).flatMap(num => Seq(num, num * 10))
Vector(1, 2, 3).flatMap(num => Seq(num, num * 10))

Seq(1, 2, 3).foldLeft(0)(_ + _)
Seq(1, 2, 3).foldRight(0)(_ + _)
Seq(1, 2, 3).foreach(num => println("And a " + num + "..."))
Seq(1, 2, 3).reduce(_ + _)
