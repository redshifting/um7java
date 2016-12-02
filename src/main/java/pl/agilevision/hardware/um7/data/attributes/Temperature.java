package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class Temperature extends ConfigurableRateAttribute {
  public static String Value = "temp";
  public static String Time = "temp_time";

  public Temperature(int registerAddress, String name, int bitOffset) {
    super(registerAddress, name, bitOffset);
  }
}
