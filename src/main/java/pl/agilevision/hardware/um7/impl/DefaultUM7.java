package pl.agilevision.hardware.um7.impl;

import pl.agilevision.hardware.um7.UM7;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;

/**
 * Created by volodymyr on 07.11.16.
 */
public class DefaultUM7 implements UM7 {
  public boolean zeroGyros() throws DeviceConnectionException, OperationTimeoutException {
    return false;
  }

  public boolean resetEkf() throws DeviceConnectionException, OperationTimeoutException {
    return false;
  }

  public boolean resetToFactory() throws DeviceConnectionException, OperationTimeoutException {
    return false;
  }

  public boolean setMagReference() throws DeviceConnectionException, OperationTimeoutException {
    return false;
  }

  public boolean setHomePosition() throws DeviceConnectionException, OperationTimeoutException {
    return false;
  }

  public boolean flashCommit() throws DeviceConnectionException, OperationTimeoutException {
    return false;
  }

  public String getFirmwareVersion() throws DeviceConnectionException, OperationTimeoutException {
    return null;
  }
}
