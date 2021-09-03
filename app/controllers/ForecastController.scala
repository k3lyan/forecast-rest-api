package controllers

import model.{Forecast, Production, Stock, SupplierInfo, Suppliers}

import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._

import scala.collection.mutable

@Singleton
class ForecastController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  private val suppliersList = Suppliers(mutable.ListBuffer[SupplierInfo]())

  def getAll: Action[AnyContent] = Action {
    if (suppliersList.suppliersInfo.isEmpty) NoContent else Ok(Json.toJson(suppliersList))
  }

  def getStock(elapsedTime: Int): Action[AnyContent] = Action {
    val stocks = suppliersList
      .suppliersInfo
      .toList
      .map(supp => Forecast(supp.age + elapsedTime).getStock)
      .foldLeft(Stock(0, 0))((newStock, stock) => Stock(newStock.glasses + stock.glasses, newStock.frames + stock.frames))
    Ok(Json.toJson(stocks))
  }

  def getProd(elapsedTime: Int): Action[AnyContent] = Action {
    val prod = suppliersList
      .suppliersInfo
      .toList
      .map(supp => Forecast(supp.age + elapsedTime).getProduction(supp.name))

    Ok(Json.toJson(Production(prod)))
  }


  def loadSuppliers = Action { implicit request =>
    val suppliersOpt = request
      .body
      .asJson
      .flatMap(Json.fromJson[Suppliers](_).asOpt)

    suppliersOpt match {
      case Some(suppliers) => {
        suppliersList.suppliersInfo.clear()
        suppliers.suppliersInfo.foreach(suppliersList.suppliersInfo += _)
      }
        ResetContent
      case None => BadRequest
    }
  }
}