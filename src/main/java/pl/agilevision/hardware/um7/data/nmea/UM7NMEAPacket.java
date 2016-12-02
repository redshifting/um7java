package pl.agilevision.hardware.um7.data.nmea;

import pl.agilevision.hardware.um7.data.UM7Packet;

/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public abstract class UM7NMEAPacket extends UM7Packet{
  private float time;

  public float getTime() {
    return time;
  }

  public void setTime(String value) {
    this.time = Float.parseFloat(value);
  }
}
