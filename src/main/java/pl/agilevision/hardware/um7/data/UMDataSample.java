package pl.agilevision.hardware.um7.data;

import java.util.Map;

/**
 * Describes a data sample of the UM7
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public class UMDataSample{
  private Map<String, Object> data;

  public UMDataSample(Map<String, Object> data) {
    this.data = data;
  }

  public void update(UMDataSample sample) {
    this.data.putAll(sample.getRawData());
  }

  /**
   * Returns raw data
   * @return raw data
   */
  public Map<String, Object> getRawData() {
    return data;
  }

  /**
   * Returns a property value casted to a type or null if no property exists
   * @param property property name
   * @param type property type
   * @param <T> property type class
   * @return property value casted to T or null
   */
  public <T> T getValue(final String property, Class<T> type){
    return (T)data.get(property);
  }

  public Object getUntypedValue(final String property) {
    return data.get(property);
  }

  /**
   * Returns property value as String or null if no property exists
   * @param property property name
   * @return property value or null
   */
  public String getValue(final String property){
    return String.valueOf(data.get(property));
  }

  public boolean hasValue(final String property){
    return data.containsKey(property);
  }

}
