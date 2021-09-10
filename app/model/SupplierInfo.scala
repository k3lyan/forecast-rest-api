package model

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{Format, JsPath, Json, JsonValidationError, Reads, Writes}

final case class SupplierInfo(name: String, age: Int)

object SupplierInfo {
  val supplierReads: Reads[SupplierInfo] = (
      (JsPath \ "name").read[String] and
      (JsPath \ "age").read[Int].filter(JsonValidationError("Error: positive age value expected."))(_ > 0)
    )(SupplierInfo.apply _)

  val supplierWrites: Writes[SupplierInfo] = (
      (JsPath \ "name").write[String] and
      (JsPath \ "age").write[Int]
    )(unlift(SupplierInfo.unapply))

  implicit val supplierFormat: Format[SupplierInfo] =
    Format(supplierReads, supplierWrites)
}