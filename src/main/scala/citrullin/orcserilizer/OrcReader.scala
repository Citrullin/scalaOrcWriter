package citrullin.orcserilizer

import citrullin.orcserilizer.types.OrcStruct
import org.apache.hadoop.conf.Configuration
import org.apache.orc.{OrcFile, Reader, RecordReader}
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hive.ql.exec.vector.{BytesColumnVector, LongColumnVector, VectorizedRowBatch}

/**
  * Created by citrullin on 26.03.17.
  */
object OrcReader {
  def readOrcFile(path: String, structSchema: OrcStruct): Unit = {
    val configuration: Configuration = new Configuration()
    val reader: Reader = OrcFile.createReader(
      new Path(path),
      OrcFile.readerOptions(configuration)
    )

    println("File Size: " + reader.getContentLength/ 1000) //Kbytes
    val row: RecordReader = reader.rows()
    reader.getStripes.forEach(stripe => println(stripe))

  }
}
