package pl.agilevision.hardware.um7.exceptions;

/**
 * Thrown if the given operation didn't finish before the timeout
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class OperationTimeoutException extends Exception {
  public OperationTimeoutException() {
  }

  public OperationTimeoutException(String message) {
    super(message);
  }

  public OperationTimeoutException(String message, Throwable cause) {
    super(message, cause);
  }

  public OperationTimeoutException(Throwable cause) {
    super(cause);
  }

  public OperationTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
