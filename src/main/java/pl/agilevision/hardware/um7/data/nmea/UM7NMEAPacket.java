package pl.agilevision.hardware.um7.data.nmea;

import pl.agilevision.hardware.um7.data.UM7Packet;

import java.lang.reflect.Field;

/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class UM7NMEAPacket extends UM7Packet{
  public static boolean isNmea() {
    return true;
  }
}
