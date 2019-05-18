package scalawithcats.chapter3Functors

import cats.Functor

object BinaryTree extends App{
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
}
