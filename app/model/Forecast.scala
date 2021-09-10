package model

final case class Forecast(age: Int, elapsedTime: Int) {
  val totalDays = age + elapsedTime
  val productionOfFramesInterval = 8 + totalDays * 0.01

  val producedGlasses =
    if (elapsedTime < 30) 0
    else (50 + totalDays * 0.003).toInt

  val daysOfProduction: Int =
    if (elapsedTime < 30) 0
    else ((elapsedTime - 30) / productionOfFramesInterval).toInt

  val lastDayofFramesProduction: Int =
    if (elapsedTime < 30) 0
    else 30 + (daysOfProduction * productionOfFramesInterval).toInt

  val producedFrames = 1000 + 1000 * daysOfProduction

  val getStock: Stock = {
      Stock(producedGlasses, 1000 + 1000 * daysOfProduction)
  }

  def getProduction(supplierName: String): SupplierProduction = {
      SupplierProduction(supplierName, age + elapsedTime, lastDayofFramesProduction)
  }
}

