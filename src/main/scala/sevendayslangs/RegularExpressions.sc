val reg = """^(F|f)\w*""".r
reg.findFirstIn("Fantastic")
reg.findFirstIn("not Fantastic")

val reg2 = "the".r
reg2.findAllIn("the way the scissors trim the hair and the shrubs")

val movies = <movies>
  <movie>The Incredibles</movie>
  <movie>WALL E</movie>
  <short>Jack Jack Attack</short>
  <short>Geri's Game</short>
</movies>

(movies \ "_").foreach { movie =>
  movie match {
    case <movie>{moviename}</movie> => println(moviename)
    case <short>{shortname}</short> => println(shortname + " (short)")
  }
}