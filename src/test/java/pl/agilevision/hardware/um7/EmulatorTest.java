package pl.agilevision.hardware.um7;

import org.junit.Test;
import pl.agilevision.hardware.um7.exceptions.DeviceConnectionException;
import pl.agilevision.hardware.um7.exceptions.OperationTimeoutException;
import pl.agilevision.hardware.um7.impl.DefaultUM7;
import pl.agilevision.hardware.um7.impl.DefaultUM7Client;

import java.io.IOException;

/**
 * Created by volodymyr on 07.11.16.
 */
public class EmulatorTest {

  @Test
  //@Ignore
  public void testConnection() throws DeviceConnectionException, OperationTimeoutException {

    String[] statevars = new String[] {
      "health", "roll", "pitch", "yaw", "mag_proc_x", "mag_proc_y", "mag_proc_z", "mag_raw_x", "mag_raw_y", "mag_raw_z",
      "accel_raw_x", "accel_raw_y", "accel_raw_z", "accel_proc_x", "accel_proc_y", "accel_proc_z", "gyro_proc_x", 
      "gyro_proc_y", "gyro_proc_z", "accel_raw_time", "accel_proc_time", "euler_time"
    };

    String head = "";
    String width = "%15s";
    for (String s: statevars) {
      head += String.format(width, s);
    }

    UM7Client um7cli = new DefaultUM7Client("um7", "COM1", 115200, 0.1f);
    UM7 um7 = new DefaultUM7(um7cli, new String[] {"roll", "pitch", "yaw"});
    System.out.println("GET_FW_REVISION="+ um7.getFirmwareVersion());
    boolean zg_ok = um7.zeroGyros();
    System.out.println("ZERO_GYROS " + (zg_ok ? "ok":"failed."));
    boolean rEkf_ok = um7.resetEkf();
    System.out.println("RESET_EKF  " + (rEkf_ok ? "ok":"failed."));

    boolean setR_ok;

//    setR_ok = um7cli.setDataRate(UM7Attributes.Health, UM7Attributes.Frequency.HealthRate.Freq1_HZ);
//    System.out.println("Set Health Rate " + (setR_ok ? "ok":"failed."));

    setR_ok = um7cli.setDataRate(UM7Attributes.NMEA.Health, UM7Attributes.Frequency.NMEA.Freq15_HZ);
    System.out.println("Set NMEA.Health Rate " + (setR_ok ? "ok":"failed."));

//    boolean setR_ok = um7cli.setDataRate(UM7Attributes.Accelerator.Raw, 7);
//    System.out.println("Set Accel Raw Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Gyro.Raw, 7);
//    System.out.println("Set Gyro Raw Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Magnetometer.Raw, 0xAA);
//    System.out.println("Set Mag Raw Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.AllRaw, 0xFF);
//    System.out.println("Set All Raw Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Temperature, 0xAA);
//    System.out.println("Set Temp Rate Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Accelerator.Processed, 0xAA);
//    System.out.println("Set Accel Proc Rate " + (setR_ok ? "ok":"failed."));

//    setR_ok = um7cli.setDataRate(UM7Attributes.Gyro.Processed, 0xAA);
//    System.out.println("Set Gyro Proc Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Magnetometer.Processed, 0xAA);
//    System.out.println("Set Mag Proc Rate " + (setR_ok ? "ok":"failed."));
//
    setR_ok = um7cli.setDataRate(UM7Attributes.AllProc, 0xAA);
    System.out.println("Set All proc Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Quat, 0xAA);
//    System.out.println("Set Quat Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Euler, 0xAA);
//    System.out.println("Set Euler Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Position, 0xAA);
//    System.out.println("Set Position Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Velocity, 0xAA);
//    System.out.println("Set Velocity Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.Pose, 0xAA);
//    System.out.println("Set Pose Rate " + (setR_ok ? "ok":"failed."));
//
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.GyroBias, 0xAA);
//    System.out.println("Set GyroBias Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.NMEA.Pose, UM7Attributes.Frequency.NMEA.Freq15_HZ);
//    System.out.println("Set NMEA.Pose Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.NMEA.Attitude, UM7Attributes.Frequency.NMEA.Freq15_HZ);
//    System.out.println("Set NMEA.Attitude Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.NMEA.Sensor, UM7Attributes.Frequency.NMEA.Freq15_HZ);
//    System.out.println("Set NMEA.Sensor Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.NMEA.Rates, UM7Attributes.Frequency.NMEA.Freq15_HZ);
//    System.out.println("Set NMEA.Rates Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.NMEA.GpsPose, UM7Attributes.Frequency.NMEA.Freq15_HZ);
//    System.out.println("Set NMEA.GpsPose Rate " + (setR_ok ? "ok":"failed."));
//
//    setR_ok = um7cli.setDataRate(UM7Attributes.NMEA.Quat, UM7Attributes.Frequency.NMEA.Freq15_HZ);
//    System.out.println("Set NMEA.Quat Rate " + (setR_ok ? "ok":"failed."));


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
              s += String.format(width, String.format("%8s", Integer.toBinaryString(um7.getState().getValue(v, Integer.TYPE))).replace(' ', '0'));
            } else {
              Object o = um7.getState().getValue(v);
              if (o instanceof Integer) {
                s += String.format(width, String.format("%4d", (int) o));
              } else if (o instanceof Float) {
                s += String.format(width, String.format("%1.3f", (float) o));
              } else {
                s += String.format(width, String.format("%1.3f", (double) o));
              }
            }
          } else {
            s += String.format(width, 0);
          }
        }
        System.out.println(s);

      }
    }

  }
}
