package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class Gps extends ConfigurableRateAttribute {
  public static String Latitude = "gps_latitude";
  public static String Longitude  = "gps_longitude";
  public static String Altitude = "gps_altitude";
  public static String Course = "gps_cource";
  public static String Speed = "gps_speed";
  public static String Time = "gps_time";

  public Gps(int registerAddress, String name, int bitOffset, int bitWidth, int defaultValue) {
    super(registerAddress, name, bitOffset, bitWidth, defaultValue);
  }
}
