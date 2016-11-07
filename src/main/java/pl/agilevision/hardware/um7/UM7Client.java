package pl.agilevision.hardware.um7;

import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;

/**
 * Low-level UM7 client for IO operations with the UM7 device
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public interface UM7Client {

  /**
   * Reads the device registry
   * @param start start offset
   * @param length amount of data
   * @param timeout timeout
   * @return registry value
   * @throws OperationTimeoutException if the operation didn't finish in the given timeout
   */
  UM7Packet readRegistry(final long start, final long length, final float timeout)
      throws OperationTimeoutException, DeviceConnectionException;
  UM7Packet writeRegistry(final long start, final long length, final byte[] data, final boolean noRead)
      throws OperationTimeoutException, DeviceConnectionException;
}