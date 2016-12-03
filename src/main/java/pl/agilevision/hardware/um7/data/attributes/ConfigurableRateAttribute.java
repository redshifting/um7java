package pl.agilevision.hardware.um7.data.attributes;

/**
 * Rate configuration
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class ConfigurableRateAttribute {
  private int rateConfRegisterAddress;
  private int rateConfBitOffset;
  private int rateConfWidth;
  private String rateConfName;

  public ConfigurableRateAttribute(int rateConfRegisterAddress, String rateConfName, int rateConfBitOffset){
    this(rateConfRegisterAddress, rateConfName, rateConfBitOffset, 8);
  }

  public ConfigurableRateAttribute(int rateConfRegisterAddress, String rateConfName, int rateConfBitOffset, int rateConfWidth){
    this.rateConfRegisterAddress = rateConfRegisterAddress;
    this.rateConfBitOffset = rateConfBitOffset;
    this.rateConfWidth = rateConfWidth;
    this.rateConfName = rateConfName;
  }

  public int getRateConfRegisterAddress() {
    return rateConfRegisterAddress;
  }

  public int getRateConfBitOffset() {
    return rateConfBitOffset;
  }

  public int getRateConfWidth() {
    return rateConfWidth;
  }

  public String getRateConfName() { return rateConfName; }

}
