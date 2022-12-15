package mate.jdbc.exeption;

public class DataProcessingException extends RuntimeException {
  public DataProcessingException(String message, Throwable exp) {
    super(message, exp);
  }
}
