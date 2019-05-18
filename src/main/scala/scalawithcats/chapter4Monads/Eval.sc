import cats.Eval

val x = Eval.now {
  println("Computing X")
  math.random
}
x.value
x.value

val y = Eval.always {
  println("Computing Y")
  math.random
}
y.value
y.value
val z = Eval.later {
  println("Computing Z")
  math.random
}
z.value
z.value

val greeting = Eval.
  always {
    println("Step 1");
    "Hello"
  }.
  map { str => println("Step 2"); s"$str world" }

greeting.value

val ans = for {
  a <- Eval.now {
    println("Calculating A");
    40
  }
  b <- Eval.always {
    println("Calculating B");
    2
  }
} yield {
  println("Addding A and B")
  a + b
}
ans.value
ans.value

val saying = Eval.
  always {
    println("Step 1");
    "The cat"
  }.
  map { str => println("Step 2"); s"$str sat on" }.
  memoize.
  map { str => println("Step 3"); s"$str the mat" }

saying.value
saying.value

def factorial(n: BigInt): Eval[BigInt] =
  if (n == 1) {
    Eval.now(n)
  } else {
    Eval.defer(factorial(n - 1).map(_ * n))
  }
factorial(50000).value


def foldRightEval[A, B](as: List[A], acc: Eval[B])
                       (fn: (A, Eval[B]) => Eval[B]): Eval[B] =
  as match {
    case head :: tail =>
      Eval.defer(fn(head, foldRightEval(tail, acc)(fn)))
    case Nil =>
      acc
  }
def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
  foldRightEval(as, Eval.now(acc)) { (a, b) =>
    b.map(fn(a, _))
  }.value

foldRight((1 to 100000).toList, 0L)(_ + _)
