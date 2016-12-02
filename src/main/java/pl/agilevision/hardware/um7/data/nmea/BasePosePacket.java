package pl.agilevision.hardware.um7.data.nmea;

/**
 * Created by volodymyr on 02.12.16.
 */
public class BasePosePacket extends UM7NMEAPacket {
  private float time;
  private float roll;
  private float pitch;
  private float yaw;
  private float heading;

  public float getTime() {
    return time;
  }

  public void setTime(String value) {
    this.time = Float.parseFloat(value);
  }

  public float getRoll() {
    return roll;
  }

  public void setRoll(String value) {
    this.roll = Float.parseFloat(value);
  }

  public float getPitch() {
    return pitch;
  }

  public void setPitch(String value) {
    this.pitch = Float.parseFloat(value);
  }

  public float getYaw() {
    return yaw;
  }

  public void setYaw(String value) {
    this.yaw = Float.parseFloat(value);
  }

  public float getHeading() {
    return heading;
  }

  public void setHeading(String value) {
    this.heading = Float.parseFloat(value);
  }

}
