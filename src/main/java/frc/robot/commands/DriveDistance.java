// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import static frc.robot.Constants.AutoConstants.*;

public class DriveDistance extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final double m_dist;

  TrapezoidProfile.Constraints constraints = new TrapezoidProfile.Constraints(5, 10);

  ProfiledPIDController m_leftController = new ProfiledPIDController(1, 0, 0, constraints);
  ProfiledPIDController m_rightController = new ProfiledPIDController(1, 0, 0, constraints);

  /** Creates a new DriveDistance. */
  public DriveDistance(Drivetrain drivetrain, double dist) {
    m_drivetrain = drivetrain;
    m_dist = dist;
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.resetEncoders();

    m_leftController.setGoal(m_dist);
    m_rightController.setGoal(m_dist);

    m_leftController.setTolerance(kDriveControllerTolerance);
    m_rightController.setTolerance(kDriveControllerTolerance);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftPower = 0;
    double rightPower = 0;

    if (!m_leftController.atGoal()) 
      leftPower = m_leftController.calculate(m_drivetrain.getLeftDist());
    if (!m_rightController.atGoal())
      rightPower = m_rightController.calculate(m_drivetrain.getRightDist());

    m_drivetrain.setPower(leftPower, rightPower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_leftController.atGoal() && m_rightController.atGoal();
  }
}
