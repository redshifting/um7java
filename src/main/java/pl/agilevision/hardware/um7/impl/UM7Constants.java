package pl.agilevision.hardware.um7.impl;

/**
 * Interface contains all UM7-related constants splitted by their type
 */
public interface UM7Constants {

  /**
   * Commands
   */
  interface Commands {
    int GET_FW_REVISION       = 0xAA; //# (170)
    int FLASH_COMMIT          = 0xAB; //# (171)
    int RESET_TO_FACTORY      = 0xAC; //# (172)
    int ZERO_GYROS            = 0xAD; //# (173)
    int SET_HOME_POSITION     = 0xAE; //# (174)
    int SET_MAG_REFERENCE     = 0xB0; //# (176)
    int RESET_EKF             = 0xB3; //# (179)

  }

  /**
   * Registers
   */
  interface Registers {
    int DREG_HEALTH           = 0x55;
    int DREG_GYRO_RAW_XY      = 0x56;
    int DREG_GYRO_PROC_X      = 0x61;
    int DREG_ACCEL_PROC_X     = 0x65;
    int DREG_EULER_PHI_THETA  = 0x70;
    int DREG_GYRO_BIAS_X      = 0x89;

    int CREG_COM_SETTINGS     = 0x00;
    int CREG_GYRO_TRIM_X      = 0x0C;
    int CREG_MAG_CAL1_1       = 0x0F;
    int CREG_MAG_BIAS_X       = 0x18;

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
    float OPERATION_TIMEOUT = 0.1f;
  }
}
