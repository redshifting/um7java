package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class BaseAttribute {
  private int registerAddress;
  private int bitOffset;
  private int width;
  private String name;

  public BaseAttribute(int registerAddress, String name, int bitOffset){
    this(registerAddress, name, bitOffset, 8);
  }

  public BaseAttribute(int registerAddress, String name, int bitOffset, int width){
    this.registerAddress = registerAddress;
    this.bitOffset = bitOffset;
    this.width = width;
    this.name = name;
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

  public String getName() { return name; }
}
