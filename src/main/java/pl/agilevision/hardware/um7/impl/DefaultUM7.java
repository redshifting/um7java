package pl.agilevision.hardware.um7.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agilevision.hardware.um7.UM7Attributes;
import pl.agilevision.hardware.um7.UM7;
import pl.agilevision.hardware.um7.UM7Client;
import pl.agilevision.hardware.um7.UM7Constants;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.data.binary.UM7BinaryPacket;
import pl.agilevision.hardware.um7.data.UM7DataSample;
import pl.agilevision.hardware.um7.data.parser.BinaryPacketParser;
import pl.agilevision.hardware.um7.data.parser.NMEAPacketParser;
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
      UM7BinaryPacket packet;

      packet = this.um7Client.readPacket();

      if (packet.foundpacket) {
        UM7DataSample newsample = null;
        newsample = this.parseDataBatch(packet.data, packet.startaddress, packet.isNmeaPacket);
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

  public DefaultUM7(UM7Client um7Client, final String [] stateVars) {
    this.um7Client = um7Client;
    Map<String, Object> m = new HashMap<>();
    for (String i : stateVars) {
      m.put(i, 0);
    }

    state = new UM7DataSample(m);
  }

  public boolean zeroGyros() throws DeviceConnectionException, OperationTimeoutException {
    UM7BinaryPacket p = this.um7Client.clearRegister(UM7Constants.Commands.ZERO_GYROS);
    return (! p.commandfailed);
  }

  public boolean resetEkf() throws DeviceConnectionException, OperationTimeoutException {
    UM7BinaryPacket p = this.um7Client.clearRegister(UM7Constants.Commands.RESET_EKF);
    return (! p.commandfailed);
  }

  public boolean resetToFactory() throws DeviceConnectionException, OperationTimeoutException {
    UM7BinaryPacket p = this.um7Client.clearRegister(UM7Constants.Commands.RESET_TO_FACTORY);
    return (! p.commandfailed);
  }

  public boolean setMagReference() throws DeviceConnectionException, OperationTimeoutException {
    UM7BinaryPacket p = this.um7Client.clearRegister(UM7Constants.Commands.SET_MAG_REFERENCE);
    return (! p.commandfailed);
  }

  public boolean setHomePosition() throws DeviceConnectionException, OperationTimeoutException {
    UM7BinaryPacket p = this.um7Client.clearRegister(UM7Constants.Commands.SET_HOME_POSITION);
    return (! p.commandfailed);
  }

  public boolean flashCommit() throws DeviceConnectionException, OperationTimeoutException {
    UM7BinaryPacket p = this.um7Client.clearRegister(UM7Constants.Commands.FLASH_COMMIT);
    return (! p.commandfailed);
  }

  public String getFirmwareVersion() throws DeviceConnectionException, OperationTimeoutException {
    UM7BinaryPacket p = this.um7Client.readRegister(UM7Constants.Commands.GET_FW_REVISION);
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

    UM7BinaryPacket packet = this.um7Client.readPacket();
    if (! packet.foundpacket || packet.commandfailed) {
      return null;
    }

    try {
      UM7DataSample sample =
          this.parseDataBatch(packet.data, packet.startaddress, packet.isNmeaPacket);
      if (sample != null && sample.getRawData() != null) {
        this.state.update(sample);
      }

      return sample;
    } catch (final Exception e) {
      throw new DeviceConnectionException("Failed to catch sample", e);
    }
  }

  private UM7DataSample parseDataBatch(byte[] data, int startAddress, boolean isNmeaPacket)
      throws IOException {
    UM7Packet u = ! isNmeaPacket ? BinaryPacketParser.getParser()
        .parse(data, um7Client.getCallbacks(), startAddress)
        : NMEAPacketParser.getParser().parse(data, um7Client.getCallbacks());
    return new UM7DataSample(u.getAttributes());
  }

}
