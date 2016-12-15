package pl.agilevision.hardware.um7;

import org.junit.Test;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.data.parser.NMEAPacketParser;
import pl.agilevision.hardware.um7.data.parser.PacketParser;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by volodymyr on 02.12.16.
 */
public class NMEAParserTest {

  private PacketParser parser = new NMEAPacketParser();


  @Test
  public void testParseHealth(){

    // Given
    final byte[] data = "$PCHRH,105.015,05,11,1.5,0,0,0,0,0,0,0,0,0,*70".getBytes(
        StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }

  @Test
  public void testParsePose(){

    // Given
    final byte[] data = "$PCHRP,105.015,-501.234,-501.234,15.521,20.32,20.32,20.32,20.32,*47".getBytes(
        StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }

  @Test
  public void testParsePoseAlternative(){

    // Given
    final byte[] data = "$PCHRP,105.015,-501.234,-501.234,15.521,20.32,20.32,20.32,*46".getBytes(
      StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }

  @Test
  public void testParseSensor(){

    final byte[] data = "$PCHRS,1,105.015,-0.9987,-0.9987,-0.9987,*79".getBytes(
        StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }

  @Test
  public void testParseAttitude(){

    final byte[] data = "$PCHRA,105.015,20.32,20.32,20.32,20.32,*66".getBytes(
        StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }


  @Test
  public void testParseAttitudeAlternative(){

    final byte[] data = "$PCHRA,105.015,20.32,20.32,20.32,*67".getBytes(
      StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }

  @Test
  public void testParseGpsPose(){

    final byte[] data = "$PCHRG,105.015,40.047706,-111.742072,15.230,20.32,20.32,20.32,20.32,*49".getBytes(
        StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }

  @Test
  public void testParseGpsPoseAlternative() {
    final byte[] data = "$PCHRG,105.015,40.047706,-111.742072,15.230,20.32,20.32,20.32,*48".getBytes(
      StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }

  @Test
  public void testParseRate(){

    final byte[] data = "$PCHRR,105.015,15.23,15.23,15.23,-450.26,-450.26,-450.26,*68".getBytes(
        StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }

  @Test
  public void testParseQuaternion(){

    final byte[] data = "$PCHRQ,105.015,0.76592,0.76592,0.76592,0.76592,*76".getBytes(
        StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data, null);

    assertNotNull(nmeaPacket);
  }
}
