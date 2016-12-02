package pl.agilevision.hardware.um7.data.nmea;

import pl.agilevision.hardware.um7.data.UM7Packet;

/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public abstract class UM7NMEAPacket extends UM7Packet{
  private String header;
  private String checksum;


  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getChecksum() {
    return checksum;
  }

  public void setChecksum(String checksum) {
    this.checksum = checksum;
  }
}
