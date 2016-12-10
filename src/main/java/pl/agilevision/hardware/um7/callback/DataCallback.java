package pl.agilevision.hardware.um7.callback;

import pl.agilevision.hardware.um7.data.UM7Packet;

/**
 * Data callback class
 * @author Ivan Borschov (iborschov@agilevision.pl)
 */
public interface DataCallback {

  /**
   * Implement this method to catch data packets
   * @return packet
   * @throws
   */
  void onPacket(UM7Packet packet);
}
