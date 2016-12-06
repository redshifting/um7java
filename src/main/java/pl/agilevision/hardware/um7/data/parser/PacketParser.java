package pl.agilevision.hardware.um7.data.parser;

import pl.agilevision.hardware.um7.data.UM7Packet;

/**
 * Interface for all packet parsers
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public interface PacketParser {

  /**
   * Returns true of the given packet can be parsed by the current
   * @param data data to be parsed
   * @return true if the data can be parsed
   */
  boolean canParse(final byte[] data, Integer... startAddress);

  /**
   * Parses data and returns the packet
   * @param data data to parse
   * @return packet
   */
  UM7Packet parse(final byte[] data, Integer... startAddress);
}
