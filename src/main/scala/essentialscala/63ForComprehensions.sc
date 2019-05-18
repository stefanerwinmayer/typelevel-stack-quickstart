Seq(1, 2, 3).map(_ * 2)
for {
  x <- Seq(1, 2, 3)
} yield x * 2

val data = Seq(Seq(1), Seq(2, 3), Seq(4, 5, 6))
data.flatMap(_.map(_ * 2))
for {
  subseq <- data
  element <- subseq
} yield element * 2

for {
  seq <- Seq(Seq(1), Seq(2, 3))
  elt <- seq
} println(elt * 2)

for (x <- Seq(1, 2, 3)) yield {
  x * 2
}

for (x <- Seq(-2, -1, 0, 1, 2) if x > 0) yield x

Seq(1, 2, 3).zip(Seq(4, 5, 6))
for (x <- Seq(1, 2, 3).zip(Seq(4, 5, 6))) yield {
  val (a, b) = x; a + b
}
for ((a, b) <- Seq(1, 2, 3) zip Seq(4, 5, 6)) yield a + b

for (x <- Seq(1, 2, 3).zipWithIndex) yield x

for {
  x <- Seq(1, 2, 3)
  square = x * x
  y <- Seq(4, 5, 6)
} yield square * y