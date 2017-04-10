# Scala OrcWriter
## Short Description
A simple library to write apache orc files to the local file system or to hdfs.
You can find some examples in citrullin.orcwriter.examples

## Motivation
You may ask why you need additional scala library for writing
orc files while there is already the java core API.
The apache orc core API is a low level API for writing the actual files.
You have to deal with ColumnVectors, complex Maps, Arrays etc. by yourself.
When you use the Scala OrcWriter you don't need to care about all these things.
You can simply define your object with a OrcStruct, put it in a list,
and write it to a file.

## Usage:
There are two ways to use the library. The implicit API or the standard
library. The implicit API extend a OrcStruct List with a method write.

### Implicit API

```scala
  import citrullin.orcwriter.OrcWriterImplicitAPI._
  import citrullin.orcwriter.types._

  //Create a List with 100 elements
  val data: List[OrcStruct] = List.tabulate(100)(index => {
    new OrcStruct(
      List(
        new OrcField("property1", new OrcString(""))
      )
    )
  })

  //Write this List a orc file
  data.write("/home/citrullin/orcFiles/test.orc")
```

### Standard API
```scala
  import citrullin.orcwriter.OrcWriterAPI
  import citrullin.orcwriter.types._

  //Create a schema orcStruct object. Used the generate the orcSchema
  val schema: OrcStruct = new OrcStruct(
    List(
      new OrcField("property1", new OrcString(""))
    )
  )

  //Create 100 orcStructs with one field, named property1, with empty string
  val data: List[OrcStruct] = List.tabulate(100)(index => {
    new OrcStruct(
      List(
        new OrcField("property1", new OrcString(""))
      )
    )
  })

  OrcWriterAPI.createWriter("/home/citrullin/orcFiles/test2.orc" , schema)
    .write(data)
```

## Available OrcTypes:

| Orc type | Scala object |
| -------- | ------------ |
| array    | OrcArray     |
| binary   | OrcChar      |
| bigint   | OrcBigInt    |
| boolean  | OrcBoolean   |
| char     | OrcChar      |
| date     | OrcDate      |
| decimal  | OrcDecimal   |
| double   | OrcDouble    |
| float    | OrcFloat     |
| int      | OrcInt       |
| map      | OrcMap       |
| smallint | OrcSmallInt  |
| string   | OrcString    |
| struct   | OrcStruct    |
| timestamp| OrcTimestamp |
| tinyint  | OrcTinyInt   |
| ~~uniontype~~| not supported|
| varchar  | OrcVarChar   |

