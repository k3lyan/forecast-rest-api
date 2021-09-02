package model

import play.api.libs.json.Json

import scala.collection.mutable.ListBuffer

final case class Suppliers(suppliersInfo: ListBuffer[SupplierInfo])

object Suppliers {
  implicit val suppliersFormat = Json.format[Suppliers]
}