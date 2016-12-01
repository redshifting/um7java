package pl.agilevision.hardware.um7.data;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class MagnetometerRaw extends GyroRaw {
  public static String X = "mag_raw_x";
  public static String Y = "mag_raw_y";
  public static String Z = "mag_raw_z";
  public static String Time = "mag_raw_time";

  public MagnetometerRaw(int registerAddress, String name, int bitOffset) {
    super(registerAddress, name, bitOffset);
  }
}
