package model

import play.api.libs.json.Json

final case class SupplierInfo(name: String, age: Int)

object SupplierInfo {
  implicit val supplierFormat = Json.format[SupplierInfo]
}