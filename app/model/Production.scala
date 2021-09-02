package model

import play.api.libs.json.Json

final case class Production(suppliers: List[SupplierProduction])

object Production {
  implicit val productionFormat = Json.format[Production]
}