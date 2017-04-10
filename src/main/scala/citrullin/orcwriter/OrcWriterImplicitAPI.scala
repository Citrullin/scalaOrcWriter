package citrullin.orcwriter

import citrullin.orcwriter.types.OrcStruct

/**
  * Created by citrullin on 09.04.17.
  */
object OrcWriterImplicitAPI {
  implicit class orcList(structList: List[OrcStruct]){
    def write(path: String): Boolean = {
      OrcWriterCreator.createWriter(path, structList.head).write(structList)
    }
    def getJsonSchema: String = OrcSchemaCreator.createTypeDescription(structList.head).toJson
    def getStringSchema: String = OrcSchemaCreator.createTypeDescription(structList.head).toString
  }
}
