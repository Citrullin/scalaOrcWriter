package citrullin.orcserilizer.exceptions

/**
  * Created by citrullin on 25.03.17.
  */
class OrcSerializationException(message: String = null, cause: Throwable = null) extends
  RuntimeException(OrcSerializationException.defaultMessage(message, cause), cause)

object OrcSerializationException{
  def defaultMessage(message: String, cause: Throwable) =
    if (message != null) message
    else if (cause != null) cause.toString()
    else null
}