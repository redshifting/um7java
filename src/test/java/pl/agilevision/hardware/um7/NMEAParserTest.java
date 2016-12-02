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

    UM7Packet nmeaPacket = parser.parse(data);

    assertNotNull(nmeaPacket);
  }

  @Test
  public void testParsePose(){

    // Given
    final byte[] data = "$PCHRP,105.015,-501.234,-501.234,15.521,20.32,20.32,20.32,20.32,*47".getBytes(
        StandardCharsets.US_ASCII);

    // Then
    assertTrue(parser.canParse(data));

    UM7Packet nmeaPacket = parser.parse(data);

    assertNotNull(nmeaPacket);
  }
}
