package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class Velocity extends ConfigurableRateAttribute {
  public static String North = "velocity_north";
  public static String East = "velocity_east";
  public static String Up = "velocity_up";
  public static String Time = "velocity_time";

  public Velocity(int registerAddress, String name, int bitOffset) {
    super(registerAddress, name, bitOffset);
  }
}
