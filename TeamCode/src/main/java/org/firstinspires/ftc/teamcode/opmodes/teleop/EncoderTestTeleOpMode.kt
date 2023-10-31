package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotorEx
import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.TeleOpMode
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.roadrunner.Encoder
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.controls.CompetitionControls

@TeleOp(name = "Encoder Test")
class EncoderTestTeleOpMode: TeleOpMode(
    CompetitionControls(),
    Constants.Color.RED,
    mainRoutine = { parallel {
        +TelemetryCommand(100.0) { Constants.drive.localizer.poseEstimate.toString() }
        +DisplayEncoderCount()
    } },
    drive = MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ) { Pose2d(0.0, 0.0, 0.0.rad) },
    subsystems = arrayOf()
)
class DisplayEncoderCount: Command() {
    private lateinit var parallelEncoder: Encoder
    private lateinit var perpendicularEncoder: Encoder
    override val _isDone: Boolean
        get() = false

    override fun start() {
        parallelEncoder = Encoder(Constants.opMode.hardwareMap.get(DcMotorEx::class.java, CompetitionOdometryConstants.PARALLEL_NAME))
        perpendicularEncoder = Encoder(Constants.opMode.hardwareMap.get(DcMotorEx::class.java, CompetitionOdometryConstants.PERPENDICULAR_NAME))
    }

    override fun execute() {
        TelemetryController.telemetry.addData("Parallel Encoder Count", parallelEncoder.currentPosition)
        TelemetryController.telemetry.addData("Perpendicular Encoder Count", perpendicularEncoder.currentPosition)
    }
}