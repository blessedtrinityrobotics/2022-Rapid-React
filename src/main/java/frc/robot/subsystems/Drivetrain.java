// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveConstants.*;

public class Drivetrain extends SubsystemBase {
  MotorControllerGroup m_left = new MotorControllerGroup(
    new VictorSP(kFrontLeftMotorPort), 
    new VictorSP(kBackLeftMotorPort));

  MotorControllerGroup m_right = new MotorControllerGroup(
    new VictorSP(kFrontRightMotorPort),
    new VictorSP(kBackRightMotorPort)
  );

  DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);
  
  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_right.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double speed, double direction) {
    m_drive.arcadeDrive(speed, direction);
  }
}
