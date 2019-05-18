import java.io.{FileNotFoundException, InputStream}
import java.net.{MalformedURLException, URL}

import scala.io.Source
import scala.util.{Failure, Success, Try}

def parseURL(url: String): Try[URL] = Try(new URL(url))
parseURL("http://danielwestheide.com")
parseURL("garbage")

//val url = parseURL(Console.readLine("URL: ")) getOrElse new URL("http://duckduckgo.com")
parseURL("http://danielwestheide.com").map(_.getProtocol)
parseURL("garbage").map(_.getProtocol)

def inputStreamForURL(url: String): Try[InputStream] = parseURL(url).flatMap { u =>
  Try(u.openConnection()).flatMap(conn => Try(conn.getInputStream))
}

def parseHttpURL(url: String) = parseURL(url).filter(_.getProtocol == "http")
parseHttpURL("http://apache.openmirror.de")
parseHttpURL("ftp://mirror.netcologne.de/apache.org")
parseHttpURL("http://danielwestheide.com").foreach(println)

def getURLContent(url: String): Try[Iterator[String]] =
  for {
    url <- parseURL(url)
    connection <- Try(url.openConnection())
    is <- Try(connection.getInputStream)
    source = Source.fromInputStream(is)
  } yield source.getLines()

getURLContent("http://danielwestheide.com/foobar") match {
  case Success(lines) => lines.foreach(println)
  case Failure(ex) => println(s"Problem rendering URL content: ${ex.getMessage}")
}

val content = getURLContent("garbage") recover {
  case e: FileNotFoundException => Iterator("Requested page does not exist")
  case e: MalformedURLException => Iterator("Please make sure to enter a valid URL")
  case _ => Iterator("An unexpected error has occurred. We are so sorry!")
}
content.get.foreach(println)
