package pl.agilevision.hardware.um7.data.nmea;

/**
 * Created by volodymyr on 02.12.16.
 */
public class SensorPacket extends  UM7NMEAPacket{
  public static final String HEADER = "$PCHRS";

  private float time;
  private SensorType sensorType;
  private float x;
  private float y;
  private float z;

  public float getTime() {
    return time;
  }

  public void setTime(String value) {
    this.time = Float.parseFloat(value);
  }

  public SensorType getSensorType() {
    return sensorType;
  }

  public void setSensorType(String sensorType) {
    if ("0".equals(sensorType)){
      this.sensorType = SensorType.Gyro;
    }

    if ("1".equals(sensorType)){
      this.sensorType = SensorType.Accelerometer;
    }

    if ("2".equals(sensorType)){
      this.sensorType = SensorType.Magnetometer;
    }

  }

  public float getX() {
    return x;
  }

  public void setX(String value) {
    this.x = Float.parseFloat(value);
  }

  public float getY() {
    return y;
  }

  public void setY(String value) {
    this.y = Float.parseFloat(value);
  }

  public float getZ() {
    return z;
  }

  public void setZ(String value) {
    this.z = Float.parseFloat(value);
  }
}
