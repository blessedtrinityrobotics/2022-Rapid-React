package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import static frc.robot.Constants.IndexConstants.*;
import static frc.robot.Constants.ShuffleboardConstants.*;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HorizontalIndexer extends SubsystemBase {
    private final VictorSPX m_motor = new VictorSPX(kHorizontalMotorId);

    NetworkTableEntry m_power = Shuffleboard.getTab(kDriveTab)
      .add("Horizontal Power", kDefaultHoriPower)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();

    public void into() {
        m_motor.set(ControlMode.PercentOutput, m_power.getDouble(kDefaultHoriPower));
    }

    public void out() {
        m_motor.set(ControlMode.PercentOutput, -m_power.getDouble(kDefaultHoriPower));
    }

    public void stop() {
        m_motor.set(ControlMode.PercentOutput, 0);
    }
}