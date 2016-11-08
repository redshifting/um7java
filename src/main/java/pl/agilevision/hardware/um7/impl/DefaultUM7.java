package pl.agilevision.hardware.um7.impl;

import com.fazecast.jSerialComm.SerialPort;
import pl.agilevision.hardware.um7.UM7;
import pl.agilevision.hardware.um7.UM7Client;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class DefaultUM7 implements UM7 {

  private UM7Client um7Client;

  public DefaultUM7(UM7Client um7Client) {
    this.um7Client = um7Client;
  }

  public boolean zeroGyros() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.um7Client.writeRegistry(UM7Constants.Commands.ZERO_GYROS);
      return (! p.commandfailed);
  }

  public boolean resetEkf() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.um7Client.writeRegistry(UM7Constants.Commands.RESET_EKF);
      return (! p.commandfailed);
  }

  public boolean resetToFactory() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.um7Client.writeRegistry(UM7Constants.Commands.RESET_TO_FACTORY);
      return (! p.commandfailed);
  }

  public boolean setMagReference() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.um7Client.writeRegistry(UM7Constants.Commands.SET_MAG_REFERENCE);
      return (! p.commandfailed);
  }

  public boolean setHomePosition() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.um7Client.writeRegistry(UM7Constants.Commands.SET_HOME_POSITION);
      return (! p.commandfailed);
  }

  public boolean flashCommit() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.um7Client.writeRegistry(UM7Constants.Commands.FLASH_COMMIT);
      return (! p.commandfailed);
  }

  public String getFirmwareVersion() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.um7Client.readRegistry(UM7Constants.Commands.GET_FW_REVISION);
      if (p.commandfailed) {
          return "";
      }

      return p.data.toString();
  }
}
