package controllers

import model.{Production, Stock}
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._


class ForecastControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "ForecastController load suppliers" should {

    "should return 400 Bad Request if the field in the body are incorrect" in {
      val suppliersJson = Json.obj(
        "suppliersInfo" -> Json.arr(
          Json.obj(
            "name" -> "Redstick",
            "age" -> "ten"
          )
        )
      )

      val controller = new ForecastController(stubControllerComponents())
      val loadSuppliers = controller.loadSuppliers.apply(
        FakeRequest(POST, "/suppliers/load")
          .withHeaders(("Content-Type", "application/json"))
          .withJsonBody(suppliersJson)
      )

      status(loadSuppliers) mustBe BAD_REQUEST
    }


    "should return Reset Content if the request is correct" in {
      val suppliersJson = Json.obj(
        "suppliersInfo" -> Json.arr(
          Json.obj(
            "name" -> "Redstick",
            "age" -> 10,
          ),
          Json.obj(
            "name" -> "GlasRus",
            "age" -> 4
          )
        )
      )
      val controller = new ForecastController(stubControllerComponents())
      val loadSuppliers = controller.loadSuppliers.apply(
        FakeRequest(POST, "/suppliers/load")
          .withHeaders(("Content-Type", "application/json"))
          .withJsonBody(suppliersJson)
      )

      status(loadSuppliers) mustBe RESET_CONTENT
    }
  }

  "ForecastController forecast suppliers" should {

    "should still return OK if the list of suppliers is empty" in {

      val controller = new ForecastController(stubControllerComponents())
      val forecastSuppliers = controller.getProd(10).apply(
        FakeRequest(GET, "/forecast/suppliers/")
      )
      val response = Json.toJson(Production(List()))
      status(forecastSuppliers) mustBe OK
      contentAsJson(forecastSuppliers) must be(response)
    }
  }

  "ForecastController forecast stock" should {

    "should still return OK if the list of suppliers is empty" in {

      val controller = new ForecastController(stubControllerComponents())
      val forecastStock = controller.getStock(10).apply(
        FakeRequest(GET, "/forecast/stock/")
      )
      val response = Json.toJson(Stock(0,0))
      status(forecastStock) mustBe OK
      contentAsJson(forecastStock) must be(response)
    }
  }
}
