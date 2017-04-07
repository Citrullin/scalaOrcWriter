package citrullin.orcserilizer.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcTinyInt(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _:Int | _:Long | _:Byte => true
      case _ => false
    }
  }

  def getValue: Byte = value match{
    case value: Byte => value
    case value: Int => value.toByte
    case value: Short => value.toByte
  }
}
