/**
  * Created by citrullin on 4/7/17.
  */

import citrullin.orcserilizer.types.{OrcMap, _}
import citrullin.orcserilizer.OrcSchemaCreator
import org.apache.orc.TypeDescription
import org.specs2.matcher._
import scala.collection.JavaConverters._


import scala.util.Random

class OrcSchemaCreatorSpec extends org.specs2.mutable.Specification{
  "Simple type test" >> {
    "createTypeDescription must return a OrcStruct typeDescription with a property test of type" >> {
      "string when used a OrcString in the OrcField" >> {
        val simpleStringStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcString(""))
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleStringStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "string"
      }

      "char when used a OrcChar in the OrcField" >> {
        val simpleCharStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcChar(""))
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleCharStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "char"
      }

      "varchar when used a OrcVarChar in the OrcField" >> {
        val simpleVarCharStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcVarChar(""))
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleVarCharStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "varchar"
      }

      "boolean when used a OrcBoolean in the OrcField" >> {
        val simpleBooleanStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcBoolean(0))
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleBooleanStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "boolean"
      }

      "tinyint when used a OrcTinyInt in the OrcField" >> {
        val simpleTinyIntStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcTinyInt(0))
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleTinyIntStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "tinyint"
      }

      "smallint when used a OrcSmallInt in the OrcField" >> {
        val simpleSmallIntStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcSmallInt(0))
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleSmallIntStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "smallint"
      }

      "int when used a OrcInt in the OrcField" >> {
        val simpleIntStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcInt(0))
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleIntStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "int"
      }

      "bigint when used a OrcBigInt in the OrcField" >> {
        val simpleBigIntStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcBigInt(0))
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleBigIntStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "bigint"
      }

      "bigint when used a OrcBigInt in the OrcField" >> {
        val simpleBigIntStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcBigInt(0))
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleBigIntStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "bigint"
      }

      "array when used a OrcArray in the OrcField" >> {
        val simpleStringArrayStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcArray(
              List(new OrcString(""))
            )
            )
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleStringArrayStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "array"
      }

      "map when used a OrcMap in the OrcField" >> {
        val simpleStringMapStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcMap(
              Map(new OrcString("mapKey") -> new OrcString("mapValue"))
            )
            )
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(simpleStringMapStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "map"
      }
    }
  }

  "Complex structure type test" >> {
    "createTypeDescription must return a OrcStruct typeDescription with a property test of type" >> {
      "map with an inner string key and a inner int value when" +
        "used a OrcString key and a OrcInt value" >> {

        val complexMapStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcMap(
              Map(new OrcString("key") -> new OrcInt(1))
            )
            )
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(complexMapStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual("map") and(
          typeDescription.getChildren.asScala.head.getChildren.asScala.head.getCategory.getName mustEqual "string"
          ) and(
          typeDescription.getChildren.asScala.head.getChildren.asScala(1).getCategory.getName mustEqual "int"
        )
      }

      "array with an inner string value when used a OrcString in a OrcArray" >> {
        val complexStringArrayStruct: OrcStruct = new OrcStruct(
          List(
            new OrcField("test", new OrcArray(
              List(new OrcString(""))
            )
            )
          )
        )
        val typeDescription: TypeDescription = OrcSchemaCreator.createTypeDescription(complexStringArrayStruct)

        typeDescription.getChildren.asScala.head.getCategory.getName mustEqual "array" and(
          typeDescription.getChildren.asScala.head.getChildren.asScala.head.getCategory.getName mustEqual "string"
        )
      }
    }
  }
}
