package pl.agilevision.hardware.um7.impl;

import com.fazecast.jSerialComm.SerialPort;
import pl.agilevision.hardware.um7.UM7;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by volodymyr on 07.11.16.
 */
public class DefaultUM7 implements UM7 {

  final int GET_FW_REVISION       = 0xAA; //# (170)
  final int FLASH_COMMIT          = 0xAB; //# (171)
  final int RESET_TO_FACTORY      = 0xAC; //# (172)
  final int ZERO_GYROS            = 0xAD; //# (173)
  final int SET_HOME_POSITION     = 0xAE; //# (174)
  final int SET_MAG_REFERENCE     = 0xB0; //# (176)
  final int RESET_EKF             = 0xB3; //# (179)

  final int DREG_HEALTH           = 0x55;
  final int DREG_GYRO_RAW_XY      = 0x56;
  final int DREG_GYRO_PROC_X      = 0x61;
  final int DREG_ACCEL_PROC_X     = 0x65;
  final int DREG_EULER_PHI_THETA  = 0x70;
  final int DREG_GYRO_BIAS_X      = 0x89;

  final int CREG_COM_SETTINGS     = 0x00;
  final int CREG_GYRO_TRIM_X      = 0x0C;
  final int CREG_MAG_CAL1_1       = 0x0F;
  final int CREG_MAG_BIAS_X       = 0x18;

  final int REG_HIDDEN            = 0xF000;
  final int H_CREG_GYRO_ALIGN1_1  = REG_HIDDEN | 0x31;
  final int H_CREG_ACCEL_ALIGN1_1 = REG_HIDDEN | 0x52;
  final int H_CREG_MAG_ALIGN1_1   = REG_HIDDEN | 0x73;
  final int H_CREG_MAG_REF        = REG_HIDDEN | 0x7C;

  final int HEALTH_GPS   = 0x1;
  final int HEALTH_MAG   = 0x2;
  final int HEALTH_GYRO  = 0x4;
  final int HEALTH_ACCEL = 0x8;
  final int HEALTH_ACC_N = 0x10;
  final int HEALTH_MG_N  = 0x20;
  final int HEALTH_RES6  = 0x40;
  final int HEALTH_RES7  = 0x80;
  final int HEALTH_OVF   = 0x100;

  private static final Map<Integer, Integer> baud_rates;
  static
  {
    baud_rates = new HashMap<>();
    baud_rates.put(9600, 0);
    baud_rates.put(14400, 1);
    baud_rates.put(19200, 2);
    baud_rates.put(38400, 3);
    baud_rates.put(57600, 4);
    baud_rates.put(115200, 5);
    baud_rates.put(128000, 6);
    baud_rates.put(153600, 7);
    baud_rates.put(230400, 8);
    baud_rates.put(256000, 9);
    baud_rates.put(460800, 10);
    baud_rates.put(921600, 11);
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
  public UM7Packet catchsample() {
    UM7Packet packet = this.readpacket();
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

  public UM7Packet readpacket() {
    return this.readpacket(0.1f);
  }

  /** Scans for and partially parses new data packets. Binary data can then be sent to data parser
   :return: Parsed packet info */
  public UM7Packet readpacket(float timeout) {
    long ns_timeout = (long) (timeout * 1.0e9);
    int foundpacket = 0;
    t0 = System.nanoTime();

    while (System.nanoTime() - t0 < ns_timeout) {  //While elapsed time is less than timeout
      try {
        if (serial.getInputStream().available() >= 3) {
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

      } catch (IOException e) {
        System.out.print("Ignore getInputStream exception: ");
        e.printStackTrace();
      } catch (InterruptedException e) {
        System.out.print("Program interrupted");
      }
    }

    int hasdata = 0;
    int commandfailed = 0;
    int startaddress = 0;
    int data = 0;
    int timeouted = 1;

    if (foundpacket == 0) {
      hasdata = 0;
      commandfailed = 0;
      startaddress = 0;
      data = 0;
      timeouted = 1;
    } else {
      timeouted = 0;

      //todo else not implemented
    }
    return new UM7Packet(foundpacket == 1, hasdata == 1, startaddress, data, commandfailed == 1, timeouted == 1);
  }

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
