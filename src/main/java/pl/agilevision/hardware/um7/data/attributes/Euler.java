package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class Euler extends ConfigurableRateAttribute {

  public static String Roll = "roll";  // Phi
  public static String Pitch = "pitch";  // Theta
  public static String Yaw = "yaw";  // Psi
  public static String RollRate = "roll_rate";
  public static String PitchRate = "pitch_rate";
  public static String YawRate = "yaw_rate";
  public static String Time = "euler_time";

  public Euler(int registerAddress, String name, int bitOffset) {
    super(registerAddress, name, bitOffset);
  }
}
