package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.TeleOpMode
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.roadrunner.DashboardUtil
import org.atomicrobotics3805.cflib.subsystems.DisplayRobot
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import org.firstinspires.ftc.teamcode.controls.CompetitionControls
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants

@TeleOp(name = "Encoder Drivetrain Test")
class EncoderDrivetrainTest: TeleOpMode(
    CompetitionControls(),
    Constants.Color.RED,
    initRoutine = { parallel {
            +TelemetryCommand(1000.0) { Constants.drive.poseEstimate.toString() }
            +TelemetryCommand(1000.0) { Constants.drive.poseVelocity.toString() }
            +DisplayRobot()
        }
    },
    drive = MecanumDrive(
            CompetitionDriveConstants,
            TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
        ) { Pose2d(0.0, 0.0, 0.0.rad) }
)