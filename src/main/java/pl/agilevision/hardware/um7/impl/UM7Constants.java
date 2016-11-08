package pl.agilevision.hardware.um7.impl;

/**
 * Interface contains all UM7-related constants splitted by their type
 */
public interface UM7Constants {

  /**
   * Commands
   */
  interface Commands {
    byte GET_FW_REVISION       = (byte)0xAA; //# (170)
    byte FLASH_COMMIT          = (byte)0xAB; //# (171)
    byte RESET_TO_FACTORY      = (byte)0xAC; //# (172)
    byte ZERO_GYROS            = (byte)0xAD; //# (173)
    byte SET_HOME_POSITION     = (byte)0xAE; //# (174)
    byte SET_MAG_REFERENCE     = (byte)0xB0; //# (176)
    byte RESET_EKF             = (byte)0xB3; //# (179)

  }

  /**
   * Registers
   */
  interface Registers {
    byte DREG_HEALTH           = (byte)0x55;
    byte DREG_GYRO_RAW_XY      = (byte)0x56;
    byte DREG_GYRO_PROC_X      = (byte)0x61;
    byte DREG_ACCEL_PROC_X     = (byte)0x65;
    byte DREG_EULER_PHI_THETA  = (byte)0x70;
    byte DREG_GYRO_BIAS_X      = (byte)0x89;

    byte CREG_COM_SETTINGS     = (byte)0x00;
    byte CREG_GYRO_TRIM_X      = (byte)0x0C;
    byte CREG_MAG_CAL1_1       = (byte)0x0F;
    byte CREG_MAG_BIAS_X       = (byte)0x18;

    short REG_HIDDEN            = (short) 0xF000;
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
