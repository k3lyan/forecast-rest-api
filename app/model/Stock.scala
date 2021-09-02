package model

import play.api.libs.json.Json

final case class Stock(glasses: Double, frames: Double)

object Stock {
  implicit val stockFormat = Json.format[Stock]
}
