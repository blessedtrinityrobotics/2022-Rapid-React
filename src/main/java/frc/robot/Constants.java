// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.LinearQuadraticRegulator;
import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.LinearSystemLoop;
import edu.wpi.first.math.system.plant.LinearSystemId;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int kFrontLeftMotorPort = 3;
        public static final int kBackLeftMotorPort = 2;
        public static final int kFrontRightMotorPort = 1;
        public static final int kBackRightMotorPort = 0;
        public static final int[] kLeftEncoder = {2, 3};
        public static final int[] kRightEncoder = {0, 1};
        public static final double kWheelDiameter = 0.1524; // Wheels are 6 in -> to meters
        public static final double kEncoderCPR = 4096;
        public static final double kDistancePerPulse = 1.0 / kEncoderCPR * kWheelDiameter * Math.PI; // For every one rotation, distance traveled should be the circumference of wheel
    }

    public static final class AutoConstants {
        public static final double kDriveControllerTolerance = 0.2; // meters
        public static final double kDefaultDriveTime = 1.7; // seconds
        public static final double kDefaultShotPower = 1;
    }

    public static final class IndexConstants {
        public static final int kHorizontalMotorId = 2;
        public static final int kVerticalFrontMotorId = 6;
        public static final int kVerticalBackMotorId = 5;
        public static final double kDefaultHoriPower = 0.4; 
        public static final double kDefaultVertPower = 0.8;
    }

    public static final class ShooterConstants {
        public static final int kMasterMotorId = 7;
        public static final int kFollowerMotorId = 4;
        public static final int kSensorUnitsPerRotation = 4096;
        public static final int kMaxRPM = 18700;
        public static final int kGearRatio = 1; 
    
        public static final double kV = 0.025248;
        public static final double kA = 0.0014502;
        public static final LinearSystem<N1, N1, N1> m_plant = LinearSystemId.identifyVelocitySystem(kV, kA);

        public static final KalmanFilter<N1, N1, N1> m_observer = 
            new KalmanFilter<>(
                Nat.N1(), 
                Nat.N1(), 
                m_plant, 
                VecBuilder.fill(3.0), 
                VecBuilder.fill(0.01), 
                0.020);
    
        public static final LinearQuadraticRegulator<N1, N1, N1> m_controller =
        new LinearQuadraticRegulator<>(
            m_plant,
            VecBuilder.fill(8.0), // Velocity error tolerance
            VecBuilder.fill(12.0), // Control effort (voltage) tolerance
            0.020);

        public static final LinearSystemLoop<N1, N1, N1> loop =
            new LinearSystemLoop<>(m_plant, m_controller, m_observer, 12.0, 0.020);
    }

    public static final class IntakeConstants {
        public static final int kIntakeMotorId = 3; 
        public static final double kDefaultPower = 0.6;
    }

    public static final class OIConstants {
        // The driver joystick controls the drivetrain, intake, and indexer
        public static final int kDriverJoystickPort = 0;
        // Shooter joystick primarily controls shooter and indexer, but can control intake as well
        public static final int kShooterJoystickPort = 1;

        // both buttons
        public static final int kIntakeButton = 1;
        public static final int kIndexButton = 3;
        public static final int kReverseIndexButton = 2;
        public static final int kDriverHorizontalIndexInButton = 4; 
        public static final int kDriverHorizontalIndexOutButton = 5; 
        public static final int kDriverSwitchDirection = 8;
        public static final int kShooterVerticalIndexUpButton = 4; 
        public static final int kShooterVerticalIndexDownButton = 5; 
    }

    public static final class ShuffleboardConstants {
        public static final String kDriveTab = "Change Power";
        public static final String kAutoTab = "Auto";
    }

    public static final class PneumaticConstants {
        public static final int[] kLeftValvePorts = {2, 3};
        public static final int[] kRightValuePorts = {0, 1};
    }

}
