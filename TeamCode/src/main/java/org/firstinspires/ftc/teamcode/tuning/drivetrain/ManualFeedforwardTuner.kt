package org.firstinspires.ftc.teamcode.tuning.drivetrain

import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.kinematics.Kinematics.calculateMotorFeedforward
import com.acmerobotics.roadrunner.profile.MotionProfile
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator.generateSimpleMotionProfile
import com.acmerobotics.roadrunner.profile.MotionState
import com.acmerobotics.roadrunner.util.NanoClock
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.Constants.drive
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import java.util.*

/*
* This routine is designed to tune the open-loop feedforward coefficients. Although it may seem unnecessary,
* tuning these coefficients is just as important as the positional parameters. Like the other
* manual tuning routines, this op mode relies heavily upon the dashboard. To access the dashboard,
* connect your computer to the RC's WiFi network. In your browser, navigate to
* https://192.168.49.1:8080/dash if you're using the RC phone or https://192.168.43.1:8080/dash if
* you are using the Control Hub. Once you've successfully connected, start the program, and your
* robot will begin moving forward and backward according to a motion profile. Your job is to graph
* the velocity errors over time and adjust the feedforward coefficients. Once you've found a
* satisfactory set of gains, add them to the appropriate fields in the DriveConstants.java file.
*
* Pressing X (on the Xbox and Logitech F310 gamepads, square on the PS4 Dualshock gamepad) will
* pause the tuning process and enter driver override, allowing the user to reset the position of
* the bot in the event that it drifts off the path.
* Pressing A (on the Xbox and Logitech F310 gamepads, X on the PS4 Dualshock gamepad) will cede
* control back to the tuning process.
*/
@Config
@Autonomous(group = "tuning")
@Disabled
class ManualFeedforwardTuner: LinearOpMode() {

    enum class Mode {
        DRIVER_MODE, TUNING_MODE
    }

    private var mode: Mode? = null

    override fun runOpMode() {
        Constants.opMode = this
        Constants.drive = MecanumDrive(
            CompetitionDriveConstants,
            TwoWheelOdometryLocalizer(CompetitionOdometryConstants),
        ) { Pose2d() }
        CommandScheduler.registerSubsystems(TelemetryController, drive)
        if (drive.constants.IS_RUN_USING_ENCODER) {
            RobotLog.setGlobalErrorMsg(
                "Feedforward constants usually don't need to be tuned " +
                        "when using the built-in drive motor velocity PID."
            )
        }
        mode = Mode.TUNING_MODE
        val clock = NanoClock.system()
        CommandScheduler.scheduleCommand(TelemetryCommand(1000.0, "Ready"))
        while (!isStopRequested && !opModeIsActive()) {
            CommandScheduler.run()
        }
        CommandScheduler.cancelAll()
        var movingForwards = true
        var activeProfile = generateProfile(true)
        var profileStart = clock.seconds()
        var lastTime = clock.seconds()
        while (!isStopRequested) {
            TelemetryController.telemetry.addData("Time per cycle", clock.seconds() - lastTime)
            lastTime = clock.seconds()
            TelemetryController.telemetry.addData("mode", mode)
            CommandScheduler.run()
            when (mode!!) {
                Mode.TUNING_MODE -> {
                    if (gamepad1.x) {
                        mode = Mode.DRIVER_MODE
                    }

                    // calculate and set the motor power
                    val profileTime = clock.seconds() - profileStart
                    if (profileTime > activeProfile.duration()) {
                        // generate a new profile
                        movingForwards = !movingForwards
                        activeProfile = generateProfile(movingForwards)
                        profileStart = clock.seconds()
                    }
                    val motionState = activeProfile[profileTime]
                    val targetPower: Double =
                        calculateMotorFeedforward(motionState.v, motionState.a, drive.constants.kV, drive.constants.kA, drive.constants.kStatic)
                    drive.setDrivePower(Pose2d(targetPower, 0.0, 0.0))
                    val poseVelo: Pose2d = Objects.requireNonNull(
                        drive.poseVelocity,
                        "poseVelocity() must not be null. Ensure that the getWheelVelocities() method has been overridden in your localizer."
                    )!!
                    val currentVelo = poseVelo.y

                    // update telemetry
                    TelemetryController.telemetry.addData("targetVelocity", motionState.v)
                    TelemetryController.telemetry.addData("poseVelocity", currentVelo)
                    TelemetryController.telemetry.addData("error", currentVelo - motionState.v)
                }
                Mode.DRIVER_MODE -> {
                    if (gamepad1.a) {
                        mode = Mode.TUNING_MODE
                        movingForwards = true
                        activeProfile = generateProfile(movingForwards)
                        profileStart = clock.seconds()
                    }
                    drive.setDrivePower(
                        Pose2d(
                            (-gamepad1.left_stick_y).toDouble(),
                            (-gamepad1.left_stick_x).toDouble(),
                            (-gamepad1.right_stick_x).toDouble()
                        )
                    )
                }
            }
        }
    }

    companion object {
        @JvmField
        var DISTANCE = 72.0 // in
        private fun generateProfile(movingForward: Boolean): MotionProfile {
            val start = MotionState(if (movingForward) 0.0 else DISTANCE, 0.0, 0.0, 0.0)
            val goal = MotionState(if (movingForward) DISTANCE else 0.0, 0.0, 0.0, 0.0)
            return generateSimpleMotionProfile(
                start, goal,
                drive.constants.MAX_VEL,
                drive.constants.MAX_ACCEL,
                0.0
            )
        }
    }
}