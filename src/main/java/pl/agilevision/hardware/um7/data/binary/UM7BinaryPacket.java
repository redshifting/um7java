package pl.agilevision.hardware.um7.data.binary;

import pl.agilevision.hardware.um7.data.UM7Packet;

/**
 * Binary data packet
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class UM7BinaryPacket extends UM7Packet {
    public boolean foundpacket;
    public boolean hasdata;
    public int startaddress;
    public byte[] data;
    public boolean commandfailed;
    public boolean timeout;

    public UM7BinaryPacket(boolean foundpacket, boolean hasdata, int startaddress, byte[] data, boolean commandfailed, boolean timeout) {
        this.foundpacket = foundpacket;
        this.hasdata = hasdata;
        this.startaddress = startaddress;
        this.data = data;
        this.commandfailed = commandfailed;
        this.timeout = timeout;
    }
}
