package org.firstinspires.ftc.teamcode.tuning.drivetrain

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.Constants.drive
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.trajectories.ParallelTrajectory
import org.atomicrobotics3805.cflib.trajectories.toRadians
import org.atomicrobotics3805.cflib.utilCommands.Delay
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(group = "tuning")
@Disabled
class SplineTest: LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        Constants.opMode = this
        Constants.drive = MecanumDrive(
            CompetitionDriveConstants,
            TwoWheelOdometryLocalizer(CompetitionOdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(drive, TelemetryController)
        val forwardTrajectory: ParallelTrajectory = drive.trajectoryBuilder(Pose2d())
            .splineTo(Vector2d(30.0, 30.0), 0.0)
            .build()
        val reverseTrajectory: ParallelTrajectory = drive.trajectoryBuilder(forwardTrajectory.end(), true)
            .splineTo(Vector2d(0.0, 0.0), 180.0.toRadians)
            .build()
        waitForStart()
        CommandScheduler.scheduleCommand(sequential {
            +drive.followTrajectory(forwardTrajectory)
            +Delay(2.0)
            +drive.followTrajectory(reverseTrajectory)
        })
        while (opModeIsActive()) {
            CommandScheduler.run()
        }
    }

}