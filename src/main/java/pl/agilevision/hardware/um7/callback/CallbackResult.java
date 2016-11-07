package pl.agilevision.hardware.um7.callback;

/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class CallbackResult<T> {
  private OperationResult operationResult;
  private T data;

  public CallbackResult() {
  }

  public CallbackResult(OperationResult operationResult, T data) {
    this.operationResult = operationResult;
    this.data = data;
  }

  public OperationResult getOperationResult() {
    return operationResult;
  }

  public void setOperationResult(OperationResult operationResult) {
    this.operationResult = operationResult;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
