package pl.agilevision.hardware.um7.data.attributes.nmea;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import pl.agilevision.hardware.um7.data.attributes.ConfigurableRateAttribute;

/**
 * NmeaGpsPose packet attributes
 * @author Ivan Borschov (iborschov@agilevision.pl)
 */
public class NmeaGpsPose extends ConfigurableRateAttribute {

  public static final String NMEA_HEADER = "$PCHRG";

  public static final String Time = "nmea_gpspose_time";
  public static final String Latitude = "nmea_gpspose_latitude";
  public static final String Longitude = "nmea_gpspose_longitude";
  public static final String Altitude = "nmea_gpspose_altitude";
  public static final String Roll = "nmea_gpspose_roll";
  public static final String Pitch = "nmea_gpspose_pitch";
  public static final String Yaw = "nmea_gpspose_yaw";
  public static final String Heading = "nmea_gpspose_heading";


  public static final String[] parseFormat = {Time, Latitude, Longitude, Altitude, Roll, Pitch, Yaw,
    //Heading
  };

  public static final CellProcessor[] parseCellProcessor = new CellProcessor[] {
    new ParseDouble(), //time
    new ParseDouble(), //latitude
    new ParseDouble(), //longitude
    new ParseDouble(), //altitude

    new ParseDouble(), //roll
    new ParseDouble(), //pitch
    new ParseDouble(), //yaw
   // new ParseDouble(), //heading
  };

  public NmeaGpsPose(int registerAddress, String name, int bitOffset, int bitWidth) {
    super(registerAddress, name, bitOffset, bitWidth);
  }
}
