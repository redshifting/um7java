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
  private int rateValue;
  private String rateConfName;

  public ConfigurableRateAttribute(int rateConfRegisterAddress, String rateConfName, int rateConfBitOffset){
    this(rateConfRegisterAddress, rateConfName, rateConfBitOffset, 8, 0);
  }

  public ConfigurableRateAttribute(int rateConfRegisterAddress, String rateConfName, int rateConfBitOffset, int defaultRate){
    this(rateConfRegisterAddress, rateConfName, rateConfBitOffset, 8, defaultRate);
  }

  public ConfigurableRateAttribute(int rateConfRegisterAddress, String rateConfName, int rateConfBitOffset, int rateConfWidth, int defaultRate){
    this.rateConfRegisterAddress = rateConfRegisterAddress;
    this.rateConfBitOffset = rateConfBitOffset;
    this.rateConfWidth = rateConfWidth;
    this.rateConfName = rateConfName;
    this.rateValue = defaultRate;  // current rate value influences on batch data portions so we need to store it
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

  public int getRateValue() {
    return rateValue;
  }

  public void setRateValue(int rateValue) {
    this.rateValue = rateValue;
  }
}
