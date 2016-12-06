package pl.agilevision.hardware.um7.data.attributes.nmea;

import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import pl.agilevision.hardware.um7.data.attributes.ConfigurableRateAttribute;

/**
 * NmeaSensor configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 */
public class NmeaSensor extends ConfigurableRateAttribute {

  public static final String NMEA_HEADER = "$PCHRS";

  public static final String SensorType = "nmea_sensor_type";
  public static final String Time = "nmea_sensor_time";
  public static final String X = "nmea_sensor_x";
  public static final String Y = "nmea_sensor_y";
  public static final String Z = "nmea_sensor_z";

  public static final String[] parseFormat = {SensorType, Time, X, Y, Z};

  public static final CellProcessor[] parseCellProcessor = new CellProcessor[] {
    new ParseInt(), //sensorType
    new ParseDouble(), //time
    new ParseDouble(), //x
    new ParseDouble(), //y
    new ParseDouble(), //z
  };

  public NmeaSensor(int registerAddress, String name, int bitOffset, int bitWidth) {
    super(registerAddress, name, bitOffset, bitWidth);
  }
}
