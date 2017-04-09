package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcBigInt(value: AnyVal) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _: Long | _: Int => true
      case _ => false
    }
  }

  def getValue: Long = value match{
    case value: Long  => value
    case value: Int => value.toLong
  }
}
