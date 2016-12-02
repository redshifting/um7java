package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class Health extends ConfigurableRateAttribute {
  public static String Value = "health";

  public Health(int registerAddress, String name, int bitOffset, int width) {
    super(registerAddress, name, bitOffset, width);
  }
}
