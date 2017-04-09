package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcStruct(val list: List[OrcField]) extends OrcType{
  def isValid: Boolean = true
  def getValue: List[OrcField] = list
}
