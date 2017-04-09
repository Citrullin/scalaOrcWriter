package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcDouble(value: AnyVal) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _: Double | _: Float => true
      case _ => false
    }
  }

  def getValue: Double = value match{
    case value: Double  => value
    case value: Float => value.toDouble
  }
}
