package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcVarChar(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _: String | _: Array[Byte] | _: Char => true
      case _ => false
    }
  }

  def getValue: Array[Byte] = value match{
    case value: Array[Byte] => value
    case value: String => value.toCharArray.map(_.toByte)
    case value: Char => Array(value.toByte)
  }
}
