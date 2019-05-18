import fs2.Stream

Stream.range(0, 100).takeWhile(_ < 7).toList

Stream("Alice", "Bob", "Carol").intersperse("|").toList

Stream.range(1, 10).scan(0)(_ + _).toList
