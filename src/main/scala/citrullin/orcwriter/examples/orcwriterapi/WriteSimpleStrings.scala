package citrullin.orcwriter.examples.orcwriterapi

import citrullin.orcwriter.OrcWriterAPI
import citrullin.orcwriter.types._

/**
  * Created by citrullin on 09.04.17.
  */
object WriteSimpleStrings extends App{
  //Create a schema orcStruct object. Used the generate the orcSchema
  val schema: OrcStruct = new OrcStruct(
    List(
      new OrcField("property1", new OrcString(""))
    )
  )

  //Create 100 orcStructs with one field, named property1, with empty string
  val data: List[OrcStruct] = List.tabulate(100)(index => {
    new OrcStruct(
      List(
        new OrcField("property1", new OrcString(""))
      )
    )
  })

  OrcWriterAPI.createWriter("/home/citrullin/orcFiles/test2.orc" , schema).write(data)
}
