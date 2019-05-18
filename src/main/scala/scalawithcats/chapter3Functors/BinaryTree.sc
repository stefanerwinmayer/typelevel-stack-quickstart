import cats.Functor
import sandbox.chapter3Functors.{Branch, Leaf, Tree}


implicit val treeFunctor: Functor[Tree] =
  new Functor[Tree] {
    def map[A, B](tree: Tree[A])(func: A => B): Tree[B] =
      tree match {
        case Branch(left, right) =>
          Branch(map(left)(func), map(right)(func))
        case Leaf(value) =>
          Leaf(func(value))
      }
  }

//Tree.leaf(100).map(_ * 2)
//Tree.branch(Tree.leaf(10), Tree.leaf(20)).map(_ * 2)