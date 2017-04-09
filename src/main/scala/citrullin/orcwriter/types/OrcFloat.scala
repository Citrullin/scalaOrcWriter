package citrullin.orcwriter.types

/**
  * Created by citrullin on 26.03.17.
  */
class OrcFloat(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _: Double | _: Float => true
      case _ => false
    }
  }

  def getValue: Float = value match{
    case value: Double  => value.toFloat
    case value: Float => value
  }
}
