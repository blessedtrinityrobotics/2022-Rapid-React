// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  MotorControllerGroup m_left = new MotorControllerGroup(
    new VictorSP(3), 
    new VictorSP(2));

  MotorControllerGroup m_right = new MotorControllerGroup(
    new VictorSP(1),
    new VictorSP(0)
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
    m_drive.arcadeDrive(speed * 1, direction * 1);
  }
}
