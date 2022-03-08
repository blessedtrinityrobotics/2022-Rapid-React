// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.DriveDistance;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import static frc.robot.Constants.OIConstants.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Indexer m_indexer = new Indexer();
  private final Shooter m_shooter = new Shooter();
  private final Intake m_intake = new Intake();
  private final Joystick m_joystick = new Joystick(kDriverJoystickPort);
  private final Joystick m_altJoystick = new Joystick(kShooterJoystickPort);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_drivetrain.setDefaultCommand(new ArcadeDrive(
      () -> -m_joystick.getY(), 
      () -> m_joystick.getX(),
      m_drivetrain)
    );

    Command indexUp = new InstantCommand(m_indexer::up, m_indexer);
    Command indexDown = new InstantCommand(m_indexer::down, m_indexer);
    Command indexVertStop = new InstantCommand(m_indexer::stopVertical, m_indexer);
    Command indexIn = new InstantCommand(m_indexer::horizontalIn, m_indexer);
    Command indexOut = new InstantCommand(m_indexer::horizontalOut, m_indexer);
    Command indexHoriStop = new InstantCommand(m_indexer::stopHorizontal, m_indexer);
    // Command collectiveIndex = indexIn.alongWith(indexUp);
    // Command invCollectiveIndex = indexOut.alongWith(indexDown);
    // Command collectiveStopIndex = indexVertStop.alongWith(indexHoriStop);

    
    m_shooter.setDefaultCommand(new RunCommand(
      () -> m_shooter.spinRaw(m_altJoystick.getY()), m_shooter));

    new JoystickButton(m_joystick, kDriverIndexUpButton)
      .whenPressed(indexUp)
      .whenReleased(indexVertStop);

    new JoystickButton(m_joystick, kDriverIndexDownButton)
      .whenPressed(indexIn)
      .whenReleased(indexHoriStop);

    new JoystickButton(m_joystick, 1)
      .whenPressed(new InstantCommand(m_intake::suck, m_intake))
      .whenReleased(new InstantCommand(m_intake::stop, m_intake));

    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Drive forward 1 meter
    return new RunCommand(
      () -> m_drivetrain.tankDrive(0.75, 0.75), m_drivetrain)
      .withTimeout(5.0);
  }
}
