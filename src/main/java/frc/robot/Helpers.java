package frc.robot;

public class Helpers{

    public static double  DeadbandJoystick(double value){
        double deadband = Constants.joystickDeadband;
        if (value >= deadband) 
        return value;
    
        /* Lower deadband */
        if (value <= -deadband)
            return value;
        
        /* Outside deadband */
        return 0;

    }

}