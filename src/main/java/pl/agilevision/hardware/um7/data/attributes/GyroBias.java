package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class GyroBias extends ConfigurableRateAttribute {
  public static String X = "gyro_bias_x";
  public static String Y = "gyro_bias_y";
  public static String Z = "gyro_bias_z";

  public GyroBias(int registerAddress, String name, int bitOffset) {
    super(registerAddress, name, bitOffset);
  }
}
