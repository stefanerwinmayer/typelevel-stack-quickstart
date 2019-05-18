val songTitles = List("The White Hare", "Childe the Hunter", "Take no Rogues")
songTitles.map(t => t.toLowerCase())
songTitles.map(_.toLowerCase())

val wordFrequencies = ("habitual", 6) :: ("and", 56) :: ("consuetudinary", 2) ::
  ("additionally", 27) :: ("homely", 5) :: ("society", 13) :: Nil
def wordsWithoutOutliers(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.filter { case (_, f) => f > 3 && f < 25 } map { case (w, _) => w }
wordsWithoutOutliers(wordFrequencies)

val predicate: (String, Int) => Boolean = {
  case (_, f) => f > 3 && f < 25
}
val transformFn: (String, Int) => String = {
  case (w, _) => w
}

def wordWithoutOutliersPartialFunction(wordFrequencies: Seq[(String, Int)]): Seq[String] =
  wordFrequencies.collect { case (word, freq) if freq > 3 && freq < 25 => word }
wordWithoutOutliersPartialFunction(wordFrequencies)