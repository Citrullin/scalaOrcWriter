package citrullin.orcserilizer

import citrullin.orcserilizer.types._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hive.ql.exec.vector._
import org.apache.orc.{OrcFile, TypeDescription, Writer}

/**
  * Created by citrullin on 29.03.17.
  */
object OrcWriterCreator {
  /** writes a list of OrcStructs into a Orc File
    *
    *  @param path A string of a local path or hdfs
    *  @param structSchema A OrcStruct which represent the schema. Will be used to generate the schema.
    *  @return a boolean. wasWriteSuccessful?. True -> yes, false -> no
    */
  def createWriter(path: String, structSchema: OrcStruct): OrcWriter = {
    val schema: TypeDescription = OrcSchemaCreator.createTypeDescription(structSchema)
    val configuration: Configuration = new Configuration()
    val batch: VectorizedRowBatch = schema.createRowBatch()

    val writer: Writer = OrcFile.createWriter(
      new Path(path),
      OrcFile.writerOptions(configuration).setSchema(schema)
    )
    val columnVectorList: List[ColumnVector] = createColumnVectorList(structSchema.getValue, batch)

    new OrcWriter(writer, batch, columnVectorList, configuration, path)
  }

  /** generate a List of ColumnVectors by a given List of OrcFields
    *
    *  @param fieldList a List of OrcField (extracted from a OrcStruct)
    *  @param batch the VectorizedRowBatch. Contains the whole batch data which will
    *               written with a Writer into a Orc File.
    *  @return a List of Children of ColumnVectors (e.g. LongColumnVector)
    */
  def createColumnVectorList
  (fieldList: List[OrcField], batch: VectorizedRowBatch): List[ColumnVector] = {
    fieldList.zipWithIndex.map{
      case (field, index) =>
        createBatchColumnVectorInstances(field.value, batch.cols{index})
    }
  }

  /** upcaste with the OrcType to the right child of ColumnVector
    *
    *  @param orcType A Instance of a Children of OrcType.
    *                 Wraps a scala dataType into a Orc like dataType  (e.g. OrcLong)
    *  @param columnVector the VectorizedRowBatch. Contains the whole batch data which will
    *               written with a Writer into a Orc File.
    *  @return a Children of ColumnVector (e.g. LongColumnVector)
    */
  def createBatchColumnVectorInstances(orcType: OrcType, columnVector: ColumnVector): ColumnVector = {
    orcType match{
      case _:OrcInt | _:OrcBigInt | _:OrcSmallInt |
           _:OrcTinyInt | _:OrcBoolean | _:OrcDate  =>
        columnVector.asInstanceOf[LongColumnVector]
      case _:OrcString | _:OrcChar | _:OrcVarChar =>
        columnVector.asInstanceOf[BytesColumnVector]
      case _:OrcDouble | _:OrcFloat =>
        columnVector.asInstanceOf[DoubleColumnVector]
      case orcArray:OrcArray =>
        val listColumnVector: ListColumnVector = columnVector.asInstanceOf[ListColumnVector]
        listColumnVector.child = createBatchColumnVectorInstances(
          orcArray.getValue.head,
          listColumnVector.child
        )
        listColumnVector
      case _:OrcStruct =>
        columnVector.asInstanceOf[StructColumnVector]
      case _:OrcTimestamp =>
        columnVector.asInstanceOf[TimestampColumnVector]
      case orcMap:OrcMap =>
        val mapColumnVector: MapColumnVector = columnVector.asInstanceOf[MapColumnVector]
        mapColumnVector.keys = createBatchColumnVectorInstances(
          orcMap.getValue.keySet.head,
          mapColumnVector.keys
        )
        mapColumnVector.values = createBatchColumnVectorInstances(
          orcMap.getValue.values.head,
          mapColumnVector.values
        )
        mapColumnVector
      case _:OrcDecimal =>
        columnVector.asInstanceOf[DecimalColumnVector]
    }
  }
}
