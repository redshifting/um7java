package pl.agilevision.hardware.um7.impl;

import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agilevision.hardware.um7.UM7Client;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of the UM7 client
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class DefaultUM7Client implements UM7Client {

  private static final int DATA_BITS = 8;
  private static final int STOP_BITS = 1;
  private static final double NANOSECONDS_MULTIPLIER = 1.0e9;
  private static final long READ_DELAY_IN_NANOSECONDS = 10;

  private SerialPort serialPort;
  private String deviceName;
  private String devicePort;
  private int baudRate;
  private boolean connected;

  private static final Logger LOG = LoggerFactory.getLogger(DefaultUM7Client.class);
  private static final Map<Integer, Integer> baudRates;
  static
  {
    baudRates = new HashMap<>();
    baudRates.put(9600, 0);
    baudRates.put(14400, 1);
    baudRates.put(19200, 2);
    baudRates.put(38400, 3);
    baudRates.put(57600, 4);
    baudRates.put(115200, 5);
    baudRates.put(128000, 6);
    baudRates.put(153600, 7);
    baudRates.put(230400, 8);
    baudRates.put(256000, 9);
    baudRates.put(460800, 10);
    baudRates.put(921600, 11);
  }


  public DefaultUM7Client(final String deviceName, final String devicePort) throws DeviceConnectionException {
    this(deviceName, devicePort, UM7Constants.Defaults.BAUD_RATE);
  }

  public DefaultUM7Client(final String deviceName, final String devicePort, int baudRate) throws DeviceConnectionException {
    this.deviceName = deviceName;
    this.devicePort = devicePort;
    this.baudRate = baudRate;

    connect();
  }

  @Override
  public void connect() throws DeviceConnectionException {
    LOG.info("Connecting to the device '{}' via port '{}' at baud rate {}",
        deviceName, devicePort, baudRate);

    try {

      this.serialPort = SerialPort.getCommPort(devicePort);
      final boolean opened = this.serialPort.openPort();

      if (opened){
        this.serialPort.setBaudRate(baudRate);
        this.serialPort.setNumDataBits(DATA_BITS);
        this.serialPort.setNumStopBits(STOP_BITS);
        this.serialPort.setParity(SerialPort.NO_PARITY);
        this.connected = true;

        LOG.info("Connected to the device '{}' via port '{}' at baud rate {}",
            deviceName, devicePort, baudRate);
      } else {
        LOG.error("Failed to connect to the device '{}' via port '{}' at baud rate {}",
            deviceName, devicePort, baudRate);
        throw new DeviceConnectionException("Failed to connect to the device");
      }
    } catch (final Exception e){
      LOG.error("Connecting to the device '{}' via port '{}' at baud rate {}",
          deviceName, devicePort, baudRate, e);

      throw new DeviceConnectionException("Failed to connect to the device", e);
    }
  }

  @Override
  public void disconnect() throws DeviceConnectionException {

    if (serialPort == null){
      connected = false;
    }

    if (!connected){
      return;
    }


    try{
      LOG.info("Disconnecting from the device '{}'",
          deviceName);
      this.serialPort.closePort();
    } catch (final Exception e){
      LOG.error("Error when trying to disconnect from the device '{}'",
          deviceName, e);
    }

    this.connected = false;
  }

  @Override
  public boolean isConnected() {
    return connected;
  }

  @Override
  public int readByte() {
    byte bytes[] = new byte[1];
    serialPort.readBytes(bytes, 1);
    return bytes[0] & 0xFF;
  }


  @Override
  public UM7Packet readPacket() throws DeviceConnectionException {
    return this.readPacket(0.1f);
  }

  /** Scans for and partially parses new data packets. Binary data can then be sent to data parser
   :return: Parsed packet info */
  public UM7Packet readPacket(float timeout) throws DeviceConnectionException {
    final long timeoutInNanoseconds = (long) (timeout * NANOSECONDS_MULTIPLIER);
    int packetFound = 0;
    long t0 = System.nanoTime();

    while (System.nanoTime() - t0 < timeoutInNanoseconds) {
      try {
        if (serialPort.bytesAvailable() >= 3) {
          int byte1 = this.readByte();
          if (byte1 == 's') {
            int byte2 = this.readByte();
            if (byte2 == 'n') {
              int byte3 = this.readByte();
              if (byte3 == 'p') {
                packetFound = 1;
                break;
              }
            }
          }
        } else {
          TimeUnit.MILLISECONDS.sleep(READ_DELAY_IN_NANOSECONDS);
        }
      } catch (InterruptedException e) {
        LOG.warn("Program interrupted");
      }
    }

    int hasdata = 0;
    int commandfailed = 0;
    int startaddress = 0;
    byte[] data = null;
    int timeouted = 1;

    if (packetFound == 0) {
      timeouted = 1;
    } else {
      timeouted = 0;

      int pt = this.readByte() & 0xFF;
      hasdata = pt & 0b10000000;
      int isbatch = (pt & 0b01000000);
      int numdatabytes = ((pt & 0b00111100) >> 2) * 4;

      commandfailed = pt & 0b00000001;
      int hidden = (byte)(pt & 0b00000010);
      if (isbatch == 0 ) {
        numdatabytes = 4;
      }

      startaddress = this.readByte() ;
      LOG.debug(String.format("Pack read, pt: %s, sa: %X bytes %d",
        String.format("%8s", Integer.toBinaryString(pt)).replace(' ', '0'), startaddress, numdatabytes));

      while (serialPort.bytesAvailable() < numdatabytes) {
        ;
      }

      if (hasdata != 0 ) {
        data = new byte[numdatabytes];
        serialPort.readBytes(data, numdatabytes);
      } else {
        data = null; // False
      }

      byte[] cs_bytes = new byte[2];
      serialPort.readBytes(cs_bytes, 2);
      final DataInputStream is = new DataInputStream(new ByteArrayInputStream(cs_bytes));
      int cs = 0;
      try {
        cs = is.readShort();
      } catch (IOException e) {
        e.printStackTrace();
      }

      int ocs = 0;
      ocs += (int)'s';
      ocs += (int)'n';
      ocs += (int)'p';
      ocs += pt;
      ocs += startaddress;
      if (data != null) {
        for (byte b:data) {
          ocs += b & 0xFF;
        }
      }

      if (hidden != 0) {
        startaddress |= UM7Constants.Registers.REG_HIDDEN;
      }
      if (ocs != cs) {
        LOG.error(String.format("bad checksum: %d (should be: %d)", cs, ocs));
        hasdata = 0;   // was for all ValueError
        commandfailed = 0;
        startaddress = 0;
        data = null;
      }
    }
    return new UM7Packet(packetFound == 1, hasdata == 1, startaddress, data, commandfailed == 1, timeouted == 1);
  }


  @Override
  public UM7Packet readRegistry(final int start, final int length, final float timeout)
      throws OperationTimeoutException, DeviceConnectionException {
    long ns_timeout = (long) (timeout * 1.0e9);

    int hidden = (start & UM7Constants.Registers.REG_HIDDEN);
    int sa = (start & 0xFF);
    int pt = 0x0;
    if (length != 0) {
      pt = 0b01000000;
    }
    pt |= (length << 2);
    if (hidden != 0) {
      pt |= 0b00000010;
    }

    byte[] ba = this.makePack(pt, sa, null);
    serialPort.writeBytes(ba, ba.length);

    long t0 = System.nanoTime();
    while (System.nanoTime() - t0 < ns_timeout) { // While elapsed time is less than timeout
      UM7Packet packet = readPacket();
      if (packet.startaddress == start) {
        return packet;
      }
    }
    return new UM7Packet(false, false, start, null, true, true);
  }

  @Override
  public UM7Packet writeRegistry(final int start, final int length, final byte[] data,
                                 final float timeout, boolean noRead)
      throws OperationTimeoutException, DeviceConnectionException {
    long ns_timeout = (long) (timeout * 1.0e9);
    short hidden = (short)(start & UM7Constants.Registers.REG_HIDDEN);
    byte sa = (byte) (start & 0xFF);
    byte pt = (byte) (0x0);
    if (data != null) {
      pt = (byte)0b11000000;
      pt |= (length << 2);
    }
    if (hidden != 0 ) {
      pt |= 0b00000010;
    }
    byte[] ba = this.makePack(pt, sa, data);
    serialPort.writeBytes(ba, ba.length);
    if (noRead) {
      // todo seems we cant flush in jSerialCom
      //serial.getInputStream().flush()
      return new UM7Packet(false, false, start, null, true, false);
    }

    long t0 = System.nanoTime();
    while (System.nanoTime() - t0 < ns_timeout) { // While elapsed time is less than timeout
      UM7Packet packet = this.readPacket();
      if (packet.startaddress == start) {
        return packet;
      }
    }
    return new UM7Packet(false, false, start, null, true, true);
  }


  @Override
  public boolean setBaudRate(int baudRate) throws DeviceConnectionException, OperationTimeoutException {
    int new_baud = baudRates.get(baudRate) << 28;
    UM7Packet p = this.readRegistry(UM7Constants.Registers.CREG_COM_SETTINGS);
    if (p.commandfailed){
      return false;
    }
    // big endian unpack without java.nio
    int cr = p.data[0];
    cr = cr << 8 | p.data[1];
    cr = cr << 8 | p.data[2];
    cr = cr << 8 | p.data[3];

    LOG.debug(String.format("Current baudrate %032b", cr));
    cr &= 0x0fffffff;
    cr |= new_baud;
    LOG.debug(String.format("Current baudrate %032b", cr));

    byte[] ba = new byte[4];
    ba[0] = (byte) ((cr >> 24) & 0xFF);
    ba[1] = (byte) ((cr >> 16) & 0xFF);
    ba[2] = (byte) ((cr >> 8) & 0xFF);
    ba[3] = (byte) (cr & 0xFF);

    p = this.writeRegistry(UM7Constants.Registers.CREG_COM_SETTINGS,
        (byte)1, ba, 0.1f, true);
    if (p.commandfailed) {
      return false;
    }
    serialPort.setBaudRate(baudRate);
    return true;
  }


  @Override
  public UM7Packet readRegistry(final int start)
      throws OperationTimeoutException, DeviceConnectionException {
    return this.readRegistry(start, (byte) 0);
  }

  @Override
  public UM7Packet writeRegistry(final int start)
    throws OperationTimeoutException, DeviceConnectionException {
    return writeRegistry(start, (byte)1, null, UM7Constants.Defaults.OPERATION_TIMEOUT, false);
  }

  private UM7Packet readRegistry(final int start, final int length)
      throws OperationTimeoutException, DeviceConnectionException {
    return readRegistry(start, length, UM7Constants.Defaults.OPERATION_TIMEOUT);
  }


  private byte[] makePack(int pt, int sa, byte[] payload) {
    int payloadLength = 0;
    if (payload != null) {
      payloadLength = payload.length;
    }

    byte[] ba = new byte[7 + payloadLength];
    ba[0] = 's';
    ba[1] = 'n';
    ba[2] = 'p';
    ba[3] = (byte) pt;
    ba[4] = (byte) sa;
    int i = 5;
    if (payload != null) {
      for (byte b : payload) {
        ba[i++] = b;
      }
    }

    short cs = 0;
    for (int j=0; j < i; j++) {
      cs += ba[j] & 0xff;  // & 0xff this gets unsigned value from byte
    }
    ba[i++] = (byte)( (cs >> 8) & 0xFF);
    ba[i] = (byte)( cs & 0xFF);
    return ba;
  }


}
