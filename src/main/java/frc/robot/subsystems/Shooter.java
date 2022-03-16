// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShuffleboardConstants;

import static frc.robot.Constants.ShooterConstants.*;
import static frc.robot.Constants.ShuffleboardConstants.*;

import java.util.Map;

public class Shooter extends SubsystemBase {
  private final VictorSPX m_masterMotor = new VictorSPX(kMasterMotorId);
  private final VictorSPX m_followerMotor = new VictorSPX(kFollowerMotorId);

  // In case we ever want a set shooter speed
  // NetworkTableEntry m_percentOutput = 
  //   Shuffleboard.getTab(kDriveTab)
  //     .add("Shooter Speed", 1)
  //     .withWidget(BuiltInWidgets.kNumberSlider)
  //     .withProperties(Map.of("min", 0, "max", 1))
  //     .getEntry();

  NetworkTableEntry m_power = 
    Shuffleboard.getTab(kDriveTab).
    add("Shooter Power", 0).
    withWidget(BuiltInWidgets.kDial).
    getEntry();
  /** Creates a new Shooter. */
  public Shooter() {
    m_followerMotor.follow(m_masterMotor);

    m_followerMotor.setInverted(InvertType.OpposeMaster);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void spinRaw(double power) {
    m_power.setDouble(-power * 100);
    m_masterMotor.set(ControlMode.PercentOutput, power);
  }

  public void stop() {
    m_masterMotor.set(ControlMode.PercentOutput, 0);
  }

}
