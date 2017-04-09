package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcInt(value: Int) extends OrcType{
  def isValid: Boolean = true
  def getValue: Int = value
}
