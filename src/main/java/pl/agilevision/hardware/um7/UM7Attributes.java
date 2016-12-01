package pl.agilevision.hardware.um7;

import pl.agilevision.hardware.um7.data.*;

/**
 * Describes available UM7 properties
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public interface UM7Attributes {
  String Health = "health";
  String Roll = "roll";
  String Pitch = "pitch";
  String Yaw = "yaw";
  String RollRate = "roll_rate";
  String PitchRate = "pitch_rate";
  String YawRate = "yaw_rate";
  String EulerTime = "euler_time";
  String Temperature = "temp";

  interface Gyro {
    GyroProcessed Processed = new GyroProcessed(UM7Constants.Registers.CREG_COM_RATES3, 16);
    GyroRaw Raw = new GyroRaw(UM7Constants.Registers.CREG_COM_RATES1, 16);
  }

  interface Accelerator {
    AcceleratorProcessed Processed = new AcceleratorProcessed(UM7Constants.Registers.CREG_COM_RATES3, 24);
    AcceleratorRaw Raw = new AcceleratorRaw(UM7Constants.Registers.CREG_COM_RATES1, 24);
  }

  interface Magnetometer {
    MagnetometerProcessed Processed = new MagnetometerProcessed(UM7Constants.Registers.CREG_COM_RATES3, 8);
    MagnetometerRaw Raw = new MagnetometerRaw(UM7Constants.Registers.CREG_COM_RATES1, 8);
  }

  BaseAttribute AllRaw = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES2, 0);

  //todo mb need to merge with Temperature String constant above
  BaseAttribute TemperatureRate = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES2, 24);

  BaseAttribute AllProc = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES4, 0);

  BaseAttribute Quat = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES5, 24);
  BaseAttribute Euler = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES5, 16);
  BaseAttribute Position = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES5, 8);
  BaseAttribute Velocity = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES5, 0);

  BaseAttribute Pose = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES6, 24);

  // todo mb need to merge with Health String constant above
  BaseAttribute HealthRate = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES6, 16, 4);
  BaseAttribute GyroBias = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES6, 8);

  interface NMEA {
    BaseAttribute Health = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES7, 28, 4);
    BaseAttribute Pose = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES7, 24, 4);
    BaseAttribute Attitude = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES7, 20, 4);
    BaseAttribute Sensor = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES7, 16, 4);
    BaseAttribute Rates = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES7, 12, 4);
    BaseAttribute GpsPose = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES7, 8, 4);
    BaseAttribute Quat = new BaseAttribute(UM7Constants.Registers.CREG_COM_RATES7, 4, 4);
  }



  interface Frequency {
    interface HealthRate {
      int FreqOFF = 0; // Off
      int Freq0_125_HZ = 1; // 0.125 Hz
      int Freq0_25_HZ = 2; // 0.25 Hz
      int Freq0_5_HZ = 3; // 0.5 Hz
      int Freq1_HZ = 4; // 1 Hz
      int Freq2_HZ = 5; // 2 Hz
      int Freq4_HZ = 6; // 4 Hz
    }
    interface NMEA {
      int FreqOFF = 0; // Off
      int Freq1_HZ = 1; // 1 Hz
      int Freq2_HZ = 2; // 2 Hz
      int Frrq4_HZ = 3; // 4 Hz
      int Freq5_HZ = 4; // 5 Hz
      int Freq10_HZ = 5; // 10 Hz
      int Freq15_HZ = 6; // 15 Hz
      int Freq20_HZ = 7; // 20 Hz
      int Freq30_HZ = 8; // 30 Hz
      int Freq40_HZ = 9; // 40 Hz
      int Freq50_HZ = 10; // 50 Hz
      int Freq60_HZ = 11; // 60 Hz
      int Freq70_HZ = 12; // 70 Hz
      int Freq80_HZ = 13; // 80 Hz
      int Freq90_HZ = 14; // 90 Hz
      int Freq100_HZ = 15; // 100 Hz
    }
  }

}
