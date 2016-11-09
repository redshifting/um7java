package pl.agilevision.hardware.um7;

import org.junit.Test;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.impl.DefaultUM7Client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Set of tests for the default implementation of the {@link UM7Client}
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class DefaultUM7ClientTest extends AbstractDeviceTest{

  private static final java.lang.String TEST_PORT_NAME = "COM3";

  @Test
  public void testConnect() throws DeviceConnectionException {
    info("Connect/disconnect check");

    // Given
    final UM7Client client = new DefaultUM7Client("um7", TEST_PORT_NAME);

    // Then
    assertTrue(client.isConnected());

    client.disconnect();
  }

  @Test
  public void testConnectDisconnect() throws DeviceConnectionException {
    info("Connect/disconnect check");

    // Given
    final UM7Client client = new DefaultUM7Client("um7", TEST_PORT_NAME);

    // Then
    assertTrue(client.isConnected());
    client.disconnect();
    assertFalse(client.isConnected());

    client.connect();
    assertTrue(client.isConnected());

    client.disconnect();
  }
}
