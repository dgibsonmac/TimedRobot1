package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnPid{

    private double _kp;
    private double _ki;
    private double _kd;
    private double _interval;
    private double _minTurnPower;
    private double _lastError;
    private double _accumulatedI;
    private boolean start;
    private double _deadband;
    private double _targetAngle;

    

    public TurnPid(double kp, double ki, double kd, double minTurnPower, double interval, double deadband){
        _kp = kp;
        _ki = ki;
        _kd = kd;
        _minTurnPower = minTurnPower;
        _interval = interval;
        _accumulatedI = 0;
        _deadband = deadband;
        start = true;
    }

    public void SetTargetAngle(double targetAngle){
        _targetAngle = targetAngle;
    }

    public double GetAnglePidOutput(double currentAngle) {

        if(start){

            SmartDashboard.putString("PidStatus", "Started New PidTurn Class");
        }
        double angle_error = currentAngle - _targetAngle; //calculate error
        angle_error = Math.abs(angle_error) > 180 ? 180 - angle_error : angle_error; //scale error to take shortest path
        if (_targetAngle == 0 && currentAngle > 180) {
                angle_error = currentAngle - 360;
        }
      
        double p_Angle = _kp * angle_error; //calculate p
        _accumulatedI += _ki * (angle_error * _interval); //calculate i
        double i_Angle = _ki*_accumulatedI;
        double d_Angle = 0;
        if (!start){
            d_Angle = _kd * ((angle_error - _lastError) / _interval); //calculate d
        }
        start = false;
        
        double angleOutput = p_Angle + i_Angle + d_Angle; //calculate output
        _lastError = angle_error; //set last angle error for d value
      
      
      
        angleOutput = Math.abs(angleOutput) < _minTurnPower ? Math.copySign(_minTurnPower, angleOutput) : angleOutput; //if angleOutput is below min, set to min
        angleOutput = Math.abs(angleOutput) > .9 ? Math.copySign(.9, angleOutput) : angleOutput; //if angleOutput is above max, set to max
        //angleOutput = angle_error < 0 ? angleOutput : -angleOutput;
        if (Math.abs(angle_error) < _deadband) { //if done moving
            i_Angle = 0;
            angleOutput = 0;
            SmartDashboard.putString("PidStatus", "PidTurn Class completed");
        }
        angleOutput = -angleOutput;
        
      
        return angleOutput;
      }




}