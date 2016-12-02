package pl.agilevision.hardware.um7.data.nmea;

/**
 * Created by volodymyr on 02.12.16.
 */
public class RatePacket extends  UM7NMEAPacket{
  public static final String HEADER = "$PCHRR";

  private float velocityNorth;
  private float velocityEast;
  private float velocityUpward;
  private float rollRate;
  private float pitchRate;
  private float yawRate;

  public float getVelocityNorth() {
    return velocityNorth;
  }

  public void setVelocityNorth(String value) {
    this.velocityNorth =  Float.parseFloat(value);
  }

  public float getVelocityEast() {
    return velocityEast;
  }

  public void setVelocityEast(String value) {
    this.velocityEast = Float.parseFloat(value);
  }

  public float getVelocityUpward() {
    return velocityUpward;
  }

  public void setVelocityUpward(String value) {
    this.velocityUpward = Float.parseFloat(value);
  }

  public float getRollRate() {
    return rollRate;
  }

  public void setRollRate(String value) {
    this.rollRate = Float.parseFloat(value);
  }

  public float getPitchRate() {
    return pitchRate;
  }

  public void setPitchRate(String value) {
    this.pitchRate = Float.parseFloat(value);
  }

  public float getYawRate() {
    return yawRate;
  }

  public void setYawRate(String value) {
    this.yawRate = Float.parseFloat(value);
  }
}
