/**
  * Created by citrullin on 4/7/17.
  */

import citrullin.orcserilizer.types.{OrcMap, _}
import citrullin.orcserilizer.OrcSchemaCreator
import org.apache.orc.TypeDescription
import org.specs2.matcher._
import scala.collection.JavaConverters._


import scala.util.Random

class OrcSchemaCreatorSpec extends org.specs2.mutable.Specification{
  val simpleStringStruct: OrcStruct = new OrcStruct(
    List(
      new OrcField("test", new OrcString(""))
    )
  )

  "simpleStringStruct used as argument in " >> {
    "createTypeDescription must return a orc struct typeDescription with a property test of type string" >> {
      val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleStringStruct)

      typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "string"
    }
  }
}
