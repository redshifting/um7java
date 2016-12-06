package pl.agilevision.hardware.um7.data.attributes.nmea;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import pl.agilevision.hardware.um7.data.attributes.ConfigurableRateAttribute;

/**
 * NmeaRate packet attributes
 * @author Ivan Borschov (iborschov@agilevision.pl)
 */
public class NmeaRate extends ConfigurableRateAttribute {

  public static final String NMEA_HEADER = "$PCHRR";

  public static final String Time = "nmea_rate_time";
  public static final String VelocityNorth = "nmea_rate_velocity_north";
  public static final String VelocityEast = "nmea_rate_velocity_east";
  public static final String VelocityUpward = "nmea_rate_velocity_upward";
  public static final String RollRate = "nmea_rate_roll_rate";
  public static final String PitchRate = "nmea_rate_pitch_rate";
  public static final String YawRate = "nmea_rate_yaw_rate";


  public static final String[] parseFormat = {Time, VelocityNorth, VelocityEast, VelocityUpward, RollRate,
    PitchRate, YawRate};

  public static final CellProcessor[] parseCellProcessor = new CellProcessor[] {
    new ParseDouble(), // time
    new ParseDouble(), // velocityNorth
    new ParseDouble(), // velocityEast
    new ParseDouble(), // velocityUpward

    new ParseDouble(), // rollRate
    new ParseDouble(), // pitchRate
    new ParseDouble(), // yawRate
  };

  public NmeaRate(int registerAddress, String name, int bitOffset, int bitWidth) {
    super(registerAddress, name, bitOffset, bitWidth);
  }
}
