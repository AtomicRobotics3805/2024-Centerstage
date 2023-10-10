package org.firstinspires.ftc.teamcode.teleop

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.TeleOpMode
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import org.firstinspires.ftc.teamcode.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.controls.CompetitionControls

@TeleOp(name = "Encoder Test")
class EncoderTestTeleOpMode : TeleOpMode(
    CompetitionControls(),
    Constants.Color.RED,
    //mainRoutine = { TelemetryCommand(100.0) { Constants.drive.localizer.poseEstimate.toString() } },
    drive = MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ) { Pose2d(0.0, 0.0, 0.0.rad) },
    subsystems = arrayOf()
)