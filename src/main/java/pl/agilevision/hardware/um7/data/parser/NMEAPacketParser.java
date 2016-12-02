package pl.agilevision.hardware.um7.data.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.data.nmea.AttitudePacket;
import pl.agilevision.hardware.um7.data.nmea.GpsPosePacket;
import pl.agilevision.hardware.um7.data.nmea.HealthPacket;
import pl.agilevision.hardware.um7.data.nmea.PosePacket;
import pl.agilevision.hardware.um7.data.nmea.QuaternionPacket;
import pl.agilevision.hardware.um7.data.nmea.RatePacket;
import pl.agilevision.hardware.um7.data.nmea.SensorPacket;
import pl.agilevision.hardware.um7.data.nmea.UM7NMEAPacket;
import pl.agilevision.hardware.um7.impl.DefaultUM7Client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by volodymyr on 02.12.16.
 */
public class NMEAPacketParser implements PacketParser {

  private static final Logger LOG = LoggerFactory.getLogger(DefaultUM7Client.class);
  private static final byte[] PACKET_PREFIX = "$PCHR".getBytes(StandardCharsets.US_ASCII);
  private static final int PACKET_HEADER_LENGTH = PACKET_PREFIX.length + 2;

  private static final int CHECKSUM_BLOCK_SIZE = 4;
  private static final int RADIX_HEX = 16;


  private static final Map<String, Class<? extends UM7NMEAPacket>> PACKET_MAPPINGS;
  private static final Map<String, String[]> PACKET_COLUMNS_MAPPING;


  static {
    PACKET_MAPPINGS = new HashMap<>();
    PACKET_MAPPINGS.put(AttitudePacket.HEADER, AttitudePacket.class);
    PACKET_MAPPINGS.put(GpsPosePacket.HEADER, GpsPosePacket.class);
    PACKET_MAPPINGS.put(HealthPacket.HEADER, HealthPacket.class);
    PACKET_MAPPINGS.put(PosePacket.HEADER, PosePacket.class);
    PACKET_MAPPINGS.put(QuaternionPacket.HEADER, QuaternionPacket.class);
    PACKET_MAPPINGS.put(RatePacket.HEADER, RatePacket.class);
    PACKET_MAPPINGS.put(SensorPacket.HEADER, SensorPacket.class);


    PACKET_COLUMNS_MAPPING = new HashMap<>();
    PACKET_COLUMNS_MAPPING.put(AttitudePacket.HEADER,
        null);
    PACKET_COLUMNS_MAPPING.put(GpsPosePacket.HEADER,
        new String[]{});
    PACKET_COLUMNS_MAPPING.put(HealthPacket.HEADER,
        new String[]{"time", "satsUsed", "satsInView", "hdop", "mode", "comFault", "acceleratorRateFault", "gyroRateFault", "magnetometerRateFault", "gpsOffline", null, null, null});
    PACKET_COLUMNS_MAPPING.put(PosePacket.HEADER,
        new String[]{"time", "homeNorth", "homeEast", "homeAltitude", "roll", "pitch", "yaw", "heading"});
    PACKET_COLUMNS_MAPPING.put(QuaternionPacket.HEADER,
        new String[]{});
    PACKET_COLUMNS_MAPPING.put(RatePacket.HEADER,
        new String[]{});
    PACKET_COLUMNS_MAPPING.put(SensorPacket.HEADER,
        new String[]{});

  }



  public NMEAPacketParser(){

  }


  @Override
  public boolean canParse(byte[] data) {
    return ParserUtils.beginsWith(data, PACKET_PREFIX);
  }

  @Override
  public UM7Packet parse(byte[] data) {
    final String header = new String(Arrays.copyOf(data, PACKET_HEADER_LENGTH - 1),
        StandardCharsets.US_ASCII);

    if (PACKET_MAPPINGS.containsKey(header)) {


      final int dataStart = PACKET_HEADER_LENGTH;
      final int dataEnd = data.length - CHECKSUM_BLOCK_SIZE;

      final int checksum = Integer.parseInt(new String(Arrays.copyOfRange(data, dataEnd + 2, data.length),
          StandardCharsets.US_ASCII), RADIX_HEX);

      final byte[] csvData = Arrays.copyOfRange(data, dataStart, dataEnd - 1);

      final ICsvBeanReader beanReader = new CsvBeanReader(
          new InputStreamReader(new ByteArrayInputStream(csvData)),
          CsvPreference.STANDARD_PREFERENCE);

      try {
        final Class<? extends UM7NMEAPacket> targetClass = PACKET_MAPPINGS.get(header);
        final String[] mappings = PACKET_COLUMNS_MAPPING.get(header);

        return beanReader.read(targetClass, mappings);
      } catch (IOException e) {
        LOG.error("Failed to parse package", e);
        return null;
      }


    } else {
      LOG.warn("Unknown packet header {}", header);
      return null;
    }
  }
}
