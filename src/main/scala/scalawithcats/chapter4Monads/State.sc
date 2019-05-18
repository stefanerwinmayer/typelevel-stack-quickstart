import cats.data.State

val a = State[Int, String] { state =>
  (state, s"The state is $state")
}
val (state, result) = a.run(10).value
val state2 = a.runS(10).value
val result2 = a.runA(10).value

val step1 = State[Int, String] { num =>
  val ans = num + 1
  (ans, s"Result of step1: $ans")
}
val step2 = State[Int, String] { num =>
  val ans = num * 2
  (ans, s"Result of step2: $ans")
}
val both = for {
  a <- step1
  b <- step2
} yield (a, b)
val (state3, result3) = both.run(20).value

val getDemo = State.get[Int]
getDemo.run(10).value
val setDemo = State.set[Int](30)
setDemo.run(10).value
val pureDemo = State.pure[Int, String]("Result")
pureDemo.run(10).value
val inspectDemo = State.inspect[Int, String](_ + "!")
inspectDemo.run(10).value
val modifyDemo = State.modify[Int](_ + 1)
modifyDemo.run(10).value

import State._

val program: State[Int, (Int, Int, Int)] = for {
  a <- get[Int]
  _ <- set[Int](a + 1)
  b <- get[Int]
  _ <- modify[Int](_ + 1)
  c <- inspect[Int, Int](_ * 1000)
} yield (a, b, c)
val (state4, result4) = program.run(1).value
