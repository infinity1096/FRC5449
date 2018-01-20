package org.usfirst.frc.team5449.robot;

import org.usfirst.frc.team5449.robot.command.IntakeIn;
import org.usfirst.frc.team5449.robot.command.IntakeOut;
import org.usfirst.frc.team5449.robot.commandGroup.AutonomousGroup;
import org.usfirst.frc.team5449.robot.subsystems.Chassis_Mecanum;
import org.usfirst.frc.team5449.robot.subsystems.Holder;
import org.usfirst.frc.team5449.robot.subsystems.Intake;
import org.usfirst.frc.team5449.robot.subsystems.Lifter;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import sensors.EncoderModule;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
	//RobotDrive myRobot = new RobotDrive(0, 1);
	public static Robot r = new Robot();
	public static OI oi;
	public static Chassis_Mecanum chassis;
	public static Lifter lifter = new Lifter();
	public static Intake intake = new Intake();
	public static Holder holder = new Holder();
	public static EncoderModule encodermodule = new EncoderModule();
	//public static I2C i1;
	Command AutonomousCommand;
	
	@Override
	public void robotInit() {
		oi = new OI();
		chassis = new Chassis_Mecanum();
		//command
		AutonomousCommand = new AutonomousGroup();
	}
	@Override
	public void autonomousInit() {
		AutonomousCommand.start();
	}
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	@Override
	public void teleopInit() {
		AutonomousCommand.cancel();
	}
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();		
		SmartDashboard.putData(new IntakeIn());
		SmartDashboard.putData(new IntakeOut());
	}
	/*
	private void log(){
		//TODO TBD
	}*/
}
