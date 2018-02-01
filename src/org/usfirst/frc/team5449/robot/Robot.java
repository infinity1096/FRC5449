package org.usfirst.frc.team5449.robot;

import org.usfirst.frc.team5449.robot.command.CompressorOff;
import org.usfirst.frc.team5449.robot.command.CompressorOn;
import org.usfirst.frc.team5449.robot.command.DriveDistance;
import org.usfirst.frc.team5449.robot.command.DriveTo;
import org.usfirst.frc.team5449.robot.command.IntakeIn;
import org.usfirst.frc.team5449.robot.command.IntakeIn2;
import org.usfirst.frc.team5449.robot.command.IntakeOut;
import org.usfirst.frc.team5449.robot.command.Intake_Release;
import org.usfirst.frc.team5449.robot.command.LifterStop;
import org.usfirst.frc.team5449.robot.command.LifterToDown;
import org.usfirst.frc.team5449.robot.command.LifterToMid;
import org.usfirst.frc.team5449.robot.command.LifterToUp;
import org.usfirst.frc.team5449.robot.command.Release_Cube;
import org.usfirst.frc.team5449.robot.command.TurnTo;
import org.usfirst.frc.team5449.robot.commandGroup.AutonomousGroup;
import org.usfirst.frc.team5449.robot.subsystems.Camera;
import org.usfirst.frc.team5449.robot.subsystems.Chassis;
import org.usfirst.frc.team5449.robot.subsystems.Climber;
import org.usfirst.frc.team5449.robot.subsystems.Holder;
import org.usfirst.frc.team5449.robot.subsystems.Intake;
import org.usfirst.frc.team5449.robot.subsystems.Lifter;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import pathfinding.Simulator;
import sensors.EncoderModule;
import sensors.Gyro;


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
	public static Chassis chassis;
	public static Climber climber = new Climber();
	public static Lifter lifter = new Lifter();
	public static Intake intake = new Intake();
	public static Holder holder = new Holder();
	public static EncoderModule encodermodule = new EncoderModule();
	public static CameraServer server = CameraServer.getInstance();
	public static UsbCamera c1 = new UsbCamera("USB Camera 0",0);
	private static int working = 0;
	public static ADXRS450_Gyro g1 = new ADXRS450_Gyro();
    //Autonomous 
	Command AutonomousCommand;
	//Parameters
	@Override
	public void robotInit() {
		g1.reset();
		g1.calibrate();
		c1.setResolution(960, 540);
		c1.setFPS(24);
		server.startAutomaticCapture(c1);
		oi = new OI();
		chassis = new Chassis();
		//command
		AutonomousCommand = new AutonomousGroup();
		Scheduler.getInstance().removeAll();
	}
	@Override
	public void autonomousInit() {
		Scheduler.getInstance().removeAll();
		Timer timer = new Timer();
		//AutonomousCommand.start();
		timer.reset();
		timer.start();
		double[] start = {4,0.8};
		double[] end = {4,10.6};
		Simulator simulator = new Simulator(start,end,1);
		simulator.Simulate();
		timer.stop();
		SmartDashboard.putNumber("Caculation time:", timer.get());
		working = 0;
	}
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	@Override
	public void teleopInit() {
		
		
		Scheduler.getInstance().removeAll();
		AutonomousCommand.cancel();
		encodermodule.reset();
		Gyro.reset();
		Gyro.set_offset(-Gyro.getAngle());
		lifter.ResetEncoders();
		this.chassis.reset();
		working = 0;
		
	}
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putData(new CompressorOn());
		SmartDashboard.putData(new CompressorOff());
		SmartDashboard.putData(new TurnTo(90));
		SmartDashboard.putData(new DriveDistance(4));
		SmartDashboard.putNumber("enc_l",Robot.chassis.get()[0]);
		SmartDashboard.putNumber("enc_r",Robot.chassis.get()[1]);
		SmartDashboard.putData(new DriveDistance(4));
		double[] goal = {1,1};
		SmartDashboard.putData("DRIVE TO", new DriveTo(goal,true));
		SmartDashboard.putNumber("Heading", Gyro.getAngle());
		SmartDashboard.putNumber("X",this.encodermodule.getX() * 0.10);
		SmartDashboard.putNumber("Y",this.encodermodule.getY() * 0.10);
		SmartDashboard.putNumber("working",working);
		SmartDashboard.putNumber("AnalogGyro Data",g1.getAngle());
		if (this.holder.is_holding_block()){
			SmartDashboard.putNumber("BLOCK?",100);
		}else{
			SmartDashboard.putNumber("BLOCK?",0);
		}
		working ++;
		Scheduler.getInstance().run();
	}
	@Override
	public void disabledInit(){
		Scheduler.getInstance().removeAll();
	}
	/*
	 * 
	private void log(){
		//TODO TBD
	}*/
}
