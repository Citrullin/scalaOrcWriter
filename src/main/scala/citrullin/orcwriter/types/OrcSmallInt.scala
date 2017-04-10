package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcSmallInt(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _:Int | _:Long | _:Byte => true
      case _ => false
    }
  }

  def getValue: Short = value match{
    case value: Byte => value.toShort
    case value: Int => value.toShort
    case value: Short => value
  }

  def getUncleanValue: Any = value
}
