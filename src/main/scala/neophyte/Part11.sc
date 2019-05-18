case class Email(
                  subject: String,
                  text: String,
                  sender: String,
                  recipient: String
                )

type EmailFilter = Email => Boolean
type IntPairPred = (Int, Int) => Boolean
def sizeConstraint(pred: IntPairPred, n: Int, email: Email) = pred(email.text.size, n)
val gt: IntPairPred = _ > _
val ge: IntPairPred = _ >= _
val lt: IntPairPred = _ < _
val le: IntPairPred = _ <= _
val eq: IntPairPred = _ == _

val minimumSize: (Int, Email) => Boolean = sizeConstraint(ge, _: Int, _: Email)
val maximumSize: (Int, Email) => Boolean = sizeConstraint(le, _: Int, _: Email)
val constr20: (IntPairPred, Email) => Boolean = sizeConstraint(_: IntPairPred, 20, _: Email)
val constr30: (IntPairPred, Email) => Boolean = sizeConstraint(_: IntPairPred, 30, _: Email)
//val sizeConstraintFn: (IntPairPred, Int, Email) => Boolean = sizeConstraint _

def sizeConstraint(pred: IntPairPred)(n: Int)(email: Email): Boolean =
  pred(email.text.size, n)
val sizeConstraintFn: IntPairPred => Int => Email => Boolean = sizeConstraint _
val minSize: Int => Email => Boolean = sizeConstraint(ge)
val maxSize: Int => Email => Boolean = sizeConstraint(le)
val min20: Email => Boolean = minSize(20)
val max20: Email => Boolean = maxSize(20)
val min202: Email => Boolean = sizeConstraintFn(ge)(20)
val max202: Email => Boolean = sizeConstraintFn(le)(20)
