package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcTimestamp(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _:java.util.Date | _:Long => true
      case _ => false
    }
  }

  def getValue: Long = value match{
    case value: Long => value
    case value: java.util.Date => value.getTime
  }
}
