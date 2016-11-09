package pl.agilevision.hardware.um7;

import org.junit.Test;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;
import pl.agilevision.hardware.um7.impl.DefaultUM7Client;
import pl.agilevision.hardware.um7.impl.UM7Constants;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Set of tests for the default implementation of the {@link UM7Client}
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class DefaultUM7ClientTest extends AbstractDeviceTest{

  private static final String TEST_PORT_NAME = "COM3";
  private static final String TEST_DEVICE_NAME = "um7";

  @Test
  public void testConnect() throws DeviceConnectionException {
    info("Connect check");

    // Given
    final UM7Client client = new DefaultUM7Client(TEST_DEVICE_NAME, TEST_PORT_NAME);

    // Then
    assertTrue(client.isConnected());

    client.disconnect();
  }

  @Test
  public void testConnectDisconnect() throws DeviceConnectionException {
    info("Connect/disconnect check");

    // Given
    final UM7Client client = new DefaultUM7Client(TEST_DEVICE_NAME, TEST_PORT_NAME);

    // Then
    assertTrue(client.isConnected());
    client.disconnect();
    assertFalse(client.isConnected());

    client.connect();
    assertTrue(client.isConnected());

    client.disconnect();
  }

  @Test
  public void testReadRegistry() throws DeviceConnectionException, OperationTimeoutException {
    info("Read registry check");
    final UM7Client client = new DefaultUM7Client(TEST_DEVICE_NAME, TEST_PORT_NAME);

    try{

      // Given
      final Map<String, Integer> registers = new HashMap<>();
      registers.put("DREG_HEALTH", UM7Constants.Registers.DREG_HEALTH);
      registers.put("DREG_GYRO_RAW_XY", UM7Constants.Registers.DREG_GYRO_RAW_XY);
      registers.put("DREG_GYRO_PROC_X", UM7Constants.Registers.DREG_GYRO_PROC_X);
      registers.put("DREG_ACCEL_PROC_X", UM7Constants.Registers.DREG_ACCEL_PROC_X);
      registers.put("DREG_EULER_PHI_THETA", UM7Constants.Registers.DREG_EULER_PHI_THETA);
      registers.put("DREG_GYRO_BIAS_X", UM7Constants.Registers.DREG_GYRO_BIAS_X);
      registers.put("CREG_COM_SETTINGS", UM7Constants.Registers.CREG_COM_SETTINGS);
      registers.put("CREG_GYRO_TRIM_X", UM7Constants.Registers.CREG_GYRO_TRIM_X);
      registers.put("CREG_MAG_CAL1_1", UM7Constants.Registers.CREG_MAG_CAL1_1);
      registers.put("CREG_MAG_BIAS_X", UM7Constants.Registers.CREG_MAG_BIAS_X);

      registers.put("H_CREG_GYRO_ALIGN1_1", UM7Constants.Registers.H_CREG_GYRO_ALIGN1_1);
      registers.put("H_CREG_ACCEL_ALIGN1_1", UM7Constants.Registers.H_CREG_ACCEL_ALIGN1_1);
      registers.put("H_CREG_MAG_ALIGN1_1", UM7Constants.Registers.H_CREG_MAG_ALIGN1_1);
      registers.put("H_CREG_MAG_REF", UM7Constants.Registers.H_CREG_MAG_REF);

      // Then
      final Base64.Encoder encoder = Base64.getEncoder();

      for(final Map.Entry<String, Integer> entry : registers.entrySet()){
        
        final UM7Packet dataPacket = client.readRegistry(entry.getValue());
        final String message = String.format("Register [%-15s] contains value [%s]", entry.getKey(), encoder.encodeToString(dataPacket.data));
        System.out.println(message);
      }

    } finally {
      client.disconnect();
    }
  }
}
