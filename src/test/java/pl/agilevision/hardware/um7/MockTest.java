package pl.agilevision.hardware.um7;

import org.junit.Test;
import pl.agilevision.hardware.um7.data.UMDataSample;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;
import pl.agilevision.hardware.um7.impl.DefaultUM7;
import pl.agilevision.hardware.um7.impl.DefaultUM7Client;

import java.io.IOException;
import java.text.Format;
import java.util.Map;

/**
 * Created by volodymyr on 07.11.16.
 */
public class MockTest {

  @Test
  public void testMock() throws DeviceConnectionException, OperationTimeoutException {

    String[] statevars = new String[] {
      "health", "roll", "pitch", "yaw", "mag_proc_x", "mag_proc_y", "mag_proc_z", "mag_raw_x", "mag_raw_y", "mag_raw_z",
      "accel_raw_x", "accel_raw_y", "accel_raw_z", "accel_proc_x", "accel_proc_y", "accel_proc_z", "gyro_proc_x", 
      "gyro_proc_y", "gyro_proc_z", "accel_raw_time", "accel_proc_time", "euler_time"
    };

    String head = "";
    for (String s: statevars) {
      head += String.format("%10s ", s);
    }

    UM7Client um7cli = new DefaultUM7Client("um7", "COM1", 115200);
    UM7 um7 = new DefaultUM7(um7cli, new String[] {"roll", "pitch", "yaw"});
    System.out.println("GET_FW_REVISION="+ um7.getFirmwareVersion());
    boolean zg_ok = um7.zeroGyros();
    System.out.println("ZERO_GYROS " + (zg_ok ? "ok":"failed."));
    boolean rEkf_ok = um7.resetEkf();
    System.out.println("RESET_EKF  " + (rEkf_ok ? "ok":"failed."));

    int c = 0;
    System.out.println(head);
    while (true) {
      //um7.catchallsamples(sv, 1.0)
      boolean all = false;
      try {
        um7.catchAllSamples(statevars, 1.0f);
      } catch (IOException e) {
        e.printStackTrace();
      }

      if (c % 100 == 0) {
        String s = "";
        for (String v : statevars) {
          if (um7.getState().hasValue(v)) {
            if (v.equals("health")) {
              s += String.format("%10s", String.format("%8s", Integer.toBinaryString(um7.getState().getValue(v, Integer.TYPE))).replace(' ', '0'));
            } else {
              Object o = um7.getState().getUntypedValue(v);
              if (o instanceof Integer) {
                s += String.format("%10s", String.format("%4d", (int) o));
              } else if (o instanceof Float) {
                s += String.format("%10s", String.format("%1.3f", (float) o));
              } else {
                s += String.format("%10s", String.format("%1.3f", (double) o));
              }
            }
          } else {
            s += String.format("%10d", 0);
          }
        }
        System.out.println(s);

      }
    }
//    s1
//    if c % 100 == 0:
//    print(hs.format(*statevars))
//    print(fs.format(s1.state))
//    c += 1

  }
}
