package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcChar(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _: String | _: Char => true
      case _ => false
    }
  }

  def getValue: Byte = value match{
    case value: Byte => value
    case value: String => value.toByte
  }

  def getUncleanValue: Any = value
}
