package org.usfirst.frc.team5449.robot.command;

import org.usfirst.frc.team5449.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive extends Command {
	@Deprecated
    public Drive() {
        // Use requires() here to declare subsystem dependencies
        

    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//TODO set whatever (idk)
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//TODO
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
