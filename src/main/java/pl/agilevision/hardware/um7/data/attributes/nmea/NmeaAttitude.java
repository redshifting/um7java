package pl.agilevision.hardware.um7.data.attributes.nmea;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import pl.agilevision.hardware.um7.data.attributes.ConfigurableRateAttribute;

/**
 * NmeaHealth packet attributes
 * @author Ivan Borschov (iborschov@agilevision.pl)
 */
public class NmeaAttitude extends ConfigurableRateAttribute {

  public static final String NMEA_HEADER = "$PCHRA";

  public static final String Time = "nmea_attitude_time";
  public static final String Roll = "nmea_attitude_roll";
  public static final String Pitch = "nmea_attitude_pitch";
  public static final String Yaw = "nmea_attitude_yaw";
  public static final String Heading = "nmea_attitude_heading";

  public static final String[] parseFormat = {Time, Roll, Pitch,
          Yaw,
          //Heading
  };

  public static final CellProcessor[] parseCellProcessor =  new CellProcessor[] {
    new ParseDouble(), //time
    new ParseDouble(), //roll
    new ParseDouble(), //pitch
    new ParseDouble(), //yaw
   // new ParseDouble(), //heading
  };

  public NmeaAttitude(int registerAddress, String name, int bitOffset, int bitWidth) {
    super(registerAddress, name, bitOffset, bitWidth);
  }
}
