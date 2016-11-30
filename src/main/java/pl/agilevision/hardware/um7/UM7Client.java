package pl.agilevision.hardware.um7;

import pl.agilevision.hardware.um7.data.BaseAttribute;
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
   * Connect to the device. Disconnect and connect again if already connected
   * @throws DeviceConnectionException if connection is not possible
   */
  void connect() throws DeviceConnectionException;

  /**
   * Disconnect from the device. Do nothing if already connected
   * @throws DeviceConnectionException if an error happened while trying to disconnect.
   */
  void disconnect() throws DeviceConnectionException;

  /**
   * Returns true if the client is attached to the device
   * @return true if the client is attached to the device
   */
  boolean isConnected();

  /**
   * Waits for a single packet and returns it
   * @return packet
   * @throws DeviceConnectionException if a connection error happened while waiting for a packet
   */
  UM7Packet readPacket() throws DeviceConnectionException;

  /**
   * Reads the device registry
   * @param start start address
   * @param length amount of data
   * @param timeout timeout
   * @return registry value
   * @throws OperationTimeoutException if the operation didn't finish in the given timeout
   */
  UM7Packet readRegister(final int start, final int length, final float timeout)
      throws OperationTimeoutException, DeviceConnectionException;

  /**
   * Reads the value of the given registry with a default timeout
   * @param start start address
   * @return value of the given registry
   * @throws OperationTimeoutException if timeout happened
   * @throws DeviceConnectionException if there was an issue with the connection
   */
  UM7Packet readRegister(int start)
      throws OperationTimeoutException, DeviceConnectionException;

  /**
   *
   * @param start start address
   * @return packet with the result
   * @throws OperationTimeoutException if timeout happened
   * @throws DeviceConnectionException if there was an issue with the connection
   */
  UM7Packet clearRegister(int start)
      throws OperationTimeoutException, DeviceConnectionException;

  /**
   * Write data to the registry
   * @param start start address
   * @param length amount of data
   * @param data data
   * @param timeout operation timeout
   * @param noRead whether the operation confirmation should be parsed after the write operation
   * @return data packet with the operation result
   * @throws OperationTimeoutException if timeout happened
   * @throws DeviceConnectionException if there was an issue with the connection
   */
  UM7Packet writeRegister(final int start, final int length, final byte[] data,
                          final float timeout, final boolean noRead)
      throws OperationTimeoutException, DeviceConnectionException;

  /**
   * Set data rate of certain attribute
   * @param attribute
   * @param rate - if attribute is UM7Attributes.HealthRate, HealthRate can be one of UM7Attributes.Frequency.HealthRate.*
   *             - if attribute is UM7Attributes.NMEA.* then can be one of Frequency.NMEA.*
                 - for all other attribute rate is int from 0 to 255 that defines frequency in Hz
   * @return
   */
  boolean setDataRate(BaseAttribute attribute, int rate);


  /**
   * Configures the baud rate of the device and sets the baud rate of the underlying transport
   * channel to match it
   * @param baudRate desired baud rate
   * @return true if the operation was successful
   * @throws DeviceConnectionException in a case of connection error
   * @throws OperationTimeoutException if the operation timed out
   */
  boolean setBaudRate(int baudRate) throws DeviceConnectionException, OperationTimeoutException;
}
