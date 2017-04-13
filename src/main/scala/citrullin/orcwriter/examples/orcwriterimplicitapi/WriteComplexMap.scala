package citrullin.orcwriter.examples.orcwriterimplicitapi

import citrullin.orcwriter.OrcWriterImplicitAPI._
import citrullin.orcwriter.types.{OrcField, OrcMap, OrcString, OrcStruct}

/**
  * Created by citrullin on 4/10/17.
  */
object WriteComplexMap extends App{
  def getCurrentDirectory: String = new java.io.File(".").getCanonicalPath

  val data: List[OrcStruct] = List(
    new OrcStruct(List(
      new OrcField("map", new OrcMap(
        Map(
          new OrcString("key1") -> new OrcString("object1"),
          new OrcString("key2") -> new OrcString("object1"),
          new OrcString("key3") -> new OrcString("object1")
        )
      ))
    )),
    new OrcStruct(List(
      new OrcField("map", new OrcMap(
        Map(
          new OrcString("key1") -> new OrcString("object2"),
          new OrcString("key2") -> new OrcString("object2"),
          new OrcString("key3") -> new OrcString("object2")
        )
      ))
    )),
    new OrcStruct(List(
      new OrcField("map", new OrcMap(
        Map(
          new OrcString("key1") -> new OrcString("object3"),
          new OrcString("key2") -> new OrcString("object3"),
          new OrcString("key3") -> new OrcString("object3")
        )
      ))
    ))
  )

  data.write(getCurrentDirectory + "/tmp/orcFiles/implicit/WriteComplexMap.orc")
}
