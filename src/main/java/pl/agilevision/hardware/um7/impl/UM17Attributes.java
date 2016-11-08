package pl.agilevision.hardware.um7.impl;

/**
 * Describes available UM7 properties
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public interface UM17Attributes {
  String Health = "health";
  String Roll = "roll";
  String Pitch = "pitch";
  String Yaw = "yaw";
  String RollRate = "roll_rate";
  String PitchRate = "pitch_rate";
  String YawRate = "yaw_rate";
  String EulerTime = "euler_time";
  String Temperature = "temp";


  interface  Gyro {
    interface  Processed {
      String X = "gyro_proc_x";
      String Y = "gyro_proc_y";
      String Z = "gyro_proc_z";
      String Time = "gyro_proc_time";
    }

    interface Raw {
      String X = "gyro_raw_x";
      String Y = "gyro_raw_y";
      String Z = "gyro_raw_z";
      String Time = "gyro_raw_time";
    }
  }

  interface Accelerator {
    interface  Processed {
      String X = "accel_proc_x";
      String Y = "accel_proc_y";
      String Z = "accel_proc_z";
      String Time = "accel_proc_time";
    }

    interface Raw {
      String X = "accel_raw_x";
      String Y = "accel_raw_y";
      String Z = "accel_raw_z";
      String Time = "accel_raw_time";
    }
  }

  interface Magnetometer {
    interface  Processed {
      String X = "mag_proc_x";
      String Y = "mag_proc_y";
      String Z = "mag_proc_z";
      String Time = "mag_proc_time";
    }

    interface Raw {
      String X = "mag_raw_x";
      String Y = "mag_raw_y";
      String Z = "mag_raw_z";
      String Time = "mag_raw_time";
    }
  }



}
