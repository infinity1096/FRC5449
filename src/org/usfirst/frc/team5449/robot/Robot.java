package org.usfirst.frc.team5449.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TankAssist {

    public void operatorControl() {    	
    	init();
        while (isOperatorControl() && isEnabled()) { 
        	EncoderTest();
        	intake();//intake module
        	shooter();//shooter module
        	go();//Tank module
        	arm();
        	double range = ultra.getRangeInches();
        	SmartDashboard.putNumber("ultra data", range);
        }
    }
    
    public void autonomous() {
    	init();
    	while (isAutonomous() && isEnabled()){
        	pid_init();
    		AutoPID(-300,-500,300,500);    		
    	}
    }
    
    public void test() {
    	init();
    	while (isTest() && isEnabled()){
    		arm_l.set(0.1);
    		arm_r.set(0.1);
    		Timer.delay(1);
    		arm_l.set(0);
    		arm_r.set(0);
    	}
    }

}
