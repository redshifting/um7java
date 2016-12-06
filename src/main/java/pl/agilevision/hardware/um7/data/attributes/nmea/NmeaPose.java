package pl.agilevision.hardware.um7.data.attributes.nmea;

import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import pl.agilevision.hardware.um7.data.attributes.ConfigurableRateAttribute;

/**
 * NmeaHealth configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 */
public class NmeaHealth extends ConfigurableRateAttribute {

  public static final String NMEA_HEADER = "$PCHRH";

  public static final String Time = "nmea_health_time";
  public static final String SatsUsed  = "nmea_health_sats_used";
  public static final String SatsInView = "nmea_health_sats_in_view";
  public static final String Hdop = "nmea_health_hdop";
  public static final String Mode = "nmea_health_mode";
  public static final String ComOverflow = "nmea_health_com_overflow";
  public static final String AcceleratorRateFault = "nmea_health_accelerator_rate_fault";
  public static final String GyroRateFault = "nmea_health_gyro_rate_fault";
  public static final String MagnetometerRateFault = "nmea_health_magnetometer_rate_fault";
  public static final String GpsOffline = "nmea_health_gps_offline";
  public static final String Reserved1 = "nmea_health_reserved1";
  public static final String Reserved2 = "nmea_health_reserved2";
  public static final String Reserved3 = "nmea_health_reserved3";

  public static final String[] parseFormat = {Time, SatsUsed, SatsInView, Hdop, Mode, ComOverflow, AcceleratorRateFault,
    GyroRateFault, MagnetometerRateFault, GpsOffline, Reserved1, Reserved2, Reserved3, NmeaCheckSumField};

  public static final CellProcessor[] parseCellProcessor = new CellProcessor[] {
      new ParseDouble(), // Time
      new ParseInt(), // SatsUsed
      new ParseInt(), // SatsInView
      new ParseDouble(), // Hdop
      new ParseInt(), // Mode
      new ParseBool(), // ComOverflow
      new ParseBool(), // AcceleratorRateFault
      new ParseBool(), // GyroRateFault
      new ParseBool(), // MagnetometerRateFault
      new ParseBool(), // GpsOffline
      new ParseInt(), // Reserved1,
      new ParseInt(), // Reserved2,
      new ParseInt(), // Reserved3,
      new NotNull(),// NmeaCheckSumField
  };

  public NmeaHealth(int registerAddress, String name, int bitOffset, int bitWidth) {
    super(registerAddress, name, bitOffset, bitWidth);
  }
}
