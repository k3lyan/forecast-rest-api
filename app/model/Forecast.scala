package model

final case class Forecast(days: Int) {
  def getStock: Stock = Stock(50 + days * 0.003, 1000 + (8 + days * 0.01) * 1000)
  def getProduction(supplierName: String): SupplierProduction = SupplierProduction(supplierName, days, (days / 30) * 30)
}

