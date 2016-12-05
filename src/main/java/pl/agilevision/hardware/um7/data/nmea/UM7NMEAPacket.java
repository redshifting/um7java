package pl.agilevision.hardware.um7.data.nmea;

import pl.agilevision.hardware.um7.data.UM7Packet;

import java.lang.reflect.Field;

/**
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public abstract class UM7NMEAPacket extends UM7Packet{
  private float time;

  public float getTime() {
    return time;
  }

  public void setTime(String value) {
    this.time = Float.parseFloat(value);
  }

  public Object getFieldByName(String fieldName) {
    Field f;
    try {
      //System.out.println("Try get field :"+fieldName);
      f =  this.getClass().getDeclaredField(fieldName);

    } catch (NoSuchFieldException e) {
      Class superClass = this.getClass().getSuperclass();
      if (superClass == null) {
        return null;
      } else {
        try {
          f = superClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e1) {
          e1.printStackTrace();
          return null;
        }
      }
    }

    f.setAccessible(true);
    try {
      return f.get(this);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return null;
  }
}
