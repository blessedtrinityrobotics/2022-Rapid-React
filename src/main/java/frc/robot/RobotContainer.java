// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.ShuffleboardConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.ShootAuto;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HorizontalIndexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VerticalIndexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.IntakePistons;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
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
  private final VerticalIndexer m_verticalIndexer = new VerticalIndexer();
  private final Shooter m_shooter = new Shooter();
  private final Intake m_intake = new Intake();
  private final Joystick m_joystick = new Joystick(kDriverJoystickPort);
  private final Joystick m_altJoystick = new Joystick(kShooterJoystickPort);
  private final HorizontalIndexer m_horizontalIndexer = new HorizontalIndexer();
  //private final IntakePistons m_intakePistons = new IntakePistons();

  // state
  private double directionMultiplier = 1.0;

  NetworkTableEntry m_driveDist = Shuffleboard.getTab(ShuffleboardConstants.kAutoTab)
    .add("Auto Drive Distance (m)", AutoConstants.kDefaultDriveTime)
    .getEntry();

  NetworkTableEntry m_shotPower = Shuffleboard.getTab(ShuffleboardConstants.kAutoTab)
    .add("Auto Shooter Power (%)", AutoConstants.kDefaultShotPower)
    .getEntry();
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
      () -> -m_joystick.getY() * m_joystick.getZ(), 
      () -> m_joystick.getX(),
      m_drivetrain)
    );
    
    m_shooter.setDefaultCommand(new RunCommand(
      () -> {
        m_shooter.spin(-m_altJoystick.getY());
      }, m_shooter));

    Command indexUp = new InstantCommand(m_verticalIndexer::up, m_verticalIndexer);
    Command indexDown = new InstantCommand(m_verticalIndexer::down, m_verticalIndexer);
    Command indexStopVert = new InstantCommand(m_verticalIndexer::stop, m_verticalIndexer);
    Command indexIn = new InstantCommand(m_horizontalIndexer::into, m_horizontalIndexer);
    Command indexOut = new InstantCommand(m_horizontalIndexer::out, m_horizontalIndexer);
    Command indexStopHori = new InstantCommand(m_horizontalIndexer::stop, m_horizontalIndexer);
    Command index = new ParallelCommandGroup(
      new InstantCommand(m_verticalIndexer::up, m_verticalIndexer),
      new InstantCommand(m_horizontalIndexer::into, m_horizontalIndexer)
    );
    Command reverseIndex = new ParallelCommandGroup(
      new InstantCommand(m_verticalIndexer::down, m_verticalIndexer),
      new InstantCommand(m_horizontalIndexer::out, m_horizontalIndexer)
    );
    Command stopIndex = new ParallelCommandGroup(
      new InstantCommand(m_verticalIndexer::stop, m_verticalIndexer),
      new InstantCommand(m_horizontalIndexer::stop, m_horizontalIndexer)
    );

    // Driver Joystick binds
    new JoystickButton(m_joystick, kIndexButton)
      .whenPressed(index)
      .whenReleased(stopIndex);

    new JoystickButton(m_joystick, kReverseIndexButton)
      .whenPressed(reverseIndex)
      .whenReleased(stopIndex);

    new JoystickButton(m_joystick, kIntakeButton)
      .whenPressed(new InstantCommand(m_intake::suck, m_intake))
      .whenReleased(new InstantCommand(m_intake::stop, m_intake));

    new JoystickButton(m_joystick, kDriverHorizontalIndexInButton)
      .whenPressed(indexIn)
      .whenReleased(indexStopHori);
    
    new JoystickButton(m_joystick, kDriverHorizontalIndexOutButton)
      .whenPressed(indexOut)
      .whenReleased(indexStopHori);

    new JoystickButton(m_joystick, kDriverSwitchDirection)
      .whenPressed(new InstantCommand(() -> directionMultiplier *= -1.0, m_drivetrain));

    // Shooter joystick binds
    new JoystickButton(m_altJoystick, kIndexButton)
      .whenPressed(index)
      .whenReleased(stopIndex);

    new JoystickButton(m_altJoystick, kReverseIndexButton)
      .whenPressed(reverseIndex)
      .whenReleased(stopIndex);

    new JoystickButton(m_altJoystick, kIntakeButton)
      .whenPressed(new InstantCommand(m_intake::suck, m_intake))
      .whenReleased(new InstantCommand(m_intake::stop, m_intake));

    new JoystickButton(m_altJoystick, kShooterVerticalIndexUpButton)
      .whenPressed(indexUp)
      .whenReleased(indexStopVert);

    new JoystickButton(m_altJoystick, kShooterVerticalIndexDownButton)
      .whenPressed(indexDown)
      .whenReleased(indexStopVert);

    // new JoystickButton(m_altJoystick, 7)
    //   .whenPressed(new InstantCommand(m_intakePistons::toggle, m_intakePistons));
    // new JoystickButton(m_altJoystick, 10)
    //   .whenPressed(new InstantCommand(m_intakePistons::toggleCompressor, m_intakePistons));

    // new JoystickButton(m_joystick, 7)
    //   .whenPressed(new InstantCommand(m_intakePistons::toggle, m_intakePistons));
    // new JoystickButton(m_joystick, 10)
    //   .whenPressed(new InstantCommand(m_intakePistons::toggleCompressor, m_intakePistons));
    new JoystickButton(m_altJoystick, 9)
      .whenPressed(new InstantCommand(m_shooter::toggleMode, m_shooter));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Drive forward 1 meter
    return new ShootAuto(m_drivetrain, m_shooter, m_verticalIndexer, m_horizontalIndexer, m_driveDist.getDouble(AutoConstants.kDefaultDriveTime), m_shotPower.getDouble(AutoConstants.kDefaultShotPower));
  }
}
