package org.firstinspires.ftc.teamcode.tuning.drivetrain

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.profile.MotionProfile
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator.generateSimpleMotionProfile
import com.acmerobotics.roadrunner.profile.MotionState
import com.acmerobotics.roadrunner.util.NanoClock
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.Constants.drive
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.driving.MecanumDriveConstants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants

/*
 * This routine is designed to tune the PID coefficients used by the REV Expansion Hubs for closed-
 * loop velocity control. Although it may seem unnecessary, tuning these coefficients is just as
 * important as the positional parameters. Like the other manual tuning routines, this op mode
 * relies heavily upon the dashboard. To access the dashboard, connect your computer to the RC's
 * WiFi network. In your browser, navigate to https://192.168.49.1:8080/dash if you're using the RC
 * phone or https://192.168.43.1:8080/dash if you are using the Control Hub. Once you've successfully
 * connected, start the program, and your robot will begin moving forward and backward according to
 * a motion profile. Your job is to graph the velocity errors over time and adjust the PID
 * coefficients (note: the tuning variable will not appear until the op mode finishes initializing).
 * Once you've found a satisfactory set of gains, add them to the DriveConstants.java file under the
 * drive.constants.MOTOR_VEL_PID field.
 *
 * Recommended tuning process:
 *
 * 1. Increase kP until any phase lag is eliminated. Concurrently increase kD as necessary to
 *    mitigate oscillations.
 * 2. Add kI (or adjust kF) until the steady state/constant velocity plateaus are reached.
 * 3. Back off kP and kD a little until the response is less oscillatory (but without lag).
 *
 * Pressing Y/Δ (Xbox/PS4) will pause the tuning process and enter driver override, allowing the
 * user to reset the position of the bot in the event that it drifts off the path.
 * Pressing B/O (Xbox/PS4) will cede control back to the tuning process.
 */
@Config
@Autonomous(group = "tuning")
@Disabled
class DriveVelocityPIDTuner: LinearOpMode() {
    enum class Mode {
        DRIVER_MODE, TUNING_MODE
    }

    override fun runOpMode() {
        Constants.opMode = this
        Constants.drive = MecanumDrive(
            CompetitionDriveConstants,
            TwoWheelOdometryLocalizer(CompetitionOdometryConstants),
        )
        CommandScheduler.registerSubsystems(TelemetryController, drive)
        var mode = Mode.TUNING_MODE
        drive.constants as MecanumDriveConstants
        var lastKp: Double = drive.constants.MOTOR_VEL_PID.p
        var lastKi: Double = drive.constants.MOTOR_VEL_PID.i
        var lastKd: Double = drive.constants.MOTOR_VEL_PID.d
        var lastKf: Double = drive.constants.MOTOR_VEL_PID.f
        val clock: NanoClock = NanoClock.system()
        TelemetryController.telemetry.addLine("Ready!")
        TelemetryController.telemetry.update()
        TelemetryController.telemetry.clearAll()
        waitForStart()
        if (isStopRequested()) return
        var movingForwards = true
        var activeProfile: MotionProfile = generateProfile(true)
        var profileStart: Double = clock.seconds()
        while (!isStopRequested()) {
            TelemetryController.telemetry.addData("mode", mode)
            when (mode) {
                Mode.TUNING_MODE -> {
                    if (gamepad1.y) {
                        mode = Mode.DRIVER_MODE
                        (drive as MecanumDrive).setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER)
                    }

                    // calculate and set the motor power
                    val profileTime: Double = clock.seconds() - profileStart
                    if (profileTime > activeProfile.duration()) {
                        // generate a new profile
                        movingForwards = !movingForwards
                        activeProfile = generateProfile(movingForwards)
                        profileStart = clock.seconds()
                    }
                    val motionState: MotionState = activeProfile.get(profileTime)
                    val targetPower: Double = drive.constants.kV * motionState.v
                    drive.setDrivePower(Pose2d(targetPower, 0.0, 0.0))
                    val velocities: List<Double> = (drive as MecanumDrive).getWheelVelocities()

                    // update TelemetryController.telemetry
                    TelemetryController.telemetry.addData("targetVelocity", motionState.v)
                    var i = 0
                    while (i < velocities.size) {
                        TelemetryController.telemetry.addData("measuredVelocity$i", velocities[i])
                        TelemetryController.telemetry.addData(
                            "error$i",
                            motionState.v - velocities[i]
                        )
                        i++
                    }
                }
                Mode.DRIVER_MODE -> {
                    if (gamepad1.b) {
                        (drive as MecanumDrive).setMode(DcMotor.RunMode.RUN_USING_ENCODER)
                        mode = Mode.TUNING_MODE
                        movingForwards = true
                        activeProfile = generateProfile(movingForwards)
                        profileStart = clock.seconds()
                    }
                    drive.setDrivePower(
                        Pose2d(
                            -gamepad1.left_stick_y.toDouble(),
                            -gamepad1.left_stick_x.toDouble(),
                            -gamepad1.right_stick_x.toDouble()
                        )
                    )
                }
            }
            if (lastKp != drive.constants.MOTOR_VEL_PID.p || lastKd != drive.constants.MOTOR_VEL_PID.d || lastKi != drive.constants.MOTOR_VEL_PID.i || lastKf != drive.constants.MOTOR_VEL_PID.f) {
                (drive as MecanumDrive).setPIDFCoefficients(drive.constants.MOTOR_VEL_PID)
                lastKp = drive.constants.MOTOR_VEL_PID.p
                lastKi = drive.constants.MOTOR_VEL_PID.i
                lastKd = drive.constants.MOTOR_VEL_PID.d
                lastKf = drive.constants.MOTOR_VEL_PID.f
            }
            TelemetryController.telemetry.update()
        }
    }

    companion object {
        @JvmField
        var DISTANCE = 72.0 // in

        private fun generateProfile(movingForward: Boolean): MotionProfile {
            val start = MotionState(if (movingForward) 0.0 else DISTANCE, 0.0, 0.0, 0.0)
            val goal = MotionState(if (movingForward) DISTANCE else 0.0, 0.0, 0.0, 0.0)
            return generateSimpleMotionProfile(start, goal, drive.constants.MAX_VEL, drive.constants.MAX_ACCEL)
        }
    }
}