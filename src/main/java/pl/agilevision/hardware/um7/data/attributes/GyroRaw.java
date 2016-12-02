package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class GyroRaw extends BaseAttribute {
  public static String X = "gyro_raw_x";
  public static String Y = "gyro_raw_y";
  public static String Z = "gyro_raw_z";
  public static String Time = "gyro_raw_time";

  public GyroRaw(int registerAddress, String name, int bitOffset) {
    super(registerAddress, name, bitOffset);
  }
}
