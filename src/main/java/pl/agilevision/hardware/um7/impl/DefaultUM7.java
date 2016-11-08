package pl.agilevision.hardware.um7.impl;

import com.fazecast.jSerialComm.SerialPort;
import pl.agilevision.hardware.um7.UM7;
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

  private Map<String, Integer> state;
  private String dev_name;
  private String dev_port;
  private long t0;
  private SerialPort serial;


  public byte readByte() {
    byte bytes[] = new byte[1];
    serial.readBytes(bytes, 1);
    return bytes[0];
  }

  /**
   * Create new UM7 serial object with defuault Baud Rate = 115200
   * @param name
   * @param port
   * @param statevars
   */
  public DefaultUM7(String name, String port, String[] statevars) {
    this(name, port, statevars, 115200);
  }

  /**
   * Create new UM7 serial object.
   Initializes port, name, OS timer, and sensor state (dict)
   * @param name: name of object (str)
   * @param port: Virtual COM port to which the IMU is connected (str)
   * @param statevars
   * @param baud
   */
  public DefaultUM7(String name, String port, String[] statevars, int baud) {
    dev_name = name;
    dev_port = port;
    t0 = System.nanoTime();  // about on which platforms it safe to use http://stackoverflow.com/a/4588605/3479125
    state = new HashMap<>();
    for (String i : statevars) {
      state.put(i, 0);
    }

    serial = SerialPort.getCommPort(dev_port);

    boolean openedSuccessfully = serial.openPort();
    if (!openedSuccessfully) {
      System.out.println("Could not connect to UM7 "+ name);
      //System.err.println("\nCan't Opening " + serial.getSystemPortName() + ": " + serial.getDescriptivePortName());
      return;
    }

    serial.setBaudRate(baud);
    serial.setNumDataBits(8);
    serial.setNumStopBits(1);
    serial.setParity(SerialPort.NO_PARITY);
    //default is nonblocking mode, uncomment next for semiblocking:
    //serial.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);

  }

  protected void finalize(){
    if (serial != null) {
      serial.closePort();
    }
  }

  public String toString() {
    return dev_name;
  }

  /**
   * Function that catches and parses incoming data, and then updates the sensor's state to include new data. Old
   data in state is overwritten.

   :return: Newly obtained data, and updates internal sensor state
   */
  public UM7Packet catchSample() throws DeviceConnectionException {
    UM7Packet packet = this.readPacket();
    if (!packet.foundpacket) {
      return null;
    }
    //todo
//        sample = parsedatabatch(packet.data, packet.startaddress)
//        if (sample) {
//
//                self.state.update(sample)
//                return sample
//        }
    return null;
  }

  public UM7Packet readPacket() throws DeviceConnectionException {
    return this.readPacket(0.1f);
  }

  /** Scans for and partially parses new data packets. Binary data can then be sent to data parser
   :return: Parsed packet info */
  public UM7Packet readPacket(float timeout) throws DeviceConnectionException {
    long ns_timeout = (long) (timeout * 1.0e9);
    int foundpacket = 0;
    t0 = System.nanoTime();

    while (System.nanoTime() - t0 < ns_timeout) {  //While elapsed time is less than timeout
      try {
        if (serial.bytesAvailable() >= 3) {
          byte byte1 = this.readByte();
          if (byte1 == 's') {
            byte byte2 = this.readByte();
            if (byte2 == 'n') {
              byte byte3 = this.readByte();
              if (byte3 == 'p') {
                foundpacket = 1;
                break;
              }
            }
          } else {
            System.out.println("Non start paket s byte: " + byte1);
          }
        } else {
          TimeUnit.MILLISECONDS.sleep(10);
        }
      } catch (InterruptedException e) {
        System.out.print("Program interrupted");
      }
    }

    int hasdata = 0;
    int commandfailed = 0;
    byte startaddress = 0;
    byte[] data = null;
    int timeouted = 1;

    if (foundpacket == 0) {
      hasdata = 0;
      commandfailed = 0;
      startaddress = 0;
      data = null; //0
      timeouted = 1;
    } else {
      timeouted = 0;

      byte pt = this.readByte();
      System.out.print(String.format("PT %d", pt));
      hasdata = pt & 0b10000000;
      byte isbatch = (byte)(pt & 0b01000000);
      int numdatabytes = ((pt & 0b00111100) >> 2) * 4;
      System.out.print(String.format("Data bytes number %d", numdatabytes));
      commandfailed = pt & 0b00000001;
      byte hidden = (byte)(pt & 0b00000010);
      if (isbatch != 0 ) {
        numdatabytes = 4;
      }

      startaddress = this.readByte();
      System.out.print(String.format("Start address %d", startaddress));
      while (serial.bytesAvailable() < numdatabytes) {
        ;
      }

      if (hasdata != 0 ) {
          data = new byte[numdatabytes];
          serial.readBytes(data, numdatabytes);
      } else {
          data = null; // False
      }

      byte[] cs_bytes = new byte[2];
      short cs =  (short)(cs_bytes[1] << 8 | cs_bytes[1]); // use network (big) endian

      int ocs = 0;
      ocs += (int)'s';
      ocs += (int)'n';
      ocs += (int)'p';
      ocs += pt;
      ocs += startaddress;
      if (data != null) {
          for (byte b:data) {
              ocs += b;
          }
      }

      if (hidden != 0) {
          startaddress |= UM7Constants.Registers.REG_HIDDEN;
      }
      if (ocs != cs) {
          System.out.print(String.format("bad checksum: %d (should be: %d)", cs, ocs));
          hasdata = 0;   // was for all ValueError
          commandfailed = 0;
          startaddress = 0;
          data = null;
      }
    }
    return new UM7Packet(foundpacket == 1, hasdata == 1, startaddress, data, commandfailed == 1, timeouted == 1);
  }

  byte[] makePack(byte pt, byte sa, byte[] payload) {
      int payloadLength = 0;
      if (payload != null) {
          payloadLength = payload.length;
      }

      byte[] ba = new byte[7 + payloadLength];
      ba[0] = 's';
      ba[1] = 'n';
      ba[2] = 'p';
      ba[3] = pt;
      ba[4] = sa;
      int i = 5;
      if (payload != null) {
          for (byte b : payload) {
              ba[i++] = b;
          }
      }

      short cs = 0;
      for (int j=0; j < i; j++) {
          cs += ba[j];
      }
      ba[i++] = (byte)( (cs >> 8) & 0xFF);
      ba[i] = (byte)( cs & 0xFF);
      return ba;
  }

    UM7Packet readRegistry(final byte start)
            throws OperationTimeoutException, DeviceConnectionException {
        return this.readRegistry(start, (byte) 0, 0.1f);
    }

  UM7Packet readRegistry(final byte start, final byte length, final float timeout)
            throws OperationTimeoutException, DeviceConnectionException {

      long ns_timeout = (long) (timeout * 1.0e9);

      short hidden = (short)(start & UM7Constants.Registers.REG_HIDDEN);
      byte sa = (byte) (start & 0xFF);
      byte pt = 0x0;
      if (length != 0) {
          pt = 0b01000000;
      }
      pt |= (length << 2);
      if (hidden != 0) {
          pt |= 0b00000010;
      }

      byte[] ba = this.makePack(pt, sa, null);
      serial.writeBytes(ba, ba.length);

      t0 = System.nanoTime();
      while (System.nanoTime() - t0 < ns_timeout) { // While elapsed time is less than timeout
          UM7Packet packet = readPacket();
          if (packet.startaddress == start) {
              return packet;
          }
      }
      return new UM7Packet(false, false, start, null, true, true);
  }

    UM7Packet writeRegistry(final byte start) throws OperationTimeoutException, DeviceConnectionException {
        return this.writeRegistry(start, 0, null, 0.1f, false);
    }

    UM7Packet writeRegistry(final byte start, final long length, final byte[] data, final float timeout,
                            final boolean noRead)
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
      serial.writeBytes(ba, ba.length);
      if (noRead) {
          // todo seems we cant flush in jSerialCom
          //serial.getInputStream().flush()
          return new UM7Packet(false, false, start, null, true, false);
      }

      t0 = System.nanoTime();
      while (System.nanoTime() - t0 < ns_timeout) { // While elapsed time is less than timeout
          UM7Packet packet = this.readPacket();
          if (packet.startaddress == start) {
              return packet;
          }
      }
      return new UM7Packet(false, false, start, null, true, true);
  }

  public boolean zeroGyros() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.writeRegistry(UM7Constants.Commands.ZERO_GYROS);
      return (! p.commandfailed);
  }

  public boolean resetEkf() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.writeRegistry(UM7Constants.Commands.RESET_EKF);
      return (! p.commandfailed);
  }

  public boolean resetToFactory() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.writeRegistry(UM7Constants.Commands.RESET_TO_FACTORY);
      return (! p.commandfailed);
  }

  public boolean setMagReference() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.writeRegistry(UM7Constants.Commands.SET_MAG_REFERENCE);
      return (! p.commandfailed);
  }

  public boolean setHomePosition() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.writeRegistry(UM7Constants.Commands.SET_HOME_POSITION);
      return (! p.commandfailed);
  }

  public boolean flashCommit() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.writeRegistry(UM7Constants.Commands.FLASH_COMMIT);
      return (! p.commandfailed);
  }

  public String getFirmwareVersion() throws DeviceConnectionException, OperationTimeoutException {
      UM7Packet p = this.readRegistry(UM7Constants.Commands.GET_FW_REVISION);
      if (p.commandfailed) {
          return "";
      }

      return p.data.toString();
  }

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

      System.out.print(String.format("Current baudrate %032b", cr));
      cr &= 0x0fffffff;
      cr |= new_baud;
      System.out.print(String.format("Current baudrate %032b", cr));

      byte[] ba = new byte[4];
      ba[0] = (byte) ((cr >> 24) & 0xFF);
      ba[1] = (byte) ((cr >> 16) & 0xFF);
      ba[2] = (byte) ((cr >> 8) & 0xFF);
      ba[3] = (byte) (cr & 0xFF);

      p = this.writeRegistry(UM7Constants.Registers.CREG_COM_SETTINGS,
              1, ba, 0.1f, true);
      if (p.commandfailed) {
          return false;
      }
      serial.setBaudRate(baudRate);
      return true;
  }


}
