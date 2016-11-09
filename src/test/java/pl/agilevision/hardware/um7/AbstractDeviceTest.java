package pl.agilevision.hardware.um7;

/**
 * Created by volodymyr on 09.11.16.
 */
public class AbstractDeviceTest {


  protected static final String TEST_PORT_NAME = "COM3";
  protected static final String TEST_DEVICE_NAME = "um7";


  protected void info(final String message){
    System.out.println(String.format("[ %-70s ]", message));
  }
}
