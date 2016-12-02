package pl.agilevision.hardware.um7.data;

import java.util.List;

/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class UM7Packet {

  /**
   * Describes attributes the given packet contains. Can be 1 or more attributes
   * depending on the packet type
   */
  private List<String> attributes;


  public List<String> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<String> attributes) {
    this.attributes = attributes;
  }
}
