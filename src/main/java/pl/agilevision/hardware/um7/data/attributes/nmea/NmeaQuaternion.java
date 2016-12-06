package pl.agilevision.hardware.um7.data.attributes.nmea;

import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import pl.agilevision.hardware.um7.data.attributes.ConfigurableRateAttribute;

/**
 * NmeaPose packet attributes
 * @author Ivan Borschov (iborschov@agilevision.pl)
 */
public class NmeaPose extends ConfigurableRateAttribute {

  public static final String NMEA_HEADER = "$PCHRP";

  public static final String Time = "nmea_pose_time";
  public static final String HomeNorth = "nmea_pose_home_north";
  public static final String HomeEast = "nmea_pose_home_east";
  public static final String HomeAltitude = "nmea_pose_home_altitude";
  public static final String Roll = "nmea_pose_home_roll";
  public static final String Pitch = "nmea_pose_home_pitch";
  public static final String Yaw = "nmea_pose_home_yaw";
  public static final String Heading = "nmea_pose_home_heading";


  public static final String[] parseFormat = {Time, HomeNorth, HomeEast, HomeAltitude, Roll, Pitch, Yaw,
    Heading, NmeaCheckSumField};

  public static final CellProcessor[] parseCellProcessor = new CellProcessor[]{
    new ParseDouble(), // Time
    new ParseDouble(), // HomeNorth
    new ParseDouble(), // HomeEast",

    new ParseDouble(), // HomeAltitude
    new ParseDouble(), // Roll
    new ParseDouble(), // Pitch
    new ParseDouble(), // Yaw
    new ParseDouble(), // Heading
    new NotNull(), // checksum"
  };

  public NmeaPose(int registerAddress, String name, int bitOffset, int bitWidth) {
    super(registerAddress, name, bitOffset, bitWidth);
  }
}
