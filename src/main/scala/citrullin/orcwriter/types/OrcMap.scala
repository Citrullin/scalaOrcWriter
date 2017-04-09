package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcMap(map: Map[OrcType, OrcType]) extends OrcType{
  def isValid: Boolean = true
  def getValue: Map[OrcType, OrcType] = map
}
