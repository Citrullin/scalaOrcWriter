package citrullin.orcserilizer.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcDate(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _: java.util.Date | _: Long => true
      case _ => false
    }
  }

  def getValue: Long = value match{
    case value: java.util.Date => value.getTime
    case value: Long => value
  }
}
