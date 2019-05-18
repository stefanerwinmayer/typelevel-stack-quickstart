package essentialscala

sealed trait JsValue {
  def stringify: String
}

trait JsWriter[A] {
  def write(value: A): JsValue
}

object JsUtil {
  def toJson[A](value: A)(implicit writer: JsWriter[A]) = writer.write(value)
}
