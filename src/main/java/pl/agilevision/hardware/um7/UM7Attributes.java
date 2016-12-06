package pl.agilevision.hardware.um7;

import pl.agilevision.hardware.um7.data.attributes.*;
import pl.agilevision.hardware.um7.data.attributes.Temperature;
import pl.agilevision.hardware.um7.data.attributes.Quat;
import pl.agilevision.hardware.um7.data.attributes.Euler;
import pl.agilevision.hardware.um7.data.attributes.Health;
import pl.agilevision.hardware.um7.data.attributes.nmea.*;

/**
 * Describes available UM7 properties
 * @author Ivan Borschov (iborschov@agilevision.pl)
 * @author Volodymyr Rudyi (volodymyr@agilevision.pl)
 */
public interface UM7Attributes {

  //Attributes with configurable rates
  interface Gyro {
    GyroProcessed Processed = new GyroProcessed(UM7Constants.Registers.CREG_COM_RATES3, "PROC_GYRO_RATE", 16);
    GyroRaw Raw = new GyroRaw(UM7Constants.Registers.CREG_COM_RATES1, "RAW_GYRO_RATE", 16);
  }

  interface Accelerator {
    AcceleratorProcessed Processed = new AcceleratorProcessed(UM7Constants.Registers.CREG_COM_RATES3, "PROC_ACCEL_RATE", 24);
    AcceleratorRaw Raw = new AcceleratorRaw(UM7Constants.Registers.CREG_COM_RATES1, "RAW_ACCEL_RATE", 24);
  }

  interface Magnetometer {
    MagnetometerProcessed Processed = new MagnetometerProcessed(UM7Constants.Registers.CREG_COM_RATES3, "PROC_MAG_RATE", 8);
    MagnetometerRaw Raw = new MagnetometerRaw(UM7Constants.Registers.CREG_COM_RATES1, "RAW_MAG_RATE", 8);
  }

  Temperature Temperature = new Temperature(UM7Constants.Registers.CREG_COM_RATES2, "TEMP_RATE", 24);
  Quat Quat = new Quat(UM7Constants.Registers.CREG_COM_RATES5, "QUAT_RATE", 24);
  Euler Euler = new Euler(UM7Constants.Registers.CREG_COM_RATES5, "EULER_RATE", 16);
  Position Position = new Position(UM7Constants.Registers.CREG_COM_RATES5, "POSITION_RATE", 8);
  Velocity Velocity = new Velocity(UM7Constants.Registers.CREG_COM_RATES5, "VELOCITY_RATE", 0);
  Health Health = new Health(UM7Constants.Registers.CREG_COM_RATES6, "HEALTH_RATE", 16, 4);
  GyroBias GyroBias = new GyroBias(UM7Constants.Registers.CREG_COM_RATES6, "GYRO_BIAS_RATE", 8);

  interface NMEA {
    NmeaHealth Health = new NmeaHealth(UM7Constants.Registers.CREG_COM_RATES7, "NMEA HEALTH_RATE", 28,  4);
    NmeaPose Pose = new NmeaPose(UM7Constants.Registers.CREG_COM_RATES7, "NMEA POSE_RATE", 24, 4);
    NmeaAttitude Attitude = new NmeaAttitude(UM7Constants.Registers.CREG_COM_RATES7, "NMEA ATTITUDE_RATE", 20, 4);
    NmeaSensor Sensor = new NmeaSensor(UM7Constants.Registers.CREG_COM_RATES7, "NMEA SENSOR_RATE", 16, 4);
    NmeaRate Rates = new NmeaRate(UM7Constants.Registers.CREG_COM_RATES7, "NMEA RATES_RATE", 12, 4);
    NmeaGpsPose GpsPose = new NmeaGpsPose(UM7Constants.Registers.CREG_COM_RATES7, "NMEA GPS_POSE_RATE", 8, 4);
    NmeaQuaternion Quaternion = new NmeaQuaternion(UM7Constants.Registers.CREG_COM_RATES7, "NMEA QUAT_RATE", 4, 4);
  }

  // Rates that re-defines batch groups
  ConfigurableRateAttribute AllRaw = new ConfigurableRateAttribute(
    UM7Constants.Registers.CREG_COM_RATES2, "ALL_RAW_RATE", 0);
  ConfigurableRateAttribute AllProc = new ConfigurableRateAttribute(
    UM7Constants.Registers.CREG_COM_RATES4, "ALL_PROC_RATE", 0);
  ConfigurableRateAttribute Pose = new ConfigurableRateAttribute(
    UM7Constants.Registers.CREG_COM_RATES6, "POSE_RATE", 24);

  // attributes with only ON/OFF rates
  Gps Gps = new Gps(
    UM7Constants.Registers.CREG_COM_SETTINGS, "GPS", 8, 1);

  GpsSateliteDetails GpsSateliteDetails = new GpsSateliteDetails(
    UM7Constants.Registers.CREG_COM_SETTINGS, "SAT", 4, 1);

  interface Frequency {
    interface Gps {
      int Off = 0; // Off
      int On = 1; // On
    }
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
