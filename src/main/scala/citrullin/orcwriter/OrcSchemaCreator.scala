package citrullin.orcwriter

import citrullin.orcwriter.exceptions.ErrorHandler
import citrullin.orcwriter.types._
import org.apache.orc.TypeDescription

/**
  * Created by citrullin on 25.03.17.
  */
object OrcSchemaCreator {
  def createTypeDescription(struct: OrcStruct): TypeDescription ={
    val typeDescription: TypeDescription = TypeDescription.createStruct()

    struct.list.map(field =>
      if(field.value.isValid){
        typeDescription.addField(field.key, getTypeDescriptionByField(field))
      }else{
        ErrorHandler.wrongOrcType(field.value, field.value.getUncleanValue)
      }
    )

    typeDescription
  }

  def getTypeDescriptionByField(field: OrcField): TypeDescription = {
    getTypeDescriptionByOrcType(field.value)
  }

  def getTypeDescriptionByOrcType(orcType: OrcType): TypeDescription = {
    orcType match{
      case value: OrcBigInt => getBigIntTypeDescription(value)
      case value: OrcBinary => getBinaryTypeDescription(value)
      case value: OrcDouble => getDoubleTypeDescription(value)
      case value: OrcString => getStringTypeDescription(value)
      case value: OrcTinyInt => getTinyIntTypeDescription(value)
      case value: OrcSmallInt => getSmallIntTypeDescription(value)
      case value: OrcDecimal => getDecimalTypeDescription(value)
      case value: OrcArray => getArrayTypeDescription(value)
      case value: OrcMap => getMapTypeDescription(value)
      case value: OrcBoolean => getBooleanTypeDescription(value)
      case value: OrcChar => getCharTypeDescription(value)
      case value: OrcVarChar => getVarCharTypeDescription(value)
      case value: OrcStruct => getStructTypeDescription(value)
      case value: OrcDate => getDateTypeDescription(value)
      case value: OrcTimestamp => getTimestampTypeDescription(value)
      case value: OrcInt => getIntTypeDescription(value)
      case value: OrcFloat => getFloatTypeDescription(value)
    }
  }

  def getBinaryTypeDescription(value: OrcBinary): TypeDescription = {
    TypeDescription.createBinary()
  }

  def getBigIntTypeDescription(value: OrcBigInt): TypeDescription = {
    TypeDescription.createLong()
  }

  def getBooleanTypeDescription(value: OrcBoolean): TypeDescription = {
    TypeDescription.createBoolean()
  }

  def getCharTypeDescription(value: OrcChar): TypeDescription = {
    TypeDescription.createChar()
  }

  def getDateTypeDescription(value: OrcDate): TypeDescription = {
    TypeDescription.createDate()
  }

  def getDecimalTypeDescription(value: OrcDecimal): TypeDescription = {
    TypeDescription.createVarchar()
  }

  def getDoubleTypeDescription(value: OrcDouble): TypeDescription = {
    TypeDescription.createDouble()
  }

  def getFloatTypeDescription(value: OrcFloat): TypeDescription = {
    TypeDescription.createFloat()
  }

  def getIntTypeDescription(value: OrcInt): TypeDescription = {
    TypeDescription.createInt()
  }

  def getArrayTypeDescription(value: OrcArray): TypeDescription = {
    TypeDescription.createList(getTypeDescriptionByOrcType(value.getValue.head))
  }

  def getMapTypeDescription(orcMap: OrcMap): TypeDescription = {
    TypeDescription.createMap(
      getTypeDescriptionByOrcType(orcMap.getValue.keySet.toList.head),
      getTypeDescriptionByOrcType(orcMap.getValue.values.toList.head)
    )
  }

  def getSmallIntTypeDescription(value: OrcSmallInt): TypeDescription = {
    TypeDescription.createShort()
  }

  def getStringTypeDescription(value: OrcString): TypeDescription = {
    TypeDescription.createString()
  }

  def getStructTypeDescription(struct: OrcStruct): TypeDescription = {
    val typeDescription: TypeDescription = TypeDescription.createStruct()
    struct.getValue.map(field =>{
      typeDescription.addField(field.key, getTypeDescriptionByField(field))
    })
    typeDescription
  }

  def getTimestampTypeDescription(value: OrcTimestamp): TypeDescription = {
    TypeDescription.createTimestamp()
  }

  def getTinyIntTypeDescription(value: OrcTinyInt): TypeDescription = {
    TypeDescription.createByte()
  }

  def getVarCharTypeDescription(value: OrcVarChar): TypeDescription = {
    TypeDescription.createVarchar()
  }
}
