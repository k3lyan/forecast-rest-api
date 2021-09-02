package model

import play.api.libs.json.Json

final case class SupplierProduction(name: String, age_in_days: Int, last_day_of_frame_production: Int)

object SupplierProduction {
  implicit val suppliersInfoFormat = Json.format[SupplierProduction]
}