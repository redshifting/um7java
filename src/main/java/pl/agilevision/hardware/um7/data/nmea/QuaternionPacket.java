package pl.agilevision.hardware.um7.data.nmea;

/**
 * Created by volodymyr on 02.12.16.
 */
public class QuaternionPacket extends UM7NMEAPacket {
  public static final String HEADER = "$PCHRQ";

  private float a;
  private float b;
  private float c;
  private float d;


  public float getA() {
    return a;
  }

  public void setA(String value) {
    this.a = Float.parseFloat(value);
  }

  public float getB() {
    return b;
  }

  public void setB(String value) {
    this.b = Float.parseFloat(value);
  }

  public float getC() {
    return c;
  }

  public void setC(String value) {
    this.c = Float.parseFloat(value);
  }

  public float getD() {
    return d;
  }

  public void setD(String value) {
    this.d = Float.parseFloat(value);
  }
}
