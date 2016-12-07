package pl.agilevision.hardware.um7.data.attributes.nmea;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import pl.agilevision.hardware.um7.data.attributes.ConfigurableRateAttribute;

/**
 * NmeaPose packet attributes
 * @author Ivan Borschov (iborschov@agilevision.pl)
 */
public class NmeaQuaternion extends ConfigurableRateAttribute {

  public static final String NMEA_HEADER = "$PCHRQ";

  public static final String Time = "nmea_quaternion_time";
  public static final String A = "nmea_quaternion_a";
  public static final String B = "nmea_quaternion_b";
  public static final String C = "nmea_quaternion_c";
  public static final String D = "nmea_quaternion_d";

  public static final String[] parseFormat = {Time, A, B, C, D};

  public static final CellProcessor[] parseCellProcessor = new CellProcessor[] {
    new ParseDouble(), // time
    new ParseDouble(), // a
    new ParseDouble(), // b
    new ParseDouble(), // c
    new ParseDouble(), // d
  };

  public NmeaQuaternion(int registerAddress, String name, int bitOffset, int bitWidth) {
    super(registerAddress, name, bitOffset, bitWidth);
  }
}
