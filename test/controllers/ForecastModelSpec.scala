package controllers

import model.Forecast
import org.scalatestplus.play.PlaySpec

class ForecastModelSpec extends PlaySpec {
  "Produced Frames" must {
    "be 1000 if the elapsed time is under 30 days" in {
      val forecast = Forecast(3, 29)
      forecast.producedFrames mustBe 1000
    }

    "be equal to 1000 plus the number of production days multipiled by 1000 if the elapsed time is greater than 30" in {
      val age = 3
      val elapsedTime = 50
      val totalDays = elapsedTime + age
      val forecast = Forecast(age, elapsedTime)

      val productionOfFramesInterval = 8 + totalDays * 0.01
      val daysOfProduction = ((elapsedTime - 30) / productionOfFramesInterval).toInt
      forecast.producedFrames mustBe (1000 + daysOfProduction * 1000).toInt
    }
  }

  "Produced glasses" must {
    "be 0 if the elapsed time is under 30 days" in {
      val forecast = Forecast(3, 29)
      forecast.producedGlasses mustBe 0
    }

    "be equal to the formula if the elapsed time is greater than 30" in {
      val age = 3
      val elapsedTime = 50
      val totalDays = elapsedTime + age
      val forecast = Forecast(age, elapsedTime)
      forecast.producedGlasses mustBe ((elapsedTime - 30) * (50 + totalDays * 0.003)).toInt
    }
  }

  "Last day of production" must {
    "be 0 if the elapsed time is under 30 days" in {
      val forecast = Forecast(3, 29)
      forecast.lastDayofFramesProduction mustBe 0
    }

    "be equal to 30 plus the number of production days if the elapsed time is greater than 30" in {
      val age = 3
      val elapsedTime = 50
      val totalDays = elapsedTime + age
      val forecast = Forecast(age, elapsedTime)

      val productionOfFramesInterval = 8 + totalDays * 0.01
      val daysOfProduction = ((elapsedTime - 30) / productionOfFramesInterval).toInt
      forecast.lastDayofFramesProduction mustBe (30 + daysOfProduction * productionOfFramesInterval).toInt
    }
  }
}
