// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IndexConstants.*;
import static frc.robot.Constants.ShuffleboardConstants.*;

import java.util.Map;

public class VerticalIndexer extends SubsystemBase {
  private final VictorSPX m_front = new VictorSPX(kVerticalFrontMotorId);
  private final VictorSPX m_back = new VictorSPX(kVerticalBackMotorId);

  NetworkTableEntry m_power = 
    Shuffleboard.getTab(kDriveTab)
      .add("Vertical Indexer Power", kDefaultPower)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();

  /** Creates a new VerticalIndexer. */
  public VerticalIndexer() {
    m_back.follow(m_front);
    m_back.setInverted(InvertType.OpposeMaster);
  }

  public void up() {
    m_front.set(ControlMode.PercentOutput, m_power.getDouble(kDefaultPower));
  }

  public void down() {
    m_front.set(ControlMode.PercentOutput, -m_power.getDouble(kDefaultPower));
  }

  public void stop() {
    m_front.set(ControlMode.PercentOutput, 0);
  }
}
