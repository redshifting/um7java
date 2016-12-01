package pl.agilevision.hardware.um7.data;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class GyroProcessed extends BaseAttribute {
  public static String X = "gyro_proc_x";
  public static String Y = "gyro_proc_y";
  public static String Z = "gyro_proc_z";
  public static String Time = "gyro_proc_time";

  public GyroProcessed(int registerAddress, String name, int bitOffset) {
    super(registerAddress, name, bitOffset);
  }
}
