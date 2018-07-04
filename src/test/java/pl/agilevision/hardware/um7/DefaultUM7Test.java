package pl.agilevision.hardware.um7;

import org.junit.Test;
import pl.agilevision.hardware.um7.data.UM7DataSample;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by volodymyr on 09.11.16.
 */
public class DefaultUM7Test extends AbstractDeviceTest {

  @Test
  public void testReadState() throws DeviceConnectionException, OperationTimeoutException {
    info("Data sample test");
    withDevice(um7 -> {

      try {
        for (int i = 0; i < 5; i++) {
          final UM7DataSample state = um7.readState();

          if (state != null && state.getRawData() != null) {

            for (final Map.Entry<String, Object> value : state.getRawData().entrySet()) {
              System.out.println(
                  String.format("%-10s: %s", value.getKey(), String.valueOf(value.getValue())));
            }
          } else {
            info("Can't get state");
          }
        }
      } catch (Exception ex) {
        ex.printStackTrace();
        fail(String.format("Error when try to read state=%s", ex));
      }
    });
  }

  @Test
  public void testGetFirmwareVersion() throws DeviceConnectionException, OperationTimeoutException {
    info("Firmware version test");
    withDevice(um7 -> {

      final String version = um7.getFirmwareVersion();
      assertNotNull(version);
      System.out.println("Version: " + version);

    });
  }

  @Test
  public void testZeroGyros() throws DeviceConnectionException, OperationTimeoutException {
    info("zero gyros test");
    withDevice(um7 -> {

      assertTrue(um7.zeroGyros());
    });
  }


  @Test
  public void testSetMagReference() throws DeviceConnectionException, OperationTimeoutException {
    info("mag reference test");
    withDevice(um7 -> {

      assertTrue(um7.setMagReference());

    });
  }


  @Test
  public void testHomePosition() throws DeviceConnectionException, OperationTimeoutException {
    info("home position test");
    withDevice(um7 -> {

      assertTrue(um7.setHomePosition());

    });
  }

  @Test
  public void testResetEkf() throws DeviceConnectionException, OperationTimeoutException {
    info("reset ekf test");
    withDevice(um7 -> {

      assertTrue(um7.resetEkf());
    });
  }
}
