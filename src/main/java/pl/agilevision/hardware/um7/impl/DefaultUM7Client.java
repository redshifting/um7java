package pl.agilevision.hardware.um7.impl;

import pl.agilevision.hardware.um7.UM7Client;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;

/**
 * Default implementation of the UM7 client
 * @author Ivan Borschov (iborschov@agilevision.pl)
 */
public class DefaultUM7Client implements UM7Client {

  public UM7Packet readRegistry(final long start, final long length, final float timeout)
      throws OperationTimeoutException, DeviceConnectionException {
    return null;
  }

  public UM7Packet writeRegistry(final long start, final long length, final byte[] data, boolean noRead)
      throws OperationTimeoutException, DeviceConnectionException {
    return null;
  }
}
