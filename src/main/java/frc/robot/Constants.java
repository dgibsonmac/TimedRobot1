
package frc.robot;

public class Constants{

    public static final int driveStick = 0;
    public static final int opStick = 1;

    public static final int leftMaster = 12;
    public static final int leftFollower = 13;
    public static final int rightMaster = 14;
    public static final int rightFollower = 15;


    private static final double kAngleSetpoint = 0.0;
    private static final double kP = 0.005; // propotional turning constant
    public static final double joystickDeadband = .05;
    public static double driveEncoderTicksPerInch = 2048/6/3.14;
    public static double pidTurnDeadband = 2;



    
    
}