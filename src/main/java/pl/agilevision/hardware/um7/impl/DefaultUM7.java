package pl.agilevision.hardware.um7.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agilevision.hardware.um7.UM7Attributes;
import pl.agilevision.hardware.um7.UM7;
import pl.agilevision.hardware.um7.UM7Client;
import pl.agilevision.hardware.um7.UM7Constants;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.data.UM7DataSample;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class DefaultUM7 implements UM7 {

  private UM7Client um7Client;

  private UM7DataSample state;

  private static double DEGREES_DIVIDER = 91.02222; // divider for degrees
  private static double RATE_DIVIDER = 16.0;     // divider for rate
  private static final Logger LOG = LoggerFactory.getLogger(DefaultUM7Client.class);

  public UM7DataSample getState() {
    return state;
  }

  @Override
  public boolean catchAllSamples(final String [] wantedState, float timeout) throws DeviceConnectionException, IOException {
    Map<String, Object> m = new HashMap<>();
    UM7DataSample sample = new UM7DataSample(m);
    long t0 = System.nanoTime();
    long ns_timeout = (long) (timeout * 1.0e9);
    boolean all_found = false;
    while (System.nanoTime() - t0 < ns_timeout) {
      UM7Packet packet;

      packet = this.um7Client.readPacket();

      if (packet.foundpacket) {
        UM7DataSample newsample = null;
        newsample = this.parseDataBatch(packet.data, packet.startaddress);
        if (newsample != null) {
          sample.update(newsample);
        }
      }
      boolean all = true;
      for (String k: wantedState) {
        if (sample.getRawData().get(k) == null) {
          all = false;
        }
      }
      if (all) {
        all_found = true;
        break;
      }
    }
    state.update(sample);
    return all_found;
  }

  ;

  public DefaultUM7(UM7Client um7Client, final String [] stateVars) {
    this.um7Client = um7Client;
    Map<String, Object> m = new HashMap<>();
    for (String i : stateVars) {
      m.put(i, 0);
    }

    state = new UM7DataSample(m);
  }

  public boolean zeroGyros() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.um7Client.clearRegister(UM7Constants.Commands.ZERO_GYROS);
    return (! p.commandfailed);
  }

  public boolean resetEkf() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.um7Client.clearRegister(UM7Constants.Commands.RESET_EKF);
    return (! p.commandfailed);
  }

  public boolean resetToFactory() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.um7Client.clearRegister(UM7Constants.Commands.RESET_TO_FACTORY);
    return (! p.commandfailed);
  }

  public boolean setMagReference() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.um7Client.clearRegister(UM7Constants.Commands.SET_MAG_REFERENCE);
    return (! p.commandfailed);
  }

  public boolean setHomePosition() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.um7Client.clearRegister(UM7Constants.Commands.SET_HOME_POSITION);
    return (! p.commandfailed);
  }

  public boolean flashCommit() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.um7Client.clearRegister(UM7Constants.Commands.FLASH_COMMIT);
    return (! p.commandfailed);
  }

  public String getFirmwareVersion() throws DeviceConnectionException, OperationTimeoutException {
    UM7Packet p = this.um7Client.readRegister(UM7Constants.Commands.GET_FW_REVISION);
    if (p.commandfailed) {
      return "";
    }
    String s = "";
    for (byte ch: p.data) {
      s += (char)ch;
    }
    return s;
  }

  @Override
  public UM7DataSample readState() throws DeviceConnectionException, OperationTimeoutException {

    UM7Packet packet = this.um7Client.readPacket();
    if (!packet.foundpacket) {
      return null;
    }

    try {
      UM7DataSample sample = this.parseDataBatch(packet.data, packet.startaddress);
      if (sample != null) {
        this.state.update(sample);
      }
      return sample;
    } catch (final Exception e){
      throw new DeviceConnectionException("Failed to catch sample", e);
    }


  }



  private UM7DataSample parseDataBatch(byte[] data, int startAddress) throws IOException {


    final Map<String, Object> output = new HashMap<>();
    final DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));

    if (startAddress == UM7Constants.Registers.DREG_HEALTH) {
      // (0x55,  85) Health register
      output.put(UM7Attributes.Health, is.readInt());
    } else if (startAddress == UM7Constants.Registers.DREG_GYRO_PROC_X) {
      // (0x61,  97) Processed Data: gyro (deg/s) xyzt, accel (m/s²) xyzt, mag xyzt

      output.put(UM7Attributes.Gyro.Processed.X, is.readFloat()); //f
      output.put(UM7Attributes.Gyro.Processed.Y, is.readFloat());
      output.put(UM7Attributes.Gyro.Processed.Z, is.readFloat());
      output.put(UM7Attributes.Gyro.Processed.Time, is.readFloat());

      output.put(UM7Attributes.Accelerator.Processed.X, is.readFloat());
      output.put(UM7Attributes.Accelerator.Processed.Y, is.readFloat());
      output.put(UM7Attributes.Accelerator.Processed.Z, is.readFloat());
      output.put(UM7Attributes.Accelerator.Processed.Time, is.readFloat());

      output.put(UM7Attributes.Magnetometer.Processed.X, is.readFloat());
      output.put(UM7Attributes.Magnetometer.Processed.Y, is.readFloat());
      output.put(UM7Attributes.Magnetometer.Processed.Z, is.readFloat());
      output.put(UM7Attributes.Magnetometer.Processed.Time, is.readFloat());

    } else if (startAddress == UM7Constants.Registers.DREG_GYRO_RAW_XY) {
      // (0x56,  86) Raw Rate Gyro Data: gyro xyz#t, accel xyz#t, mag xyz#t, temp ct

      output.put(UM7Attributes.Gyro.Raw.X, is.readShort() / DEGREES_DIVIDER); //h
      output.put(UM7Attributes.Gyro.Raw.Y, is.readShort() / DEGREES_DIVIDER);
      output.put(UM7Attributes.Gyro.Raw.Z, is.readShort() / DEGREES_DIVIDER);
      is.skipBytes(2); //2x
      output.put(UM7Attributes.Gyro.Raw.Time, is.readFloat()); //f


      output.put(UM7Attributes.Accelerator.Raw.X, is.readShort() / DEGREES_DIVIDER); //h
      output.put(UM7Attributes.Accelerator.Raw.Y, is.readShort() / DEGREES_DIVIDER);
      output.put(UM7Attributes.Accelerator.Raw.Z, is.readShort() / DEGREES_DIVIDER);
      is.skipBytes(2); //2x
      output.put(UM7Attributes.Accelerator.Raw.Time, is.readFloat()); //f

      output.put(UM7Attributes.Magnetometer.Raw.X, is.readShort()); //h
      output.put(UM7Attributes.Magnetometer.Raw.Y, is.readShort());
      output.put(UM7Attributes.Magnetometer.Raw.Z, is.readShort());
      is.skipBytes(2); //2x
      output.put(UM7Attributes.Magnetometer.Raw.Time, is.readShort());
      output.put(UM7Attributes.Temperature, is.readFloat()); //f
      is.readFloat(); //f

    } else if (startAddress == UM7Constants.Registers.DREG_ACCEL_PROC_X) {
      // (0x65, 101) Processed Accel Data
    } else if (startAddress == UM7Constants.Registers.DREG_EULER_PHI_THETA) {
      // (0x70, 112) Processed Euler Data:
      output.put(UM7Attributes.Roll, is.readShort() / DEGREES_DIVIDER); //h
      output.put(UM7Attributes.Pitch, is.readShort() / DEGREES_DIVIDER); //h
      output.put(UM7Attributes.Yaw, is.readShort() / DEGREES_DIVIDER); //h
      is.skipBytes(2); //2x
      output.put(UM7Attributes.RollRate, is.readShort() / RATE_DIVIDER); //h
      output.put(UM7Attributes.PitchRate, is.readShort() / RATE_DIVIDER); //h
      output.put(UM7Attributes.YawRate, is.readShort() / RATE_DIVIDER); //h
      is.skipBytes(2); //2x
      output.put(UM7Attributes.EulerTime, is.readFloat()); //f

    } else if (startAddress == UM7Constants.Registers.DREG_GYRO_BIAS_X) {
      //(0x89, 137) gyro bias xyz
      // values=struct.unpack('!fff', data)
    } else if (startAddress == UM7Constants.Registers.CREG_GYRO_TRIM_X) {
      // (0x0C,  12)
      // values=struct.unpack('!fff', data)
    } else {
      LOG.debug(String.format("batch pack start=0x%4x len=%4d", startAddress, data.length));
      return null;
    }
    return new UM7DataSample(output);
  }

}
