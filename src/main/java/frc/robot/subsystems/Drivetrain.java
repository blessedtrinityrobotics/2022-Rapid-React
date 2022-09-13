// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveConstants.*;
import static frc.robot.Constants.ShuffleboardConstants.*;

public class Drivetrain extends SubsystemBase {
  MotorControllerGroup m_left = new MotorControllerGroup(
    new VictorSP(kFrontLeftMotorPort), 
    new VictorSP(kBackLeftMotorPort)
  );

  MotorControllerGroup m_right = new MotorControllerGroup(
    new VictorSP(kFrontRightMotorPort),
    new VictorSP(kBackRightMotorPort)
  );

  Encoder m_leftEncoder = new Encoder(kLeftEncoder[0], kLeftEncoder[1]); // placeholder values
  Encoder m_rightEncoder = new Encoder(kRightEncoder[0], kRightEncoder[1]);

  DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);
  
  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_left.setInverted(true);

    m_rightEncoder.setReverseDirection(true);
    m_leftEncoder.setDistancePerPulse(kDistancePerPulse);
    m_rightEncoder.setDistancePerPulse(kDistancePerPulse);

  }
  
  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  public double getLeftDist() {
    return m_leftEncoder.getDistance();
  }

  public double getRightDist() {
    return m_rightEncoder.getDistance();
  }

  public void arcadeDrive(double speed, double direction) {
    m_drive.arcadeDrive(speed, direction);
  }

  public void tankDrive(double left, double right) {
    m_drive.tankDrive(left, right);
  }

  public void setPower(double leftPower, double rightPower) {
    m_left.set(leftPower);
    m_right.set(rightPower);
  }
}
