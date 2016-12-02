package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class Position extends ConfigurableRateAttribute {
  public static String North = "position_north";
  public static String East = "position_east";
  public static String Up = "position_up";
  public static String Time = "position_time";

  public Position(int registerAddress, String name, int bitOffset) {
    super(registerAddress, name, bitOffset);
  }
}
