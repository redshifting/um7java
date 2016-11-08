package pl.agilevision.hardware.um7.impl;

import com.fazecast.jSerialComm.SerialPort;
import pl.agilevision.hardware.um7.UM7;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class DefaultUM7 implements UM7 {


  private static final Map<Integer, Integer> baudRates;

  static {
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

  private Map<String, Object> state;
  private String dev_name;
  private String dev_port;
  private long t0;
  private SerialPort serial;


  private byte readByte() {
    byte bytes[] = new byte[1];
    serial.readBytes(bytes, 1);
    return bytes[0];
  }

  /**
   * @see DefaultUM7#DefaultUM7(String, String, String[], int)
   */
  public DefaultUM7(String name, String port, String[] stateVars) {
    this(name, port, stateVars, 115200);
  }

  /**
   * Create new UM7 serial object.
   * Initializes port, name, OS timer, and sensor state (dict)
   *
   * @param name:      name of object (str)
   * @param port:      Virtual COM port to which the IMU is connected (str)
   * @param stateVars: State Variables
   * @param baudRate:  baud rate
   */
  public DefaultUM7(String name, String port, String[] stateVars, int baudRate) {
    dev_name = name;
    dev_port = port;
    t0 = System.nanoTime();  // about on which platforms it safe to use http://stackoverflow.com/a/4588605/3479125
    state = new HashMap<>();
    for (String i : stateVars) {
      state.put(i, null);
    }

    serial = SerialPort.getCommPort(dev_port);

    boolean openedSuccessfully = serial.openPort();
    if (!openedSuccessfully) {
      System.out.println("Could not connect to UM7 " + name);
      //System.err.println("\nCan't Opening " + serial.getSystemPortName() + ": " + serial.getDescriptivePortName());
      return;
    }

    serial.setBaudRate(baudRate);
    serial.setNumDataBits(8);
    serial.setNumStopBits(1);
    serial.setParity(SerialPort.NO_PARITY);
    //default is nonblocking mode, uncomment next for semiblocking:
    //serial.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);

  }

  protected void finalize() throws Throwable {
    if (serial != null) {
      serial.closePort();
    }
    super.finalize();
  }

  public String toString() {
    return dev_name;
  }

  /**
   * Function that catches and parses incoming data, and then updates the sensor's state to include new data. Old
   * data in state is overwritten.
   * <p>
   * :return: Newly obtained data, and updates internal sensor state
   */
  public Map<String, Object> catchSample() throws DeviceConnectionException, IOException {
    UM7Packet packet = this.readPacket();
    if (!packet.foundpacket) {
      return null;
    }
    Map<String, Object> sample = this.parseDataBatch(packet.data, packet.startaddress);
    if (sample != null) {
      this.state.putAll(sample);
    }
    return sample;
  }

  private Map parseDataBatch(byte[] data, byte startAddress) throws IOException {
    String health = "health";
    String gpx = "gyro_proc_x";
    String gpy = "gyro_proc_y";
    String gpz = "gyro_proc_z";
    String gpt = "gyro_proc_time";
    String grx = "gyro_raw_x";
    String gry = "gyro_raw_y";
    String grz = "gyro_raw_z";
    String grt = "gyro_raw_time";
    String apx = "accel_proc_x";
    String apy = "accel_proc_y";
    String apz = "accel_proc_z";
    String apt = "accel_proc_time";
    String arx = "accel_raw_x";
    String ary = "accel_raw_y";
    String arz = "accel_raw_z";
    String art = "accel_raw_time";
    String mpx = "mag_proc_x";
    String mpy = "mag_proc_y";
    String mpz = "mag_proc_z";
    String mpt = "mag_proc_time";
    String mrx = "mag_raw_x";
    String mry = "mag_raw_y";
    String mrz = "mag_raw_z";
    String mrt = "mag_raw_time";
    String r = "roll";
    String p = "pitch";
    String y = "yaw";
    String rr = "roll_rate";
    String pr = "pitch_rate";
    String yr = "yaw_rate";
    String et = "euler_time";
    String temp = "temp";

    double DD = 91.02222; // divider for degrees
    double DR = 16.0;     // divider for rate

    Map<String, Object> output = new HashMap<>();
    DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));

    if (startAddress == UM7Constants.Registers.DREG_HEALTH) {
      // (0x55,  85) Health register
      output.put(health, is.readInt());
    } else if (startAddress == UM7Constants.Registers.DREG_GYRO_PROC_X) {
      // (0x61,  97) Processed Data: gyro (deg/s) xyzt, accel (m/s²) xyzt, mag xyzt
      output.put(gpx, is.readFloat()); //f
      output.put(gpy, is.readFloat());
      output.put(gpz, is.readFloat());
      output.put(gpt, is.readFloat());

      output.put(apx, is.readFloat());
      output.put(apy, is.readFloat());
      output.put(apz, is.readFloat());
      output.put(apt, is.readFloat());

      output.put(mpx, is.readFloat());
      output.put(mpy, is.readFloat());
      output.put(mpz, is.readFloat());
      output.put(mpt, is.readFloat());

    } else if (startAddress == UM7Constants.Registers.DREG_GYRO_RAW_XY) {
      // (0x56,  86) Raw Rate Gyro Data: gyro xyz#t, accel xyz#t, mag xyz#t, temp ct
      output.put(grx, is.readShort() / DD); //h
      output.put(gry, is.readShort() / DD);
      output.put(grz, is.readShort() / DD);
      is.skipBytes(2); //2x
      output.put(grt, is.readFloat()); //f


      output.put(arx, is.readShort() / DD); //h
      output.put(ary, is.readShort() / DD);
      output.put(arz, is.readShort() / DD);
      is.skipBytes(2); //2x
      output.put(art, is.readFloat()); //f

      output.put(mrx, is.readShort()); //h
      output.put(mry, is.readShort());
      output.put(mrz, is.readShort());
      is.skipBytes(2); //2x
      output.put(mrt, is.readFloat()); //f
      output.put(temp, is.readFloat()); //f
      is.readFloat(); //f

    } else if (startAddress == UM7Constants.Registers.DREG_ACCEL_PROC_X) {
      // (0x65, 101) Processed Accel Data
    } else if (startAddress == UM7Constants.Registers.DREG_EULER_PHI_THETA) {
      // (0x70, 112) Processed Euler Data:
      output.put(r, is.readShort() / DD); //h
      output.put(p, is.readShort() / DD); //h
      output.put(y, is.readShort() / DD); //h
      is.skipBytes(2); //2x
      output.put(rr, is.readShort() / DR); //h
      output.put(pr, is.readShort() / DR); //h
      output.put(yr, is.readShort() / DR); //h
      is.skipBytes(2); //2x
      output.put(et, is.readFloat()); //f

    } else if (startAddress == UM7Constants.Registers.DREG_GYRO_BIAS_X) {
      //(0x89, 137) gyro bias xyz
      // values=struct.unpack('!fff', data)
    } else if (startAddress == UM7Constants.Registers.CREG_GYRO_TRIM_X) {
      // (0x0C,  12)
      // values=struct.unpack('!fff', data)
    } else {
      System.out.println(String.format("batch pack start=0x%4x len=%4d", startAddress, data.length));
      return null;
    }
    return output;
  }

  private UM7Packet readPacket() throws DeviceConnectionException {
    return this.readPacket(0.1f);
  }

  /**
   * Scans for and partially parses new data packets. Binary data can then be sent to data parser
   * :return: Parsed packet info
   */
  private UM7Packet readPacket(float timeout) throws DeviceConnectionException {
    long ns_timeout = (long) (timeout * 1.0e9);
    int foundPacket = 0;
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
                foundPacket = 1;
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

    int hasData;
    int commandFailed;
    byte startAddress;
    byte[] data;
    int timeOuted;

    if (foundPacket == 0) {
      hasData = 0;
      commandFailed = 0;
      startAddress = 0;
      data = null; //0
      timeOuted = 1;
    } else {
      timeOuted = 0;

      byte pt = this.readByte();
      System.out.print(String.format("PT %d", pt));
      hasData = pt & 0b10000000;
      byte isBatch = (byte) (pt & 0b01000000);
      int numDataBytes = ((pt & 0b00111100) >> 2) * 4;
      System.out.print(String.format("Data bytes number %d", numDataBytes));
      commandFailed = pt & 0b00000001;
      byte hidden = (byte) (pt & 0b00000010);
      if (isBatch != 0) {
        numDataBytes = 4;
      }

      startAddress = this.readByte();
      System.out.print(String.format("Start address %d", startAddress));
      while (serial.bytesAvailable() < numDataBytes) {}

      if (hasData != 0) {
        data = new byte[numDataBytes];
        serial.readBytes(data, numDataBytes);
      } else {
        data = null; // False
      }

      byte[] cs_bytes;
      cs_bytes = new byte[2];
      short cs = (short) (cs_bytes[1] << 8 | cs_bytes[1]); // use network (big) endian

      int ocs = 0;
      ocs += (int) 's';
      ocs += (int) 'n';
      ocs += (int) 'p';
      ocs += pt;
      ocs += startAddress;
      if (data != null) {
        for (byte b : data) {
          ocs += b;
        }
      }

      if (hidden != 0) {
        startAddress |= UM7Constants.Registers.REG_HIDDEN;
      }
      if (ocs != cs) {
        System.out.print(String.format("bad checksum: %d (should be: %d)", cs, ocs));
        hasData = 0;   // was for all ValueError
        commandFailed = 0;
        startAddress = 0;
        data = null;
      }
    }
    return new UM7Packet(foundPacket == 1, hasData == 1, startAddress, data, commandFailed == 1, timeOuted == 1);
  }

  private byte[] makePack(byte pt, byte sa, byte[] payload) {
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
    for (int j = 0; j < i; j++) {
      cs += ba[j];
    }
    ba[i++] = (byte) ((cs >> 8) & 0xFF);
    ba[i] = (byte) (cs & 0xFF);
    return ba;
  }

  private UM7Packet readRegistry(final byte start)
    throws OperationTimeoutException, DeviceConnectionException {
    return this.readRegistry(start, (byte) 0, 0.1f);
  }

  private UM7Packet readRegistry(final byte start, final byte length, final float timeout)
    throws OperationTimeoutException, DeviceConnectionException {

    long ns_timeout = (long) (timeout * 1.0e9);

    short hidden = (short) (start & UM7Constants.Registers.REG_HIDDEN);
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

  private UM7Packet writeRegistry(final byte start) throws OperationTimeoutException, DeviceConnectionException {
    return this.writeRegistry(start, 0, null, 0.1f, false);
  }

  private UM7Packet writeRegistry(final byte start, final long length, final byte[] data, final float timeout,
                                  final boolean noRead)
    throws OperationTimeoutException, DeviceConnectionException {

    long ns_timeout = (long) (timeout * 1.0e9);
    short hidden = (short) (start & UM7Constants.Registers.REG_HIDDEN);
    byte sa = (byte) (start & 0xFF);
    byte pt = (byte) (0x0);
    if (data != null) {
      pt = (byte) 0b11000000;
      pt |= (length << 2);
    }
    if (hidden != 0) {
      pt |= 0b00000010;
    }
    byte[] ba = this.makePack(pt, sa, data);
    serial.writeBytes(ba, ba.length);
    if (noRead) {
      // todo seems we cant flush in jSerialCom, not sure it is critical her
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
    return (!p.commandfailed);
  }

  public boolean resetEkf() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.writeRegistry(UM7Constants.Commands.RESET_EKF);
    return (!p.commandfailed);
  }

  public boolean resetToFactory() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.writeRegistry(UM7Constants.Commands.RESET_TO_FACTORY);
    return (!p.commandfailed);
  }

  public boolean setMagReference() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.writeRegistry(UM7Constants.Commands.SET_MAG_REFERENCE);
    return (!p.commandfailed);
  }

  public boolean setHomePosition() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.writeRegistry(UM7Constants.Commands.SET_HOME_POSITION);
    return (!p.commandfailed);
  }

  public boolean flashCommit() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.writeRegistry(UM7Constants.Commands.FLASH_COMMIT);
    return (!p.commandfailed);
  }

  public String getFirmwareVersion() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.readRegistry(UM7Constants.Commands.GET_FW_REVISION);
    if (p.commandfailed) {
      return "";
    }

    return Arrays.toString(p.data);
  }

  public boolean setBaudRate(int baudRate) throws DeviceConnectionException, OperationTimeoutException {
    int new_baud = baudRates.get(baudRate) << 28;
    UM7Packet p = this.readRegistry(UM7Constants.Registers.CREG_COM_SETTINGS);
    if (p.commandfailed) {
      return false;
    }
    // big endian unpack without java.nio
    int cr = p.data[0];
    cr = cr << 8 | p.data[1];
    cr = cr << 8 | p.data[2];
    cr = cr << 8 | p.data[3];

    System.out.print(String.format("Current baud rate %s",
                      String.format("%16s", Integer.toBinaryString(cr)).replace(' ', '0') ));
    cr &= 0x0fffffff;
    cr |= new_baud;
    System.out.print(String.format("Current baud rate %s",
                      String.format("%16s", Integer.toBinaryString(cr)).replace(' ', '0') ));

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




