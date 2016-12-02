package pl.agilevision.hardware.um7.data.nmea;

/**
 * Created by volodymyr on 02.12.16.
 */
public class HealthPacket extends UM7NMEAPacket {
  public static final String HEADER = "$PCHRH";
  private final String STR_TRUE = "1";

  private int satsUsed;
  private int satsInView;
  private float hdop;
  private boolean comFault;
  private boolean acceleratorRateFault;
  private boolean gyroRateFault;
  private boolean magnetometerRateFault;
  private boolean gpsOffline;
  private String mode;


  public int getSatsUsed() {
    return satsUsed;
  }

  public void setSatsUsed(String satsUsed) {
    this.satsUsed = Integer.parseInt(satsUsed);
  }

  public int getSatsInView() {
    return satsInView;
  }

  public void setSatsInView(String satsInView) {
    this.satsInView = Integer.parseInt(satsInView);
  }

  public float getHdop() {
    return hdop;
  }

  public void setHdop(String hdop) {
    this.hdop = Float.parseFloat(hdop);
  }

  public boolean isComFault() {
    return comFault;
  }

  public void setComFault(String fault) {
    this.comFault = STR_TRUE.equals(fault);
  }

  public boolean isAcceleratorRateFault() {
    return acceleratorRateFault;
  }

  public void setAcceleratorRateFault(String fault) {
    this.acceleratorRateFault = STR_TRUE.equals(fault);
  }

  public boolean isGyroRateFault() {
    return gyroRateFault;
  }

  public void setGyroRateFault(String fault) {
    this.gyroRateFault = STR_TRUE.equals(fault);
  }

  public boolean isMagnetometerRateFault() {
    return magnetometerRateFault;
  }

  public void setMagnetometerRateFault(String fault) {
    this.magnetometerRateFault =  STR_TRUE.equals(fault);
  }

  public boolean isGpsOffline() {
    return gpsOffline;
  }

  public void setGpsOffline(String fault) {
    this.gpsOffline = STR_TRUE.equals(fault);
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }
}
