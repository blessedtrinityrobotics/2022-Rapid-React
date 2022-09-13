// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.PneumaticConstants.*;

public class IntakePistons extends SubsystemBase {
  Compressor m_compressor = new Compressor(PneumaticsModuleType.CTREPCM);
  DoubleSolenoid m_rightValve = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, kRightValuePorts[0], kRightValuePorts[1]);
  DoubleSolenoid m_leftValve = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, kLeftValvePorts[0], kLeftValvePorts[1]);

  boolean pistonToggle = false;

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Intake Down", pistonToggle);
  }
  
  public void toggle() {
    pistonToggle = !pistonToggle;
    if (pistonToggle == true) {
      m_rightValve.set(Value.kForward);
      m_leftValve.set(Value.kForward);
    } else {
      m_rightValve.set(Value.kReverse);
      m_leftValve.set(Value.kReverse);
    }
  }
  public void toggleCompressor() {
    if (m_compressor.enabled()) 
      m_compressor.disable();
    else
      m_compressor.enableDigital();
  }

  public void disableCompressor() {
    m_compressor.disable();
  }
}
