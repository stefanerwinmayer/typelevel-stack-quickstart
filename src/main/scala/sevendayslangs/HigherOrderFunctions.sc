

val list = List("frodo", "samwise", "pippin")
list.foreach(hobbit => println(hobbit))

val hobbits = Set("frodo", "samwise", "pippin")
hobbits.foreach(hobbit => println(hobbit))

val hobbits2 = Map("frodo" -> "hobbit", "samwise" -> "hobbit", "pippin" -> "hobbit")
hobbits2.foreach(hobbit => println(hobbit))
hobbits2.foreach(hobbit => println(hobbit._1))
hobbits2.foreach(hobbit => println(hobbit._2))

list
list.isEmpty
Nil.isEmpty
list.length
list.size
list.head
list.tail
list.last
list.init
list.reverse
list.drop(1)
list
list.drop(2)

val words = List("peg", "al", "bud", "kelly")
words.count(word => word.size > 2)
words.filter(word => word.size > 2)
words.map(word => word.size)
words.forall(word => word.size > 1)
words.exists(word => word.size > 4)
words.exists(word => word.size > 5)
words.sortWith((s, t) => s.charAt(0).toLower < t.charAt(0).toLower)
words.sortWith((s, t) => s.size < t.size)

val list2 = List(1, 2, 3)
val sum = (0 /: list2) { (sum, i) => sum + i }
list2.foldLeft(0)((sum, value) => sum + value)

val strings = List("abc", "def", "ghi")
val stringsSize = (0 /: strings) { (size, _) => size + 1 }
val stringSize2 = strings.foldLeft(0)((size, _) => size + 1)

object Censor {

  def sanitise(word: String): String = wordMap.getOrElse(word, word)

  val wordMap = Map(
    "Shoot" -> "Pucky",
    "Darn" -> "Beans"
  )
}

val same = Censor.sanitise("Hello")
val shoot = Censor.sanitise("Shoot")
val darn = Censor.sanitise("Darn")