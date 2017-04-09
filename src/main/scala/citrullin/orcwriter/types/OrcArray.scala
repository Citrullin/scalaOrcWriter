package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */
class OrcArray(val list: AnyRef) extends OrcType{
  def isValid: Boolean = list match{
    case _:List[OrcType] | _:Array[OrcType] |
         _:Seq[OrcType] | _:Iterable[OrcType] |
         _:Stream[OrcType] =>
      true
    case _ => false
  }

  def getValue: List[OrcType] = list match{
    case value: List[OrcType] => value
    case value: Array[OrcType] => value.toList
    case value: Seq[OrcType] => value.toList
    case value: Iterable[OrcType] => value.toList
  }
}
