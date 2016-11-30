package pl.agilevision.hardware.um7;

/**
 * Interface contains all UM7-related constants splitted by their type
 */
public interface UM7Constants {

  /**
   * Commands
   */
  interface Commands {
    int GET_FW_REVISION       = 0xAA; //# (170) Causes the autopilot to respond with a packet containing the current firmware revision.
    int FLASH_COMMIT          = 0xAB; //# (171) Writes all current configuration settings to flash
    int RESET_TO_FACTORY      = 0xAC; //# (172) Reset all settings to factory defaults
    int ZERO_GYROS            = 0xAD; //# (173) Causes the rate gyro biases to be calibrated.
    int SET_HOME_POSITION     = 0xAE; //# (174) Sets the current GPS location as position (0,0)
    int SET_MAG_REFERENCE     = 0xB0; //# (176) Sets the magnetometer reference vector
    int RESET_EKF             = 0xB3; //# (179) Resets the EKF
  }


  /**
   * Registers
   */
  interface Registers {
    // Configuration Registers
    int CREG_COM_SETTINGS       = 0x00; // General communication settings
    int CREG_COM_RATES1         = 0x01; // (1) Broadcast rate settings
    int CREG_COM_RATES2         = 0x02; // (2) Broadcast rate settings
    int CREG_COM_RATES3         = 0x03; // (3) Broadcast rate settings
    int CREG_COM_RATES4         = 0x04; // (4) Broadcast rate settings
    int CREG_COM_RATES5         = 0x05; // (5) Broadcast rate settings
    int CREG_COM_RATES6         = 0x06; // (6) Broadcast rate settings
    int CREG_COM_RATES7         = 0x07; // (7) Broadcast rate settings
    int CREG_MISC_SETTINGS      = 0x08;
    int CREG_HOME_NORTH         = 0x09; // (9) GPS north position to consider position 0
    int CREG_HOME_EAST          = 0x0A; // (10) GPS east position to consider position 0
    int CREG_HOME_UP            = 0x0B; // (11)  GPS altitude to consider position 0
    int CREG_GYRO_TRIM_X        = 0x0C; // (12) Bias trim for x-axis rate gyro
    int CREG_GYRO_TRIM_Y        = 0x0D; // (13) Bias trim for y-axis rate gyro
    int CREG_GYRO_TRIM_Z        = 0x0E; // (14) Bias trim for z-axis rate gyro
    int CREG_MAG_CAL1_1         = 0x0F; // (15) Row 1, Column 1 of magnetometer calibration matrix
    int CREG_MAG_CAL1_2         = 0x10; // (16) Row 1, Column 2 of magnetometer calibration matrix
    int CREG_MAG_CAL1_3         = 0x11; // (17) Row 1, Column 3 of magnetometer calibration matrix
    int CREG_MAG_CAL2_1         = 0x12; // (18) Row 2, Column 1 of magnetometer calibration matrix
    int CREG_MAG_CAL2_2         = 0x13; // (19) Row 2, Column 2 of magnetometer calibration matrix
    int CREG_MAG_CAL2_3         = 0x14; // (20) Row 2, Column 3 of magnetometer calibration matrix
    int CREG_MAG_CAL3_1         = 0x15; // (21) Row 3, Column 1 of magnetometer calibration matrix
    int CREG_MAG_CAL3_2         = 0x16; // (22) Row 3, Column 2 of magnetometer calibration matrix
    int CREG_MAG_CAL3_3         = 0x17; // (23) Row 3, Column 3 of magnetometer calibration matrix
    int CREG_MAG_BIAS_X         = 0x18; // (24) Magnetometer X-axis bias
    int CREG_MAG_BIAS_Y         = 0x19; // (25) Magnetometer Y-axis bias
    int CREG_MAG_BIAS_Z         = 0x1A; // (26) Magnetometer Z-axis bias

