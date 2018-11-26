package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DistancePid{

    private double _kp;
    private double _ki;
    private double _kd;
    private double _interval;
    private double _minMovePower;
    private double _lastError;
    private double _accumulatedI;
    private boolean start;
    private double _deadband;
    private double _targetDistance;
    private double _startEncoderValue;

    

    public DistancePid(double kp, double ki, double kd, double minMovePower, double interval, double deadband){
        _kp = kp;
        _ki = ki;
        _kd = kd;
        _minMovePower = minMovePower;
        _interval = interval;
        _accumulatedI = 0;
        _deadband = deadband;

        start = true;
    }

    public void SetTargetDistance(double targetAngle, double startEncoder){
        _targetDistance = targetAngle;
        _startEncoderValue = startEncoder;
    }

    public double GeDistancePidOutput(double currentEncoder) {

        if(start){

            SmartDashboard.putString("PidStatus", "Started New PidDistance Class");
        }
        double distanceTraveled = (currentEncoder - _startEncoderValue)/Constants.driveEncoderTicksPerInch;
        double distError = distanceTraveled - _targetDistance; //calculate error
        distError = Math.abs(distError) > 180 ? 180 - distError : distError; //scale error to take shortest path
        
        double p_Dist = _kp * distError; //calculate p
        _accumulatedI += _ki * (distError * _interval); //calculate i
        double i_Dist = _ki*_accumulatedI;
        double d_Dist = 0;
        if (!start){
            d_Dist = _kd * ((distError - _lastError) / _interval); //calculate d
        }
        start = false;
        
        double distOutput = p_Dist + i_Dist + d_Dist; //calculate output
        _lastError = distError; //set last angle error for d value
      
      
      
        distOutput = Math.abs(distOutput) < _minMovePower ? Math.copySign(_minMovePower, distOutput) : distOutput; //if Distance Output is below min, set to min
        distOutput = Math.abs(distOutput) > .9 ? Math.copySign(.9, distOutput) : distOutput; //if Distance Output is above max, set to max
        
        if (Math.abs(distError) < _deadband) { //if done moving
            i_Dist = 0;
            distOutput = 0;
            SmartDashboard.putString("PidStatus", "PidDistance Class completed");
        }
        
        
      
        return distOutput;
      }




}