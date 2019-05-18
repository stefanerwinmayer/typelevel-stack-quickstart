def foo(a: Int) = a
foo(_)

def foo(a: Int, b: Int) = a + b
foo(_, 2)

object Sum {
  def sum(x: Int, y: Int) = x + y
}
Sum.sum _

object MathStuff {
  def add1(num: Int) = num + 1
}