package controllers

import model.{Production, Stock}
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.Play.materializer
import play.api.libs.json.{JsNumber, JsString, JsValue, Json}
import play.api.test._
import play.api.test.Helpers._


class ForecastControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "ForecastController load suppliers" should {

    "return 400 Bad Request if the field in the body has wrong type" in {
      val suppliersJson = Json.obj(
        "suppliersInfo" -> Json.arr(
          Json.obj(
            "name" -> JsString("Redstick"),
            "age" -> JsString("10"),
          ),
          Json.obj(
            "name" -> JsString("GlasRus"),
            "age" -> JsNumber(4)
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


    "return 400 Bad Request if one of the supplier age in the body is negative" in {
      val suppliersJson = Json.obj(
        "suppliersInfo" -> Json.arr(
          Json.obj(
            "name" -> JsString("Redstick"),
            "age" -> JsNumber(10),
          ),
          Json.obj(
            "name" -> JsString("GlasRus"),
            "age" -> JsNumber(-4)
          )
        )
      )

      val controller = new ForecastController(stubControllerComponents())
      val loadSuppliers = controller.loadSuppliers.apply(
        FakeRequest(POST, "/suppliers/load")
          .withHeaders(("Content-Type", "application/json"))
          .withBody[JsValue](suppliersJson)

      )

      status(loadSuppliers) mustBe BAD_REQUEST
    }

    "return Reset Content if the request is correct" in {
            val suppliersJson = Json.obj(
              "suppliersInfo" -> Json.arr(
                Json.obj(
                  "name" -> JsString("Redstick"),
                  "age" -> JsNumber(10),
                ),
                Json.obj(
                  "name" -> JsString("GlasRus"),
                  "age" -> JsNumber(4)
                )
              )
            )

      val controller = new ForecastController(stubControllerComponents())
      val loadSuppliers = controller.loadSuppliers.apply(
        FakeRequest(POST, "/suppliers/load")
          .withHeaders(("Content-Type", "application/json"))
          .withBody[JsValue](suppliersJson)
      )

      status(loadSuppliers) mustBe RESET_CONTENT
    }

    "ForecastController forecast suppliers" should {

      "still return OK if the list of suppliers is empty" in {

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

      "still return OK if the list of suppliers is empty" in {

        val controller = new ForecastController(stubControllerComponents())
        val forecastStock = controller.getStock(10).apply(
          FakeRequest(GET, "/forecast/stock/")
        )
        val response = Json.toJson(Stock(0, 0))
        status(forecastStock) mustBe OK
        contentAsJson(forecastStock) must be(response)
      }
    }
  }
}
