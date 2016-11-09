package pl.agilevision.hardware.um7;

import org.junit.Test;
import pl.agilevision.hardware.um7.data.UMDataSample;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;
import pl.agilevision.hardware.um7.impl.DefaultUM7;
import pl.agilevision.hardware.um7.impl.DefaultUM7Client;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by volodymyr on 09.11.16.
 */
public class DefaultUM7Test extends AbstractDeviceTest {

  @Test
  public void testGetDataSample() throws DeviceConnectionException, OperationTimeoutException {

    final UM7Client client = new DefaultUM7Client(TEST_DEVICE_NAME, TEST_PORT_NAME);

    try {
      // Given
      final UM7 um7 = new DefaultUM7(client, new String[0]);

      // When
      final UMDataSample state = um7.readState();

      // Then
      assertNotNull(state);

      for(final Map.Entry<String, Object> value : state.getRawData().entrySet()){
        System.out.println(String.format("%-10s: %s", value.getKey(), String.valueOf(value.getValue())));
      }


    } finally {
      client.disconnect();
    }
  }
}
