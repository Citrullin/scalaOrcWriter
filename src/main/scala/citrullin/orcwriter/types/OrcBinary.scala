package citrullin.orcwriter.types

/**
  * Created by citrullin on 4/10/17.
  */
class OrcBinary(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match{
      case String | Byte | Int | Long => true
      case _ => false
    }
  }

  def getValue: Byte = value.toString.toByte
  def getUncleanValue = value
}
