val example = Map("a" -> 1, "b" -> 2, "c" -> 3)
"a" -> 1
("a", 1)

example("a")
example.get("a")

//example("d")
example.get("d")

example.getOrElse("d", -1)

example.contains("a")
example.size

example + ("d" -> 4) - "c"

// ordered Map
scala.collection.immutable.ListMap("a" -> 1) + ("b" -> 2) + ("c" -> 3) + ("d" -> 4) + ("e" -> 5)

example.map(pair => pair._1 -> pair._2 * 2)
example.map(pair => pair._1 + " = " + pair._2)

Set(1, 2) ++ Set(1, 3)
Set(1, 2, 3) -- Set(1, 3)

(10 until 1 by -1).toList
(1 until 10 by 2).toList
