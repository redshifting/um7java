package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class GpsSateliteDetails extends ConfigurableRateAttribute {
  public static String Sat1Id = "gps_sat_1_id";
  public static String Sat1Snr = "gps_sat_1_snr";

  public static String Sat2Id = "gps_sat_2_id";
  public static String Sat2Snr = "gps_sat_2_snr";

  public static String Sat3Id = "gps_sat_3_id";
  public static String Sat3Snr = "gps_sat_3_snr";

  public static String Sat4Id = "gps_sat_4_id";
  public static String Sat4Snr = "gps_sat_4_snr";

  public static String Sat5Id = "gps_sat_5_id";
  public static String Sat5Snr = "gps_sat_5_snr";

  public static String Sat6Id = "gps_sat_6_id";
  public static String Sat6Snr = "gps_sat_6_snr";

  public static String Sat7Id = "gps_sat_7_id";
  public static String Sat7Snr = "gps_sat_7_snr";

  public static String Sat8Id = "gps_sat_8_id";
  public static String Sat8Snr = "gps_sat_8_snr";

  public static String Sat9Id = "gps_sat_9_id";
  public static String Sat9Snr = "gps_sat_9_snr";

  public static String Sat10Id = "gps_sat_10_id";
  public static String Sat10Snr = "gps_sat_10_snr";

  public static String Sat11Id = "gps_sat_11_id";
  public static String Sat11Snr = "gps_sat_11_snr";

  public static String Sat12Id = "gps_sat_12_id";
  public static String Sat12Snr = "gps_sat_12_snr";

  public GpsSateliteDetails(int registerAddress, String name, int bitOffset, int bitWidth) {
    super(registerAddress, name, bitOffset, bitWidth);
  }
}
