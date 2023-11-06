package org.firstinspires.ftc.teamcode.tuning.drivetrain

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.util.Angle.norm
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.Constants.drive
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.driving.Turn
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.trajectories.toRadians
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.MovingStatistics
import org.firstinspires.ftc.robotcore.internal.system.Misc
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import kotlin.math.sqrt

/*
 * This routine determines the effective track width. The procedure works by executing a point turn
 * with a given angle and measuring the difference between that angle and the actual angle (as
 * indicated by an external IMU/gyro, track wheels, or some other localizer). The quotient
 * given angle / actual angle gives a multiplicative adjustment to the estimated track width
 * (effective track width = estimated track width * given angle / actual angle). The routine repeats
 * this procedure a few times and averages the values for additional accuracy. Note: a relatively
 * accurate track width estimate is important or else the angular constraints will be thrown off.
 */
@Config
@Autonomous(group = "tuning")
@Disabled
class   TrackWidthTuner: LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        Constants.opMode = this
        Constants.drive = MecanumDrive(
            CompetitionDriveConstants,
            TwoWheelOdometryLocalizer(CompetitionOdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(TelemetryController, drive)
        // FINISHED: if you haven't already, set the localizer to something that doesn't depend on
        // drive encoders for computing the heading
        TelemetryController.telemetry.addLine("Press play to begin the track width tuner routine")
        TelemetryController.telemetry.addLine("Make sure your robot has enough clearance to turn smoothly")
        TelemetryController.telemetry.update()
        waitForStart()
        TelemetryController.telemetry.clearAll()
        TelemetryController.telemetry.addLine("Running...")
        TelemetryController.telemetry.update()
        val trackWidthStats = MovingStatistics(NUM_TRIALS)
        for (i in 0 until NUM_TRIALS) {
            drive.poseEstimate = Pose2d()

            // it is important to handle heading wraparounds
            var headingAccumulator = 0.0
            var lastHeading = 0.0
            CommandScheduler.scheduleCommand(drive.turn(ANGLE.toRadians, Turn.TurnType.RELATIVE))
            CommandScheduler.run()
            while (opModeIsActive() && CommandScheduler.hasCommands()) {
                val heading: Double = drive.poseEstimate.heading
                headingAccumulator += norm(heading - lastHeading)
                lastHeading = heading
                CommandScheduler.run()
            }
            val trackWidth: Double =
                CompetitionDriveConstants.TRACK_WIDTH * Math.toRadians(ANGLE) / headingAccumulator
            trackWidthStats.add(trackWidth)
            sleep(DELAY.toLong())
        }
        TelemetryController.telemetry.clearAll()
        TelemetryController.telemetry.addLine("Tuning complete")
        TelemetryController.telemetry.addLine(
            Misc.formatInvariant(
                "Effective track width = %.2f (SE = %.3f)",
                trackWidthStats.mean,
                trackWidthStats.standardDeviation / sqrt(NUM_TRIALS.toDouble())
            )
        )
        telemetry.update()
        while (opModeIsActive()) {
            idle()
        }
    }

    companion object {
        @JvmField
        var ANGLE = 180.0 // deg
        @JvmField
        var NUM_TRIALS = 5
        @JvmField
        var DELAY = 1000 // ms
    }
}