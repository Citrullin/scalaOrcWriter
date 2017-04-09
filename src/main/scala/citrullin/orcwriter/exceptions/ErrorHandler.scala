package citrullin.orcwriter.exceptions

import citrullin.orcwriter.types.OrcType
import org.apache.hadoop.hive.ql.exec.vector.ColumnVector

/**
  * Created by citrullin on 26.03.17.
  */
object ErrorHandler {
  def typeMismatch(exceptedType: Any, actualType: Any): OrcSerializationException = {
    new OrcSerializationException(
      "Type mismatch. Excepted Type: "+exceptedType.getClass+" "+
      "Actual Type: "+actualType.getClass+". "
    )
  }
}
