package model

import play.api.libs.json.Json

final case class Supplier(name: String, age: Int)

object Supplier {
  implicit val supplierFormat = Json.format[Supplier]
}