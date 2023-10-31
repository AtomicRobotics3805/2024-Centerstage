package org.firstinspires.ftc.teamcode.opmodes.teleop

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.TeleOpMode
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import org.firstinspires.ftc.teamcode.controls.CompetitionControls
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Drone
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.routines.SharedRoutines

@TeleOp(name = "Competition TeleOp v1")
class CompetitionTeleOp : TeleOpMode(
    CompetitionControls(),
    Constants.Color.UNKNOWN,
    null,
    { parallel {
        +TelemetryCommand(1000.0, { Constants.drive.poseEstimate.heading.toString() })

    }},
    { SharedRoutines.teleopInitRoutine },
    MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ),
    Intake,
    Lift,
    Arm,
    Claw,
    Drone
)