def whileLoop {
  var i = 1
  while (i <= 3) {
    println(i)
    i += 1
  }
}
whileLoop

val args = List("its", "all", "in", "the", "grind")
def forLoop {
  println( "for loop using Java-style iteration" )
  for(i <- 0 until args.length) {
    println(args(i))
  }
}
forLoop

def rubyStyleForLoop {
  println( "for loop using Ruby-style iteration" )
  args.foreach { arg =>
    println(arg)
  }
}
rubyStyleForLoop