    // Data Registers
    int DREG_HEALTH              = 0x55; // (85)  Contains information about the health and status of the UM7
    int DREG_GYRO_RAW_XY         = 0x56; // (86)  Raw X and Y rate gyro data
    int DREG_GYRO_RAW_Z          = 0x57; // (87) Raw Z rate gyro data
    int DREG_GYRO_TIME           = 0x58; // (88) Time at which rate gyro data was acquired
    int DREG_ACCEL_RAW_XY        = 0x59; // (89) Raw X and Y accelerometer data
    int DREG_ACCEL_RAW_Z         = 0x5A; // (90) Raw Z accelerometer data
    int DREG_ACCEL_TIME          = 0x5B; // (91) Time at which accelerometer data was acquired
    int DREG_MAG_RAW_XY          = 0x5C; // (92) Raw X and Y magnetometer data
    int DREG_MAG_RAW_Z           = 0x5D; // (93) Raw Z magnetometer data
    int DREG_MAG_RAW_TIME        = 0x5E; // (94) Time at which magnetometer data was acquired
    int DREG_TEMPERATURE         = 0x5F; // (95) Temperature data
    int DREG_TEMPERATURE_TIME    = 0x60; // (96) Time at which temperature data was acquired
    int DREG_GYRO_PROC_X         = 0x61; // (97) Processed x-axis rate gyro data
    int DREG_GYRO_PROC_Y         = 0x62; // (98) Processed y-axis rate gyro data
    int DREG_GYRO_PROC_Z         = 0x63; // (99)  Processed z-axis rate gyro data
    int DREG_GYRO_PROC_TIME      = 0x64; // (100) Time at which rate gyro data was acquired
    int DREG_ACCEL_PROC_X        = 0x65; // (101) Processed x-axis accel data
    int DREG_ACCEL_PROC_Y        = 0x66; // (102) Processed y-axis accel data
    int DREG_ACCEL_PROC_Z        = 0x67; // (103) Processed z-axis accel data
    int DREG_ACCEL_PROC_TIME     = 0x68; // (104) Time at which accelerometer data was acquired
    int DREG_MAG_PROC_X          = 0x69; // (105) Processed x-axis magnetometer data
    int DREG_MAG_PROC_Y          = 0x6A; // (106) Processed y-axis magnetometer data
    int DREG_MAG_PROC_Z          = 0x6B; // (107) Processed z-axis magnetometer data
    int DREG_MAG_PROC_TIME       = 0x6C; // (108) Time at which magnetometer data was acquired
    int DREG_EULER_PSI           = 0x71; // (113) Yaw angle
    int DREG_EULER_PHI_THETA_DOT = 0x72; // (114) Roll and pitch angle rates
    int DREG_EULER_PSI_DOT       = 0x73; // DREG_EULER_PSI_DOT
    int DREG_EULER_TIME          = 0x74; // (116) Time of computed Euler attitude and rates
    int DREG_POSITION_NORTH      = 0x75; // (117) North position in meters
    int DREG_POSITION_EAST       = 0x76; // (118) East position in meters
    int DREG_POSITION_UP         = 0x77; // (119) Altitude in meters
    int DREG_POSITION_TIME       = 0x78; // (120) Time of estimated position
    int DREG_VELOCITY_NORTH      = 0x79; // (121) North velocity
    int DREG_VELOCITY_EAST       = 0x7A; // (122) East velocity
    int DREG_VELOCITY_UP         = 0x7B; // (123) Altitude velocity
    int DREG_VELOCITY_TIME       = 0x7C; // (124) Time of velocity estimate
    int DREG_GPS_LATITUDE        = 0x7D; // (125) GPS latitude
    int DREG_GPS_LONGITUDE       = 0x7E; // (126) GPS longitude
    int DREG_GPS_ALTITUDE        = 0x7F; // (127) GPS altitude
    int DREG_GPS_COURSE          = 0x80; // DREG_GPS_COURSE
    int DREG_GPS_SPEED           = 0x81; // (129) GPS speed
    int DREG_GPS_TIME            = 0x82; // (130) GPS time (UTC time of day in seconds)
    int DREG_GPS_SAT_1_2         = 0x83; // DREG_GPS_SAT_1_2
    int DREG_GPS_SAT_3_4         = 0x84; // (132) GPS satellite information
    int DREG_GPS_SAT_5_6         = 0x85; // DREG_GPS_SAT_5_6
    int DREG_GPS_SAT_7_8         = 0x86; // (134) GPS satellite information
    int DREG_GPS_SAT_9_10        = 0x87; // (135) GPS satellite information
    int DREG_GPS_SAT_11_12       = 0x88; // (136) GPS satellite information
    int DREG_GYRO_BIAS_X         = 0x89; // (137) Gyro x-axis bias estimate
    int DREG_GYRO_BIAS_Y         = 0x8A; // (138) Gyro y-axis bias estimate
    int DREG_GYRO_BIAS_Z         = 0x8B; // (139) Gyro z-axis bias estimate
    int DREG_QUAT_AB             = 0x6D; //(109) Quaternion elements A and B
    int DREG_QUAT_CD             = 0x6E; //(110)  Quaternion elements C and D
    int DREG_QUAT_TIME           = 0x6F; // (111) Time at which the sensor was at the specified quaternion rotation
    int DREG_EULER_PHI_THETA     = 0x70; // (112)  Roll and pitch angles

    // Hidden registers
    int REG_HIDDEN            = 0xF000;
    int H_CREG_GYRO_ALIGN1_1  = REG_HIDDEN | (byte)0x31;
    int H_CREG_ACCEL_ALIGN1_1 = REG_HIDDEN | (byte)0x52;
    int H_CREG_MAG_ALIGN1_1   = REG_HIDDEN | (byte)0x73;
    int H_CREG_MAG_REF        = REG_HIDDEN | (byte)0x7C;
  }

  /**
   * Health
   */
  interface Health {
    int HEALTH_GPS   = 0x1;
    int HEALTH_MAG   = 0x2;
    int HEALTH_GYRO  = 0x4;
    int HEALTH_ACCEL = 0x8;
    int HEALTH_ACC_N = 0x10;
    int HEALTH_MG_N  = 0x20;
    int HEALTH_RES6  = 0x40;
    int HEALTH_RES7  = 0x80;
    int HEALTH_OVF   = 0x100;
  }

  interface Defaults {
    int BAUD_RATE = 115200;
    float OPERATION_TIMEOUT_IN_SECONDS = 0.1f;
    long READ_DELAY_IN_NANOSECONDS = 10;
  }
}
