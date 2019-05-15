import cats._
import cats.data._
import cats.implicits._

Functor[List].map(List(1, 2, 3, 4)) ({(_: Int) * (_:Int)}.curried)

(3.some, 5.some) mapN { _ - _ }

1.some *> 2.some <* 3.some

1.some product 2.some

