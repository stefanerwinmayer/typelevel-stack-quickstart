val movies =
  <movies>
    <movie genre="action">Pirates of the Caribbean</movie>
    <movie genre="fairytale">Edward Scissorhands</movie>
  </movies>

val movieNodes = movies \ "movie"
movieNodes(0) \ "@genre"

def doChore(chore: String): String = chore match {
  case "clean dishes" => "scrub, dry"
  case "cook dinner" => "chop, sizzle"
  case _ => "whine, complain"
}
doChore("clean dishes")
doChore("mow lawn")

def factorial(n: Int): Int = n match {
  case 0 => 1
  case x if x > 0 => factorial(n - 1) * n
}
factorial(3)
factorial(0)
