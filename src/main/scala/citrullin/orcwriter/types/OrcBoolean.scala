package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcBoolean(value: Any) extends OrcType{
  def isValid: Boolean = {
    value match {
      case _: Boolean | _: Long | _:Int | _:String => true
      case _ => false
    }
  }

  def getValue: Long = value match{
    case value: Boolean =>
      if(value) 1L else 0L
    case value: Long => value
    case value: Int => value.toLong
    case value: String =>
      if(value.isEmpty) 0L else 1L
  }
}
