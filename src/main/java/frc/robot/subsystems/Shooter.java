// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private final VictorSPX m_masterMotor = new VictorSPX(14);
  private final VictorSPX m_followerMotor = new VictorSPX(15);

  /** Creates a new Shooter. */
  public Shooter() {
    m_followerMotor.follow(m_masterMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void spin(Double power) {
    m_masterMotor.set(ControlMode.PercentOutput, power);
  }
}
