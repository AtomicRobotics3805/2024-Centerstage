package org.firstinspires.ftc.teamcode.tuning.drivetrain

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.driving.Turn
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.trajectories.toRadians
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants

/*
 * This is a simple routine to test turning capabilities.
 */
@Config
@Autonomous(group = "tuning")
@Disabled
class TurnTest: LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        Constants.opMode = this
        Constants.drive = MecanumDrive(
            CompetitionDriveConstants,
            TwoWheelOdometryLocalizer(CompetitionOdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(Constants.drive, TelemetryController)
        waitForStart()
        CommandScheduler.scheduleCommand(Constants.drive.turn(ANGLE.toRadians, Turn.TurnType.RELATIVE))
        while (opModeIsActive()) {
            CommandScheduler.run()
        }
    }

    companion object {
        @JvmField
        var ANGLE = 90.0 // deg
    }
}