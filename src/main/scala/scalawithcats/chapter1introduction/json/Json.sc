import sandbox.chapter1introduction.json.JsonSyntax._
import sandbox.chapter1introduction.json.JsonWriterInstances._
import sandbox.chapter1introduction.json.{Json, JsonWriter, Person}

Json.toJson(Person("Dave", "dave@example.com"))
Person("Dave", "dave@example.com").toJson
implicitly[JsonWriter[String]]
Json.toJson(Option("A string"))