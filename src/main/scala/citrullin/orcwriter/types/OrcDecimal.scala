package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcDecimal(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _: Double | _: Float => true
      case _ => false
    }
  }

  def getValue: Float = value match{
    case value: Double  => BigDecimal(value).setScale(2).toFloat
    case value: Float => BigDecimal(value).setScale(2).toFloat
  }

  def getUncleanValue: Any = value
}
