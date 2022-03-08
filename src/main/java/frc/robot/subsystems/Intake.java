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
import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Constants.ShuffleboardConstants.*;

import java.util.Map;

public class Intake extends SubsystemBase {
  private final TalonSRX m_motor = new TalonSRX(kIntakeMotorId);

  NetworkTableEntry m_power = 
    Shuffleboard.getTab(kDriveTab)
      .add("Intake Power", kDefaultPower)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();

  /** Creates a new Intake. */
  public Intake() {
    m_motor.setInverted(InvertType.InvertMotorOutput);
  }

  public void suck() {
    m_motor.set(ControlMode.PercentOutput, m_power.getDouble(kDefaultPower));
  }

  public void stop() {
    m_motor.set(ControlMode.PercentOutput, 0);
  }


}
