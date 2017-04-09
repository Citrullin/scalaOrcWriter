/**
  * Created by citrullin on 09.04.17.
  */

import citrullin.orcwriter.types.{OrcMap, _}
import citrullin.orcwriter.OrcSchemaCreator
import org.apache.orc.TypeDescription
import org.specs2.matcher._
import scala.collection.JavaConverters._

class OrcWriterCreatorSpec extends org.specs2.mutable.Specification{
  val simpleStringStruct: OrcStruct = new OrcStruct(
    List(
      new OrcField("test", new OrcString(""))
    )
  )
  val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleStringStruct)


}
