

case class UserClass(firstName: String, lastName: String, score: Int)

def advance(xs: List[UserClass]) = xs match {
  case UserClass(_, _, score1) :: UserClass(_, _, score2) :: _ => score1 - score2
  case _ => 0
}

trait User {
  def name: String

  def score: Int
}

class FreeUser(val name: String, val score: Int, val upgradeProbability: Double)
  extends User

class PremiumUser(val name: String, val score: Int) extends User

object FreeUser {
  def unapply(user: FreeUser): Option[(String, Int, Double)] =
    Some((user.name, user.score, user.upgradeProbability))
}

object PremiumUser {
  def unapply(user: PremiumUser): Option[(String, Int)] = Some((user.name, user.score))
}

val user: User = new FreeUser("Daniel", 3000, 0.7d)
user match {
  case FreeUser(name, _, p) =>
    if (p > 0.75) name + ", what can we do for you today?" else "Hello " + name
  case PremiumUser(name, _) => "Welcome back, dear " + name
}

object premiumCandidate {
  def unapply(user: FreeUser): Boolean = user.upgradeProbability > 0.75
}

def initiateSpamProgram(user: FreeUser) = println("Spamming...")
def sendRegularNewsletter(user: User) = println("Newslettering...")
user match {
  case freeUser@premiumCandidate() => initiateSpamProgram(freeUser)
  case _ => sendRegularNewsletter(user)
}

val xs = 58 #:: 43 #:: 93 #:: Stream.empty
xs match {
  case first #:: second #:: _ => first - second
  case _ => -1
}
xs match {
  case #::(first, #::(second, _)) => first - second
  case _ => -1
}

::(1, Nil)

