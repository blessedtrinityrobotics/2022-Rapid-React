// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Constants.ShuffleboardConstants.*;

import java.util.Map;

public class Intake extends SubsystemBase {
  private final VictorSPX m_motor = new VictorSPX(kIntakeMotorId);

  NetworkTableEntry m_power = 
    Shuffleboard.getTab(kDriveTab)
      .add("Intake Power", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();

  /** Creates a new Intake. */
  public Intake() {}

  public void suck() {
    m_motor.set(ControlMode.PercentOutput, m_power.getDouble(kDefaultPower));
  }

  public void stop() {
    m_motor.set(ControlMode.PercentOutput, 0);
  }


}
