package pl.agilevision.hardware.um7;

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
public class AbstractDeviceTest {


  protected static final String TEST_PORT_NAME = "COM3";
  protected static final String TEST_DEVICE_NAME = "um7";


  protected void info(final String message){
    System.out.println(String.format("[ %-70s ]", message));
  }

  protected void withDevice(Expression expression) throws DeviceConnectionException {
    final UM7Client client = new DefaultUM7Client(TEST_DEVICE_NAME, TEST_PORT_NAME);

    try {
      final UM7 um7 = new DefaultUM7(client, new String[0]);
      expression.execute(um7);
    } finally {
      client.disconnect();
    }

  }


  protected interface Expression{
    void execute(UM7 um7) throws OperationTimeoutException, DeviceConnectionException;
  }
}
