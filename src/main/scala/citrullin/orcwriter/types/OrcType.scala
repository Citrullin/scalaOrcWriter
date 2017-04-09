package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
trait OrcType {
  def isValid: Boolean
  def getValue: Any
}
