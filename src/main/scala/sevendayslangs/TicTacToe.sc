trait Field
case object X extends Field
case object O extends Field
case object Empty extends Field


trait Outcome
object XWins extends Outcome
object OWins extends Outcome
object Draw extends Outcome

class Board(val fields: List[Field]) {
  val row1 = fields.slice(0, 3)
  val row2 = fields.slice(3, 6)
  val row3 = fields.slice(6, 9)
  val col1 = List(row1(0), row2(0), row3(0))
  val col2 = List(row1(1), row2(1), row3(1))
  val col3 = List(row1(2), row2(2), row3(2))
  val diagonal1 = List(row1(0), row2(1), row3(2))
  val diagonal2 = List(row1(2), row2(1), row3(0))

  def eval(): Outcome = ???
}

val board = new Board(
  List(Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty, Empty)
)
//board.eval()

val x = for {
  field <- board.fields
  x <- 1 to 3
  y <- 1 to 3
} yield (field, x, y)

val filtered = x.filter { case (field, _, _) => field == X }
val cutdown = filtered.map { case (_, x, y) => (x, y)}



