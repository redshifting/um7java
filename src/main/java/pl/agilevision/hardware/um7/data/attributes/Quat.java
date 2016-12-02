package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class Quat extends ConfigurableRateAttribute {
  public static String A = "quat_a";
  public static String B = "quat_b";
  public static String C = "quat_c";
  public static String D = "quat_d";
  public static String Time = "quat_time";

  public Quat(int registerAddress, String name, int bitOffset) {
    super(registerAddress, name, bitOffset);
  }
}
