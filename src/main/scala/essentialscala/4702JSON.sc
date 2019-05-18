
case class JSON(values: List[String, JSONElement])

sealed trait JSONElement

case class JSONString(value: String) extends JSONElement

case class JSONNumber(value: Double) extends JSONElement

case class JSONBool(value: Boolean) extends JSONElement

case object JSONNull extends JSONElement

case class JSONArray(values: List[JSONElement]) extends JSONElement

case class JSONObject(key: String, value: JSONElement) extends JSONElement