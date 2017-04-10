/**
  * Created by citrullin on 09.04.17.
  */

import java.io.File

import citrullin.orcwriter.types.{OrcMap, _}
import citrullin.orcwriter.{OrcSchemaCreator, OrcWriter, OrcWriterCreator}
import org.apache.orc.TypeDescription
import org.specs2.matcher._

import scala.collection.JavaConverters._

class OrcWriterCreatorSpec extends org.specs2.mutable.Specification{
  def getCurrentDirectory = new java.io.File(".").getCanonicalPath

  "SimpleString test" >> {
    val simpleStringStruct: OrcStruct = new OrcStruct(
      List(
        new OrcField("test", new OrcString(""))
      )
    )

    val data: List[OrcStruct] = List.tabulate(100)(index => {
      new OrcStruct(
        List(
          new OrcField("test", new OrcString(index.toString))
        )
      )
    })
    val path = getCurrentDirectory + "/src/test/tmp/test.orc"
    val orcWriter: OrcWriter = OrcWriterCreator.createWriter(path, simpleStringStruct)
    orcWriter.writeMode = false

    orcWriter.write(data)
    new File(path).delete()

    "batch must have" >> {
      "a length of 100" >> {
        orcWriter.getBatch.count() mustEqual 100
      }
    }
  }
}
