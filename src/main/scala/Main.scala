import citrullin.orcserilizer.{OrcReader, OrcSerializer}
import citrullin.orcserilizer.types._
import org.apache.orc.TypeDescription

import scala.util.Random

/**
  * Created by citrullin on 25.03.17.
  */
object Main extends App{
  val fields: List[OrcField] = List(
    new OrcField("key1", new OrcString("sdfsdf")),
    new OrcField("key2", new OrcBigInt(1)),
    new OrcField("key3", new OrcArray(
      List(
        new OrcBigInt(123)
      )
    )),
    new OrcField("key4", new OrcMap(
      Map(new OrcString("Bla") -> new OrcBigInt(3455))
    ))
  )

  val structSchema: OrcStruct = new OrcStruct(fields)
  val schema: String =
    OrcSerializer.createStringSchema(structSchema)


  val structList: List[OrcStruct] = List.tabulate(1000000)(index => {
    new OrcStruct(
      List(
        new OrcField("key1", new OrcString("sdfssdfaasdfasdfdsafasdfdf")),
        new OrcField("key2", new OrcBigInt(Random.nextLong())),
        new OrcField("key3", new OrcArray(
          List(
            new OrcBigInt(23432443),
            new OrcBigInt(23432443),
            new OrcBigInt(23432443),
            new OrcBigInt(23432443),
            new OrcBigInt(23432443)
          )
        )),
        new OrcField("key4", new OrcMap(
          Map(
            new OrcString("Bla") -> new OrcBigInt(Random.nextLong()),
            new OrcString("dddd") -> new OrcBigInt(Random.nextLong()),
            new OrcString("dfsfd") -> new OrcBigInt(Random.nextLong()),
            new OrcString("asdfasdf") -> new OrcBigInt(Random.nextLong())
          )
        ))
      )
    )
  })


  val filePath = "/home/citrullin/orcFiles/largeTest.orc"
  println(filePath)
  OrcSerializer.createWriter(filePath , structSchema).write(structList)
}
