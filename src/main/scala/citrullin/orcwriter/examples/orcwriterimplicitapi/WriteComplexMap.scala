package citrullin.orcwriter.examples.orcwriterimplicitapi

import citrullin.orcwriter.OrcWriterImplicitAPI._
import citrullin.orcwriter.types.{OrcField, OrcMap, OrcString, OrcStruct}

/**
  * Created by citrullin on 4/10/17.
  */
class WriteComplexMap {
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

  data.write("a/path/to/a/file.orc")
}
