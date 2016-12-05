package pl.agilevision.hardware.um7;

import pl.agilevision.hardware.um7.data.attributes.ConfigurableRateAttribute;
import org.junit.Test;
import pl.agilevision.hardware.um7.data.binary.UM7BinaryPacket;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;
import pl.agilevision.hardware.um7.impl.DefaultUM7Client;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Set of tests for the default implementation of the {@link UM7Client}
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class DefaultUM7ClientTest extends AbstractDeviceTest{

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
      registers.put("DREG_GYRO_RAW_Z", UM7Constants.Registers.DREG_GYRO_RAW_Z);
      registers.put("DREG_GYRO_TIME", UM7Constants.Registers.DREG_GYRO_TIME);
      registers.put("DREG_ACCEL_RAW_XY", UM7Constants.Registers.DREG_ACCEL_RAW_XY);
      registers.put("DREG_ACCEL_RAW_Z", UM7Constants.Registers.DREG_ACCEL_RAW_Z);
      registers.put("DREG_ACCEL_TIME", UM7Constants.Registers.DREG_ACCEL_TIME);
      registers.put("DREG_MAG_RAW_XY", UM7Constants.Registers.DREG_MAG_RAW_XY);
      registers.put("DREG_MAG_RAW_Z", UM7Constants.Registers.DREG_MAG_RAW_Z);
      registers.put("DREG_MAG_RAW_TIME", UM7Constants.Registers.DREG_MAG_RAW_TIME);
      registers.put("DREG_TEMPERATURE", UM7Constants.Registers.DREG_TEMPERATURE);
      registers.put("DREG_TEMPERATURE_TIME", UM7Constants.Registers.DREG_TEMPERATURE_TIME);
      registers.put("DREG_GYRO_PROC_X", UM7Constants.Registers.DREG_GYRO_PROC_X);
      registers.put("DREG_GYRO_PROC_Y", UM7Constants.Registers.DREG_GYRO_PROC_Y);
      registers.put("DREG_GYRO_PROC_Z", UM7Constants.Registers.DREG_GYRO_PROC_Z);
      registers.put("DREG_GYRO_PROC_TIME", UM7Constants.Registers.DREG_GYRO_PROC_TIME);
      registers.put("DREG_ACCEL_PROC_X", UM7Constants.Registers.DREG_ACCEL_PROC_X);
      registers.put("DREG_ACCEL_PROC_Y", UM7Constants.Registers.DREG_ACCEL_PROC_Y);
      registers.put("DREG_ACCEL_PROC_Z", UM7Constants.Registers.DREG_ACCEL_PROC_Z);
      registers.put("DREG_ACCEL_PROC_TIME", UM7Constants.Registers.DREG_ACCEL_PROC_TIME);
      registers.put("DREG_MAG_PROC_X", UM7Constants.Registers.DREG_MAG_PROC_X);
      registers.put("DREG_MAG_PROC_Y", UM7Constants.Registers.DREG_MAG_PROC_Y);
      registers.put("DREG_MAG_PROC_Z", UM7Constants.Registers.DREG_MAG_PROC_Z);
      registers.put("DREG_MAG_PROC_TIME", UM7Constants.Registers.DREG_MAG_PROC_TIME);
      registers.put("DREG_EULER_PSI", UM7Constants.Registers.DREG_EULER_PSI);
      registers.put("DREG_EULER_PHI_THETA_DOT", UM7Constants.Registers.DREG_EULER_PHI_THETA_DOT);
      registers.put("DREG_EULER_PSI_DOT", UM7Constants.Registers.DREG_EULER_PSI_DOT);
      registers.put("DREG_EULER_TIME", UM7Constants.Registers.DREG_EULER_TIME);
      registers.put("DREG_POSITION_NORTH", UM7Constants.Registers.DREG_POSITION_NORTH);
      registers.put("DREG_POSITION_EAST", UM7Constants.Registers.DREG_POSITION_EAST);
      registers.put("DREG_POSITION_UP", UM7Constants.Registers.DREG_POSITION_UP);
      registers.put("DREG_POSITION_TIME", UM7Constants.Registers.DREG_POSITION_TIME);
      registers.put("DREG_VELOCITY_NORTH", UM7Constants.Registers.DREG_VELOCITY_NORTH);
      registers.put("DREG_VELOCITY_EAST", UM7Constants.Registers.DREG_VELOCITY_EAST);
      registers.put("DREG_VELOCITY_UP", UM7Constants.Registers.DREG_VELOCITY_UP);
      registers.put("DREG_VELOCITY_TIME", UM7Constants.Registers.DREG_VELOCITY_TIME);
      registers.put("DREG_GPS_LATITUDE", UM7Constants.Registers.DREG_GPS_LATITUDE);
      registers.put("DREG_GPS_LONGITUDE", UM7Constants.Registers.DREG_GPS_LONGITUDE);
      registers.put("DREG_GPS_ALTITUDE", UM7Constants.Registers.DREG_GPS_ALTITUDE);
      registers.put("DREG_GPS_COURSE", UM7Constants.Registers.DREG_GPS_COURSE);
      registers.put("DREG_GPS_SPEED", UM7Constants.Registers.DREG_GPS_SPEED);
      registers.put("DREG_GPS_TIME", UM7Constants.Registers.DREG_GPS_TIME);
      registers.put("DREG_GPS_SAT_1_2", UM7Constants.Registers.DREG_GPS_SAT_1_2);
      registers.put("DREG_GPS_SAT_3_4", UM7Constants.Registers.DREG_GPS_SAT_3_4);
      registers.put("DREG_GPS_SAT_5_6", UM7Constants.Registers.DREG_GPS_SAT_5_6);
      registers.put("DREG_GPS_SAT_7_8", UM7Constants.Registers.DREG_GPS_SAT_7_8);
      registers.put("DREG_GPS_SAT_9_10", UM7Constants.Registers.DREG_GPS_SAT_9_10);
      registers.put("DREG_GPS_SAT_11_12", UM7Constants.Registers.DREG_GPS_SAT_11_12);
      registers.put("DREG_GYRO_BIAS_X", UM7Constants.Registers.DREG_GYRO_BIAS_X);
      registers.put("DREG_GYRO_BIAS_Y", UM7Constants.Registers.DREG_GYRO_BIAS_Y);
      registers.put("DREG_GYRO_BIAS_Z", UM7Constants.Registers.DREG_GYRO_BIAS_Z);
      registers.put("DREG_QUAT_AB", UM7Constants.Registers.DREG_QUAT_AB);
      registers.put("DREG_QUAT_CD", UM7Constants.Registers.DREG_QUAT_CD);
      registers.put("DREG_QUAT_TIME", UM7Constants.Registers.DREG_QUAT_TIME);
      registers.put("DREG_EULER_PHI_THETA", UM7Constants.Registers.DREG_EULER_PHI_THETA);

      registers.put("H_CREG_GYRO_ALIGN1_1", UM7Constants.Registers.H_CREG_GYRO_ALIGN1_1);
      registers.put("H_CREG_ACCEL_ALIGN1_1", UM7Constants.Registers.H_CREG_ACCEL_ALIGN1_1);
      registers.put("H_CREG_MAG_ALIGN1_1", UM7Constants.Registers.H_CREG_MAG_ALIGN1_1);
      registers.put("H_CREG_MAG_REF", UM7Constants.Registers.H_CREG_MAG_REF);

      // Then
      final Base64.Encoder encoder = Base64.getEncoder();

      for(final Map.Entry<String, Integer> entry : registers.entrySet()){
        
        final UM7BinaryPacket dataPacket = client.readRegister(entry.getValue());
        final String message = String.format("Register [%-15s] contains value [%s]", entry.getKey(), encoder.encodeToString(dataPacket.data));
        System.out.println(message);
      }

    } finally {
      client.disconnect();
    }
  }
  @Test
  public void testSetDataRate() throws DeviceConnectionException, OperationTimeoutException {
    info("Set DataRate check");
    final UM7Client client = new DefaultUM7Client(TEST_DEVICE_NAME, TEST_PORT_NAME);

    try{
      // Given
      final Map<ConfigurableRateAttribute, Integer> attributes = new HashMap<>();
      attributes.put(UM7Attributes.Accelerator.Raw, 1);
      attributes.put(UM7Attributes.Magnetometer.Raw, 1);

      attributes.put(UM7Attributes.Temperature, 1);
      attributes.put(UM7Attributes.Accelerator.Processed, 1);
      attributes.put(UM7Attributes.Gyro.Processed, 1);
      attributes.put(UM7Attributes.Magnetometer.Processed, 1);

      attributes.put(UM7Attributes.Quat, 1);
      attributes.put(UM7Attributes.Euler, 1);
      attributes.put(UM7Attributes.Position, 1);
      attributes.put(UM7Attributes.Velocity, 1);

      attributes.put(UM7Attributes.Health, UM7Attributes.Frequency.HealthRate.Freq1_HZ);
      attributes.put(UM7Attributes.GyroBias, 1);
      attributes.put(UM7Attributes.NMEA.Health, UM7Attributes.Frequency.NMEA.Freq1_HZ);
      attributes.put(UM7Attributes.NMEA.Pose, UM7Attributes.Frequency.NMEA.Freq1_HZ);
      attributes.put(UM7Attributes.NMEA.Attitude, UM7Attributes.Frequency.NMEA.Freq1_HZ);
      attributes.put(UM7Attributes.NMEA.Sensor, UM7Attributes.Frequency.NMEA.Freq1_HZ);
      attributes.put(UM7Attributes.NMEA.Rates, UM7Attributes.Frequency.NMEA.Freq1_HZ);
      attributes.put(UM7Attributes.NMEA.GpsPose, UM7Attributes.Frequency.NMEA.Freq1_HZ);
      attributes.put(UM7Attributes.NMEA.Quat, UM7Attributes.Frequency.NMEA.Freq1_HZ);

      attributes.put(UM7Attributes.AllRaw, 0);
      attributes.put(UM7Attributes.AllProc, 0);
      attributes.put(UM7Attributes.Pose, 0);

      // Then
      final Base64.Encoder encoder = Base64.getEncoder();

      for(final Map.Entry<ConfigurableRateAttribute, Integer> entry : attributes.entrySet()){
        boolean ok = client.setDataRate(entry.getKey(), entry.getValue());

        final String message = String.format("Set rate for [%s] is [%s]", entry.getKey().getRateConfName(),  ok);
        System.out.println(message);
      }

    } finally {
      client.disconnect();
    }
  }
}
