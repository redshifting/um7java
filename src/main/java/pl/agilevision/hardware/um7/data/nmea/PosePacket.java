package pl.agilevision.hardware.um7.data.nmea;

/**
 * Created by volodymyr on 02.12.16.
 */
public class PosePacket extends BasePosePacket {
  public static final String HEADER = "$PCHRP";

  private float homeNorth;
  private float homeEast;
  private float homeAltitude;


  public float getHomeNorth() {
    return homeNorth;
  }

  public void setHomeNorth(String value) {
    this.homeNorth = Float.parseFloat(value);
  }

  public float getHomeEast() {
    return homeEast;
  }

  public void setHomeEast(String value) {
    this.homeEast = Float.parseFloat(value);
  }

  public float getHomeAltitude() {
    return homeAltitude;
  }

  public void setHomeAltitude(String value) {
    this.homeAltitude = Float.parseFloat(value);
  }
}
