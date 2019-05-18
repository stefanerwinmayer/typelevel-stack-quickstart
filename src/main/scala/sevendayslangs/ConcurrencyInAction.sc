import scala.io.Source

object PageLoader {
  def getPageSize(url: String) = Source.fromURL(url).mkString.length
}

val urls = List("http://www.amazon.com",
  "https://www.twitter.com",
  "https://www.cnn.com")

def timeMethod(method: () => Unit) = {
  val start = System.nanoTime
  method()
  val end = System.nanoTime
  println("Method took " + (end - start) / 1000000000.0 + " seconds.")
}

def getPageSizeSequentially(): Unit = {
  for (url <- urls) {
    println("Size for " + url + ": " + PageLoader.getPageSize(url))
  }
}

class WebPageSize extends Actor {
  override def receive = {
    case (url, size) => println("Size for " + url + ": " + size)
  }
}

def getPageSizeConcurrently(): Unit = {
  val system = ActorSystem("WebpageCounter")
  for (url <- urls) {
    val actor = system.actorOf(Props[WebPageSize])
    actor ! (url, PageLoader.getPageSize(url))
  }
}

println("Sequential run:")
timeMethod {
  getPageSizeSequentially
}
println("Concurrent run")
timeMethod {
  getPageSizeConcurrently
}