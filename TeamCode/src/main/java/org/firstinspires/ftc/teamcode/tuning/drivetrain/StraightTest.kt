package org.firstinspires.ftc.teamcode.tuning.drivetrain

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import org.atomicrobotics3805.cflib.*
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.trajectories.ParallelTrajectory
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants

/*
 * This is a simple routine to test driving forwards.
 */
@Config
@Autonomous(group = "tuning")
class StraightTest: LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        Constants.opMode = this
        Constants.drive = MecanumDrive(
            CompetitionDriveConstants,
            TwoWheelOdometryLocalizer(CompetitionOdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(Constants.drive, TelemetryController)
        val trajectory: ParallelTrajectory = Constants.drive.trajectoryBuilder(Pose2d())
            .forward(DISTANCE)
            .build()
        waitForStart()
        CommandScheduler.scheduleCommand(sequential {
            +Constants.drive.followTrajectory(trajectory)
            +parallel {
                TelemetryCommand(1000.0, "finalX") { Constants.drive.poseEstimate.x.toString() }
                TelemetryCommand(1000.0, "finalY") { Constants.drive.poseEstimate.y.toString() }
                TelemetryCommand(1000.0, "finalHeading") { Constants.drive.poseEstimate.heading.toString() }
            }
        })
        while (opModeIsActive()) {
            CommandScheduler.run()
        }
    }

    companion object {
        @JvmField
        var DISTANCE = 60.0 // in
    }
}