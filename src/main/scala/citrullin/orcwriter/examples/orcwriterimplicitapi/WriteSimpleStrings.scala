package citrullin.orcwriter.examples.orcwriterimplicitapi

import citrullin.orcwriter.OrcWriterImplicitAPI._
import citrullin.orcwriter.types._

/**
  * Created by citrullin on 09.04.17.
  */
object WriteSimpleStrings extends App{
  //Create 100 orcStructs with one field, named property1, with empty string
  val data: List[OrcStruct] = List.tabulate(100)(index => {
    new OrcStruct(
      List(
        new OrcField("property1", new OrcString(""))
      )
    )
  })

  data.write("/home/citrullin/orcFiles/test.orc")
}
