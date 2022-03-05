package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

/**
 * Basic Arcade Driving command, very similar to {@link TankDrive}
 */
public class ArcadeDrive extends CommandBase {
    
    private final Drivetrain m_drivetrain;

    private final DoubleSupplier m_left;
    private final  DoubleSupplier m_right;

    /**
     * Creates a new Tank Drive Command.
     * @param left Presumably the joystick to control left power
     * @param right Presumably the joystick to control right power
     * @param drivetrain DriveTrain subsystem for manipulation
     */
    public ArcadeDrive(DoubleSupplier left, DoubleSupplier right, Drivetrain drivetrain) {
        m_drivetrain = drivetrain;
        m_left = left;
        m_right = right;

        addRequirements(m_drivetrain);
    }

    @Override
    public void execute() {
        m_drivetrain.arcadeDrive(m_left.getAsDouble(), m_right.getAsDouble());
    }

    @Override
    public void end(boolean interrupted) {
        //Reset arcade drive
        m_drivetrain.arcadeDrive(0, 0);
    }

}
