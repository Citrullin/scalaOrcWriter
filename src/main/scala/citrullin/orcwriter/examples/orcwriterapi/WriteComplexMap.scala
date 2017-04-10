package citrullin.orcwriter.examples.orcwriterapi

import citrullin.orcwriter.OrcWriterAPI
import citrullin.orcwriter.types._

/**
  * Created by citrullin on 09.04.17.
  */
object WriteComplexMap extends App{
  //Create Schema.
  // It is needed to add, at minimum, one element (key, value) to the map, to get the type of the key and value
  val schema: OrcStruct = new OrcStruct(List(
    new OrcField("map", new OrcMap(
      Map(
        new OrcString("key1") -> new OrcString("value1")
      )
    ))
  ))

  val data: List[OrcStruct] = List.tabulate(100)(index => {
    new OrcStruct(List(
      new OrcField("map", new OrcMap(
        Map(
          new OrcString("key1") -> new OrcString("value1"),
          new OrcString("key2") -> new OrcString("value2"),
          new OrcString("key3") -> new OrcString("value3")
        )
      ))
    ))
  })

  OrcWriterAPI.createWriter("path/to/a/file.orc" , schema).write(data)
}
