package pl.agilevision.hardware.um7.data.nmea;

/**
 * Created by volodymyr on 02.12.16.
 */
public class GpsPosePacket extends BasePosePacket {
  public static final String HEADER = "$PCHRG";
  private float latitude;
  private float longitude;
  private float altitude;

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(String value) {
    this.latitude = Float.parseFloat(value);
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(String value) {
    this.longitude = Float.parseFloat(value);
  }

  public float getAltitude() {
    return altitude;
  }

  public void setAltitude(String value) {
    this.altitude = Float.parseFloat(value);
  }
}
