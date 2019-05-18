import java.net.URL

import scala.io.Source

def getContent(url: URL): Either[String, Source] =
  if (url.getHost.contains("google"))
    Left("Requested URL is blocked for the good of the people!")
  else
    Right(Source.fromURL(url))
getContent(new URL("http://danielwestheide.com"))
getContent(new URL("https://plus.google.com"))
getContent(new URL("http://google.com")) match {
  case Left(msg) => println(msg)
  case Right(source) => source.getLines().foreach(println)
}
val content: Either[String, Iterator[String]] =
  getContent(new URL("http://danielwestheide.com")).right.map(_.getLines())
val moreContent: Either[String, Iterator[String]] =
  getContent(new URL("http://google.com")).right.map(_.getLines())
val content2: Either[Iterator[String], Source] =
  getContent(new URL("http://danielwestheide.com")).left.map(Iterator(_))
val moreContent2: Either[Iterator[String], Source] =
  getContent(new URL("http://google.com")).left.map(Iterator(_))
val contentSame: Either[String, Iterator[String]] =
  getContent(new URL("http://danielwestheide.com")).map(_.getLines())
val moreContentSame: Either[String, Iterator[String]] =
  getContent(new URL("http://google.com")).map(_.getLines())

val part5 = new URL("http://t.co/UR1aalX4")
val part6 = new URL("http://t.co/6wlKwTmu")
val content3 = getContent(part5).map(a =>
  getContent(part6).map(b =>
    (a.getLines().size + b.getLines().size) / 2))
val content4 = getContent(part5).flatMap(a =>
  getContent(part6).map(b =>
    (a.getLines().size + b.getLines().size) / 2))

def averageLineCount(url1: URL, url2: URL): Either[String, Int] =
  for {
    source1 <- getContent(url1)
    source2 <- getContent(url2)
    lines1 <- Right(source1.getLines().size)
    lines2 <- Right(source2.getLines().size)
  } yield (lines1 + lines2) / 2

getContent(new URL("http://danielwestheide.com")).toOption
getContent(new URL("http://google.com")).toOption

val content5: Iterator[String] =
  getContent(new URL("http://danielwestheide.com")).fold(Iterator(_), _.getLines())
val moreContent5: Iterator[String] =
  getContent(new URL("http://google.com")).fold(Iterator(_), _.getLines())

case class Customer(age: Int)

class Cigarettes

case class UnderAgeFailure(age: Int, required: Int)

def buyCigarettes(customer: Customer): Either[UnderAgeFailure, Cigarettes] =
  if (customer.age < 16) Left(UnderAgeFailure(customer.age, 16))
  else Right(new Cigarettes)

type Citizen = String

case class BlackListedResource(url: URL, visitors: Set[Citizen])

val blacklist = List(
  BlackListedResource(new URL("https://google.com"), Set("John Doe", "Johanna Doe")),
  BlackListedResource(new URL("http://yahoo.com"), Set.empty),
  BlackListedResource(new URL("https://maps.google.com"), Set("John Doe")),
  BlackListedResource(new URL("http://plus.google.com"), Set.empty)
)
val checkedBlacklist: List[Either[URL, Set[Citizen]]] =
  blacklist.map(resource =>
    if (resource.visitors.isEmpty) Left(resource.url)
    else Right(resource.visitors))
val suspiciousResources = checkedBlacklist.flatMap(_.left.toOption)
val problemCitizens = checkedBlacklist.flatMap(_.right.toOption).flatten.toSet