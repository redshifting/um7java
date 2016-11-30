package pl.agilevision.hardware.um7.data;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class MagnetometerProcessed extends BaseAttribute {

  public static String X = "mag_proc_x";
  public static String Y = "mag_proc_y";
  public static String Z = "mag_proc_z";
  public static String Time = "mag_proc_time";

  public MagnetometerProcessed(long registerAddress, int bitOffset) {
    super(registerAddress, bitOffset);
  }
}