package controllers

import model.{Supplier, Suppliers}

import javax.inject._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

import scala.collection.mutable

@Singleton
class ForecastController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  //private val suppliersList: Suppliers = Suppliers(List())
  private val suppliersList = new Suppliers(mutable.ListBuffer[Supplier]())


  // curl localhost:9000/forecast/suppliers
  def getAll(): Action[AnyContent] = Action {
    if (suppliersList.suppliersInfo.isEmpty) NoContent else Ok(Json.toJson(suppliersList))
  }

  // curl localhost:9000/forecst/suppliers/Redstick
  def getByName(name: String) = Action {
    suppliersList.suppliersInfo.find(_.name == name) match {
      case Some(name) => Ok(Json.toJson(name))
      case None => NotFound
    }
  }

  // curl -v -d '{"suppliers": [{"name": "GlassesRUs", "age": "4"},{"name": "Redstick", "age": "10"}]}' -H 'Content-Type: application/json' -X POST localhost:9000/suppliers/load
  def loadSuppliers() = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson

        val suppliersOpt: Option[Suppliers] = jsonObject.flatMap(Json.fromJson[Suppliers](_).asOpt)

        suppliersOpt match {
          case Some(suppliers) => {
            suppliersList.suppliersInfo.clear()
            suppliers.suppliersInfo.foreach { supp =>
              suppliersList.suppliersInfo += supp
            }
          }
            ResetContent
          case None =>
            BadRequest
        }
//    val supplierOpt: Option[Supplier] = jsonObject.flatMap(Json.fromJson[Supplier](_).asOpt)
//
//    supplierOpt match {
//      case Some(supplier) => {
//        suppliersList.suppliersInfo.clear()
//        suppliersList.suppliersInfo += supplier
//      }
//        ResetContent
//      case None =>
//        BadRequest
//    }
  }
}
