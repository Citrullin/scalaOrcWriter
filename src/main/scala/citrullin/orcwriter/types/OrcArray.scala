package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */

class OrcArray(val list: List[OrcType]) extends OrcType{
  def isValid: Boolean = true
  def getValue: List[OrcType] = list
  def getUncleanValue: Any = list
}
