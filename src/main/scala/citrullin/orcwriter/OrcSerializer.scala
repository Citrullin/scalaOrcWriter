package citrullin.orcwriter

import citrullin.orcwriter.types._
import org.apache.orc.TypeDescription

/**
  * Created by citrullin on 25.03.17.
  */
object OrcSerializer {
  /** create a Orc C Schema String with a given OrcStruct
    *
    *  @param struct a OrcStruct which represents the schema
    *  @return the C schema
    */
  def createStringSchema(struct: OrcStruct):String =
    OrcSchemaCreator.createTypeDescription(struct).toString

  /** create a Orc Json Schema String with a given OrcStruct
    *
    *  @param struct a OrcStruct which represents the schema
    *  @return the Json Schema
    */
  def createJsonSchema(struct: OrcStruct):String =
    OrcSchemaCreator.createTypeDescription(struct).toJson

  /** create a Orc Java TypeDescription Schema with a given OrcStruct
    *
    *  @param struct a OrcStruct which represents the schema
    *  @return the TypeDescription
    */
  def createTypeDescriptionSchema(struct: OrcStruct): TypeDescription =
    OrcSchemaCreator.createTypeDescription(struct)

  def createWriter(path: String, structSchema: OrcStruct): OrcWriter =
    OrcWriterCreator.createWriter(path, structSchema)
}
