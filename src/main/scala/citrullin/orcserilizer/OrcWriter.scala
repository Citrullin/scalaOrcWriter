package citrullin.orcserilizer

import citrullin.orcserilizer.exceptions.{ErrorHandler, OrcSerializationException}
import citrullin.orcserilizer.types._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hive.ql.exec.vector._
import org.apache.orc.{OrcFile, TypeDescription, Writer}

/**
  * Created by citrullin on 25.03.17.
  */
class OrcWriter(
                 writer: Writer, batch: VectorizedRowBatch,
                 columnVectorList: List[ColumnVector], configuration: Configuration,
                 path: String
               ) {

  var rowBatchSize: Int = 0

  /** writes a list of OrcStructs into a Orc File
    *
    *  @param structList a list of orcStructs
    *  @return a boolean. wasWriteSuccessful?. True -> yes, false -> no
    */
  def write(structList: List[OrcStruct]): Boolean = {
    //If the writeRows List result doesn't contain a false return true
    !writeRows(
      structList.map(_.getValue), columnVectorList,
      batch, writer
    ).exists(!_)
  }


  /** writes given rows to a orc vertorized batch
    *
    *  @param rows the given rows, extracted from OrcStructs
    *  @param columnVectorList a List of Children of ColumnVector
    *  @param batch the VectorizedRowBatch which contains the batch
    *               data which will be written into a orc file
    *  @return a list of wasWriteSuccessful?. false = no true = yes.
    *          Each entry represents one row.
    */
  private def writeRows(
                 rows: List[List[OrcField]],
                 columnVectorList: List[ColumnVector],
                 batch: VectorizedRowBatch,
                 writer: Writer
               ): List[Boolean] = {

    val rowResult: List[Boolean] = rows.map(value => {
      writeRow(value, columnVectorList)
    })

    writer.close()
    rowResult
  }

  private def writeBatch(): Unit = {
      batch.cols = columnVectorList.toArray
      writer.addRowBatch(batch)
      batch.reset()
      rowBatchSize = 0
  }

  /** writes a given row to a orc vertorized batch
    *
    *  @param rowFields the given Fields of one row (Exctracted from a OrcStruct)
    *  @param columnVectorList is a list with ColumnVector which holds the Orc file column file data
    *  @return a list of wasWriteSuccessful?. false = no true = yes.
    *          Each entry is a field
    */
  private def writeRow(rowFields: List[OrcField], columnVectorList: List[ColumnVector]): Boolean = {
    val wasFieldWriteSuccessfulList: List[Boolean] = rowFields.zipWithIndex.map{
      case (field: OrcField, columnIndex: Int) =>
        if(writeField(field.value, columnVectorList(columnIndex), batch.size))
          true
        else
          false
    }

    //batch size is a row count
    batch.size += 1
    //the batch has a maximum size of 1024.
    // Will count up for each row, each array entry and each map entry
    rowBatchSize += 1
    if(rowBatchSize >= batch.getMaxSize -1){
      writeBatch()
    }

    if(wasFieldWriteSuccessfulList.exists(!_))
      false
    else
      true
  }

  /** writes given rows to a columnVector
    *
    *  @param value the value of a given field (OrcType) e.g OrcField or in a array a element
    *               (Exctracted from a OrcStruct)
    *  @param columnVector a ColumnVector which holds the Orc file data for one column
    *  @param index the index int value where the data want to written in.
    *        e.g. row or in a array the position
    *  @return a boolean -> wasWriteSuccessful? true => yes false => no
    */
  private def writeField(value: OrcType, columnVector: ColumnVector, index: Int): Boolean = {
    value match{
      case value: OrcBigInt => writeBigIntField(value, columnVector, index)
      case value: OrcString => writeStringField(value, columnVector, index)
      case value: OrcArray => writeArrayField(value, columnVector, index)
      case value: OrcMap => writeMapField(value, columnVector, index)
      case value: OrcBoolean => writeBooleanField(value, columnVector, index)
      case value: OrcChar => writeCharField(value, columnVector, index)
    }
  }

  /** writes a given OrcBigInt to the ColumnVector
    *
    *  @param bigIntValue the OrcBigInt which contains the value
    *  @param columnVector a ColumnVector which holds the Orc file data for one column
    *  @return a boolean -> wasWriteSuccessful? true => yes false => no
    */
  private def writeBigIntField(bigIntValue: OrcBigInt, columnVector: ColumnVector, rowIndex: Int): Boolean ={
    columnVector match{
      case columnVector:LongColumnVector =>
        columnVector.vector{rowIndex} = bigIntValue.getValue
        true
      case _ =>
        ErrorHandler.typeMismatch(columnVector, bigIntValue)
        false
    }
  }

  /** writes a given OrcBoolean to the ColumnVector
    *
    *  @param booleanValue the OrcBoolean which contains the value
    *  @param columnVector a ColumnVector which holds the Orc file data for one column
    *  @return a boolean -> wasWriteSuccessful? true => yes false => no
    */
  private def writeBooleanField(booleanValue: OrcBoolean, columnVector: ColumnVector, rowIndex: Int): Boolean ={
    columnVector match{
      case columnVector:LongColumnVector =>
        columnVector.vector{rowIndex} = booleanValue.getValue
        true
      case _ =>
        ErrorHandler.typeMismatch(columnVector, booleanValue)
        false
    }
  }

  /** writes a given OrcChar to the ColumnVector
    *
    *  @param charValue the OrcChar which contains the value
    *  @param columnVector a ColumnVector which holds the Orc file data for one column
    *  @return a boolean -> wasWriteSuccessful? true => yes false => no
    */
  private def writeCharField(charValue: OrcChar, columnVector: ColumnVector, rowIndex: Int): Boolean ={
    columnVector match{
      case columnVector:BytesColumnVector =>
        columnVector.setVal(rowIndex, Array(charValue.getValue))
        true
      case _ =>
        ErrorHandler.typeMismatch(columnVector, charValue)
        false
    }
  }

  /** writes a given OrcString to the ColumnVector
    *
    *  @param stringValue the OrcLong which contains the value
    *  @param columnVector a ColumnVector which holds the Orc file data for one column
    *  @return a boolean -> wasWriteSuccessful? true => yes false => no
    */
  private def writeStringField(stringValue: OrcString, columnVector: ColumnVector, rowIndex: Int): Boolean = {
    columnVector match{
      case columnVector:BytesColumnVector =>
        columnVector.setVal(rowIndex, stringValue.getValue)
        true
      case _ =>
        ErrorHandler.typeMismatch(columnVector, stringValue)
        false
    }
  }

  /** write a OrcArray with OrcTypes to a ColumnVector
    *
    *  @param list a array (scala list) of OrcType, The children of the list
    *  @param columnVector the columnVector which will hold the represented orc file data
    *               written with a Writer into a Orc File.
    *  @return a boolean. Were all writes successful? true -> yes false -> no
    */
  private def writeArrayField(list: OrcArray, columnVector: ColumnVector, rowIndex: Int): Boolean = {
    columnVector match{
      case arrayColumnVector:ListColumnVector =>
        arrayColumnVector.lengths{rowIndex} = list.getValue.length
        arrayColumnVector.offsets{rowIndex} = arrayColumnVector.childCount

        list.getValue.map(orcType => {
          writeField(orcType, arrayColumnVector.child, arrayColumnVector.childCount)
          rowBatchSize += 1
          arrayColumnVector.childCount += 1
          if(rowBatchSize >= batch.getMaxSize -1){
            writeBatch()
          }
        })

        true
      case _ =>
        new OrcSerializationException("Missmatch of struct type and schema type")
        false
    }
  }

  /** writes a Map to a ColumnVectors by a given Map of OrcTypes
    *
    *  @param map a map of OrcType -> OrcType, The children of the map
    *  @param columnVector the columnVector which will hold the represented orc file data
    *               written with a Writer into a Orc File.
    *  @return a boolean. Were all writes successful? true -> yes false -> no
    */
  private def writeMapField(map: OrcMap, columnVector: ColumnVector, rowIndex: Int): Boolean = {
    columnVector match{
      case mapColumnVector:MapColumnVector =>
        val mapKeys: List[OrcType] = map.getValue.keySet.toList
        mapColumnVector.lengths{rowIndex} = mapKeys.length
        mapColumnVector.offsets{rowIndex} = mapColumnVector.childCount

        //Add the keys and values
        mapKeys.map(orcType => {
          writeField(orcType, mapColumnVector.keys, mapColumnVector.childCount)
          writeField(map.getValue(orcType), mapColumnVector.values, mapColumnVector.childCount)
          rowBatchSize += 2
          mapColumnVector.childCount += 1
          if(batch.size >= batch.getMaxSize -1){
            writeBatch()
          }
        })
        true
    }
  }
}
