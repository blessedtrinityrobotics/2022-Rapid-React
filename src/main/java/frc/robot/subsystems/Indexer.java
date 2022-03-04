// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IndexConstants.*;

public class Indexer extends SubsystemBase {
  private final TalonSRX m_front = new TalonSRX(kVerticalFrontMotorId);
  private final TalonSRX m_back = new TalonSRX(kVerticalBackMotorId);

  /** Creates a new VerticalIndexer. */
  public Indexer() {
    m_back.follow(m_front);
    m_back.setInverted(InvertType.OpposeMaster);
  }

  public void up(double power) {
    m_front.set(ControlMode.PercentOutput, power);
  }

  public void back(double power) {

  }

}
