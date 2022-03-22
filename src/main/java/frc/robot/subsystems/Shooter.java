// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShuffleboardConstants;

import static frc.robot.Constants.ShooterConstants.*;
import static frc.robot.Constants.ShuffleboardConstants.*;

import java.util.Map;

public class Shooter extends SubsystemBase {
  private final TalonSRX m_masterMotor = new TalonSRX(kMasterMotorId);
  private final TalonSRX m_followerMotor = new TalonSRX(kFollowerMotorId);

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
    m_masterMotor.setSensorPhase(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
      System.out.println("Sensor Vel:" + m_masterMotor.getSelectedSensorVelocity());
      System.out.println("Sensor Pos:" + m_masterMotor.getSelectedSensorPosition());
      System.out.println("Out %" + m_masterMotor.getMotorOutputPercent());
  }

  public void spinRaw(double power) {
    m_masterMotor.set(ControlMode.PercentOutput, power);
  }

  public void stop() {
    m_masterMotor.set(ControlMode.PercentOutput, 0);
  }

}
