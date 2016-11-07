package pl.agilevision.hardware.um7.data;

/**
 * Low-level data packet
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class UM7Packet {
    public boolean foundpacket;
    public boolean hasdata;
    public int startaddress;
    public Object data;
    public boolean commandfailed;
    public boolean timeou;

    public UM7Packet(boolean foundpacket, boolean hasdata, int startaddress, Object data, boolean commandfailed, boolean timeou) {
        this.foundpacket = foundpacket;
        this.hasdata = hasdata;
        this.startaddress = startaddress;
        this.data = data;
        this.commandfailed = commandfailed;
        this.timeou = timeou;
    }
}
