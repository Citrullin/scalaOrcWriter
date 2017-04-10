/**
  * Created by citrullin on 09.04.17.
  */

import java.io.File

import citrullin.orcwriter.types.{OrcMap, _}
import citrullin.orcwriter.{OrcSchemaCreator, OrcWriter, OrcWriterCreator}
import org.apache.hadoop.hive.ql.exec.vector.{BytesColumnVector, ColumnVector, LongColumnVector, MapColumnVector}
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
      "100 rows" >> {
        orcWriter.getBatch.count() mustEqual 100
      }

      "100 string elements" >> {
        var countStrings = 0
        orcWriter.getBatch.cols{0}.asInstanceOf[BytesColumnVector].vector.foreach(byteArray => {
          val optionValue = Option(byteArray)
          optionValue match{
            case Some(value) => countStrings += 1
            case None => false
          }
        })
        countStrings mustEqual 100
      }
    }
  }

  "ComplexMap test" >> {
    val simpleStringStruct: OrcStruct = new OrcStruct(
      List(
        new OrcField("test", new OrcMap(
          Map(
            new OrcString("mapproperty1") -> new OrcBigInt(123)
          )
        ))
      )
    )

    val data: List[OrcStruct] = List.tabulate(100)(index => {
      new OrcStruct(
        List(
          new OrcField("test", new OrcMap(
            Map(
              new OrcString("mapproperty1") -> new OrcBigInt(123),
              new OrcString("mapproperty1") -> new OrcBigInt(12345)
            )
          ))
        )
      )
    })
    val path = getCurrentDirectory + "/src/test/tmp/test.orc"
    val orcWriter: OrcWriter = OrcWriterCreator.createWriter(path, simpleStringStruct)
    orcWriter.writeMode = false

    orcWriter.write(data)
    new File(path).delete()

    "batch must have" >> {
      "100 rows" >> {
        orcWriter.getBatch.count() mustEqual 100
      }

      "200 map elements of type orcString -> orcBigInt" >> {
        val mapVector: MapColumnVector = orcWriter.getBatch.cols{0}.asInstanceOf[MapColumnVector]
        val keyVector: ColumnVector = orcWriter.getBatch.cols{0}.asInstanceOf[MapColumnVector].keys
        val valueVector: ColumnVector = orcWriter.getBatch.cols{0}.asInstanceOf[MapColumnVector].values

        var countStrings = 0

        keyVector.asInstanceOf[BytesColumnVector].vector.foreach(byteArray => {
          val optionValue = Option(byteArray)
          optionValue match{
            case Some(value) => countStrings += 1
            case None => false
          }
        })

        valueVector.isInstanceOf[LongColumnVector] mustEqual true and
          (keyVector.isInstanceOf[BytesColumnVector] mustEqual true) and
          (countStrings mustEqual 200)
      }
    }
  }
}
