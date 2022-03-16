// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HorizontalIndexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VerticalIndexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootAuto extends SequentialCommandGroup {
  public ShootAuto(Drivetrain drivetrain, Shooter shooter, VerticalIndexer vIndex, HorizontalIndexer hIndex, double driveTime, double shotPower) {
    addCommands(
      new InstantCommand(() -> shooter.spinRaw(shotPower), shooter),
      new WaitCommand(2.0),
      new InstantCommand(hIndex::into, hIndex),
      new InstantCommand(vIndex::up, vIndex),
      new WaitCommand(3.0),

      new InstantCommand(shooter::stop, shooter),
      new InstantCommand(hIndex::stop, hIndex),
      new InstantCommand(vIndex::stop, vIndex),

      new WaitCommand(0.5),

      new Drive(drivetrain, 0.5, 0.5)
        .withTimeout(driveTime)
    );
  }
}
