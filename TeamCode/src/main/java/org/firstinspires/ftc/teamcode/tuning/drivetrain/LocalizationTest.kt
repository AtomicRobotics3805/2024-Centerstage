package org.firstinspires.ftc.teamcode.tuning.drivetrain

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants.drive
import org.atomicrobotics3805.cflib.Constants.opMode
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.roadrunner.DashboardUtil
import org.atomicrobotics3805.cflib.subsystems.DisplayRobot
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.utility.DriverControlled

/**
 * This is a simple teleop routine for testing localization. Drive the robot around like a normal
 * teleop routine and make sure the robot's estimated pose matches the robot's actual pose (slight
 * errors are not out of the ordinary, especially with sudden drive motions). The goal of this
 * exercise is to ascertain whether the localizer has been configured properly (note: the pure
 * encoder localizer heading may be significantly off if the track width has not been tuned).
 */
@Config
@Autonomous(group = "tuning")
@Disabled
class LocalizationTest: LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        opMode = this
        drive = MecanumDrive(
            CompetitionDriveConstants,
            TwoWheelOdometryLocalizer(CompetitionOdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(TelemetryController, drive)
        waitForStart()
        CommandScheduler.scheduleCommand(DriverControlled(opMode.gamepad1, pov = DriverControlled.POV.FIELD_CENTRIC, reverseStrafe = CompetitionDriveConstants.REVERSE_STRAFE, reverseStraight = CompetitionDriveConstants.REVERSE_STRAIGHT, reverseTurn = CompetitionDriveConstants.REVERSE_TURN))
        CommandScheduler.scheduleCommand(TelemetryCommand(1000.0, "Position") { drive.poseEstimate.toString() })
        CommandScheduler.scheduleCommand(TelemetryCommand(1000.0, "Velocity") { drive.poseVelocity.toString() })
        CommandScheduler.scheduleCommand(DisplayRobot())
        while (!isStopRequested) {
            CommandScheduler.run()
        }
    }
}