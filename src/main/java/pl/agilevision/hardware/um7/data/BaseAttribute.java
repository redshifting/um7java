package pl.agilevision.hardware.um7.data;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class BaseAttribute {
  private int registerAddress;
  private int bitOffset;
  private int width;

  public BaseAttribute(int registerAddress, int bitOffset){
    this(registerAddress, bitOffset, 8);
  }

  public BaseAttribute(int registerAddress, int bitOffset, int width){
    this.registerAddress = registerAddress;
    this.bitOffset = bitOffset;
    this.width = width;
  }

  public int getRegisterAddress() {
    return registerAddress;
  }

  public int getBitOffset() {
    return bitOffset;
  }

  public int getWidth() {
    return width;
  }
}
