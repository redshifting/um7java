package pl.agilevision.hardware.um7.data.parser;

import pl.agilevision.hardware.um7.callback.DataCallback;
import pl.agilevision.hardware.um7.data.UM7Packet;
import pl.agilevision.hardware.um7.data.attributes.ConfigurableRateAttribute;

import java.util.Map;

/**
 * Interface for all packet parsers
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public abstract class PacketParser {

  void callBack(Map<ConfigurableRateAttribute, DataCallback> callbacks, ConfigurableRateAttribute attribute,
                UM7Packet packet) {

    if (null != callbacks && callbacks.containsKey(attribute)) {
      callbacks.get(attribute).onPacket(packet);
    }
  }


  /**
   * Returns true of the given packet can be parsed by the current
   * @param data data to be parsed
   * @return true if the data can be parsed
   */
  abstract public boolean canParse(final byte[] data, Integer... startAddress);

  /**
   * Parses data and returns the packet
   * @param data data to parse
   * @return packet
   */
  abstract public UM7Packet parse(final byte[] data, Map<ConfigurableRateAttribute, DataCallback> callbacks, Integer... startAddress);
}
