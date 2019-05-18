import scala.collection.mutable

List(1, 2, 3)
List("one", "two", "three")
List("one", "two", 3)(2)
//List("one", "two", 4)(4)
//List("one", "two", 3)(-1)
Nil
val animals = Set("lions", "tigers", "bears")
animals + "armadillos"
animals - "tigers"
//animals + Set("armadillos", "raccoons")
animals ++ Set("armadillos", "raccoons")
animals -- Set("lions", "bears")
animals & Set("armadillos", "raccoons", "lions", "tigers")

Set(1, 2, 3) == Set(3, 2, 1)
List(1, 2, 3) == List(3, 2, 1)

val ordinals = Map(0 -> "zero", 1 -> "one", 2 -> "two")
ordinals(2)
val map = new mutable.HashMap[Int, String]
map += 4 -> "four"
map += 8 -> "eight"
map
//map += "zero" -> 0


