// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.ShuffleboardConstants;

import static frc.robot.Constants.ShooterConstants.*;
import static frc.robot.Constants.ShuffleboardConstants.*;

import java.util.Map;

public class Shooter extends SubsystemBase {
  private final TalonSRX m_masterMotor = new TalonSRX(kMasterMotorId);
  private final TalonSRX m_followerMotor = new TalonSRX(kFollowerMotorId);

  boolean joystickControl = true; 

  // In case we ever want a set shooter speed
  NetworkTableEntry m_percentOutput = 
    Shuffleboard.getTab(kDriveTab)
      .add("Shooter Speed", 0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();

  NetworkTableEntry m_power = 
    Shuffleboard.getTab(kDriveTab).
    add("Shooter Power", 0).
    withWidget(BuiltInWidgets.kDial).
    getEntry();

  NetworkTableEntry m_speed;
  NetworkTableEntry m_predictedSpeed;
 public Shooter() {
    m_speed = NetworkTableInstance.getDefault().getTable("Shooter").getEntry("Speed");
    m_predictedSpeed = NetworkTableInstance.getDefault().getTable("Shooter").getEntry("Predicted Speed");
    
    m_followerMotor.follow(m_masterMotor);
    
    m_followerMotor.setInverted(InvertType.OpposeMaster);
    m_masterMotor.setSensorPhase(true);

    loop.reset(VecBuilder.fill(m_masterMotor.getSelectedSensorVelocity()));
  }

  @Override
  public void periodic() {
    /**
     * TODO: Get the kalman filter sort of working and then tune it up
     * make sure to wire it up
     * good link: https://www.chiefdelphi.com/t/understanding-kalman-filter-behavior/401600
     */
    double velocity = encoderRadians(m_masterMotor.getSelectedSensorVelocity());
    m_speed.setDouble(velocity);
    // make our kalman filter predict the thing
    // m_observer.predict(VecBuilder.fill(m_masterMotor.getMotorOutputVoltage()), 0.020);
    // this graphs fine but the units or something seems to be off 
    m_predictedSpeed.setDouble(m_observer.getXhat(0));
    // This method will be called once per scheduler run

    /**
     * PREVIOUSLY, ON STATE SPACE CONTROL
     * pc was really laggy it made me angry
     * I tried getting the kalman filter to work, the graphs looked almost right except the predicted was a lot higher than the actual
     * It may be that I had the units wrong somewhere
     * I then tried to continue on making the filter, but I can't figure out how to set the voltage directly on a talon
     * PID might just be the play... 
     */
    if (!joystickControl)
      m_masterMotor.set(ControlMode.PercentOutput, m_percentOutput.getDouble(0));
  }

  public void toggleMode() {
    joystickControl = !joystickControl;
  }

  public void spin(double power) {
    if (joystickControl)
      m_masterMotor.set(ControlMode.PercentOutput, power);
  }

  public void stop() {
    m_masterMotor.set(ControlMode.PercentOutput, 0);
  }

  private double encoderRadians(double sensorCounts) {
    return sensorCounts / 4096.0 * 2.0 * Math.PI;
  }

}
