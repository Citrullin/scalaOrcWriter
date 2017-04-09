package citrullin.orcwriter.types

/**
  * Created by citrullin on 25.03.17.
  */

case class OrcListContainer(value: List[OrcType])
case class OrcSeqContainer(value: Seq[OrcType])
case class OrcArrayContainer(value: Array[OrcType])
case class OrcIterableContainer(value: Iterable[OrcType])

class OrcArray(val list: AnyRef) extends OrcType{
  def isValid: Boolean = list match{
    case OrcListContainer(_)| OrcArrayContainer(_) |
         OrcSeqContainer(_) | OrcIterableContainer(_) =>
      true
    case _ => false
  }

  def getValue: List[OrcType] = list match{
    case OrcListContainer(value) => value
    case OrcSeqContainer(value) => value.toList
    case OrcArrayContainer(value) => value.toList
    case OrcIterableContainer(value) => value.toList
  }
}
