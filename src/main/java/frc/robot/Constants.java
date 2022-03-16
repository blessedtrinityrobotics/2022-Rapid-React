// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
        public static final int[] kLeftEncoder = {0, 1};
        public static final int[] kRightEncoder = {2, 3};
        public static final double kWheelDiameter = 0.1524; // Wheels are 6 in -> to meters
        public static final double kEncoderCPR = 360.0;
        public static final double kDistancePerPulse = 1.0 / kEncoderCPR * kWheelDiameter * Math.PI; // For every one rotation, distance traveled should be the circumference of wheel
    }

    public static final class AutoConstants {
        public static final double kDriveControllerTolerance = 0.2; // meters
        public static final double kDefaultDriveTime = 1.7; // seconds
        public static final double kDefaultShotPower = 0.65;
    }

    public static final class IndexConstants {
        public static final int kHorizontalMotorId = 6;
        public static final int kVerticalFrontMotorId = 5;
        public static final int kVerticalBackMotorId = 7;
        public static final double kDefaultPower = 0.825; 
    }

    public static final class ShooterConstants {
        public static final int kMasterMotorId = 2;
        public static final int kFollowerMotorId = 3;
    }

    public static final class IntakeConstants {
        public static final int kIntakeMotorId = 4; 
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
        public static final String kDriveTab = "Drive";
        public static final String kAutoTab = "Auto";
    }

}
