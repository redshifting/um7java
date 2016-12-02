package pl.agilevision.hardware.um7.data;

import java.util.List;
import java.util.Map;

/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class UM7Packet {

  /**
   * Describes attributes the given packet contains. Can be 1 or more attributes
   * depending on the packet type
   */
  private Map<String, Object> attributes;


  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }
}
