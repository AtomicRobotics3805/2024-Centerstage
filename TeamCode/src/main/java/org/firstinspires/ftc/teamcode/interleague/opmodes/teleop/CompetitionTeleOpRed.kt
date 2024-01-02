package org.firstinspires.ftc.teamcode.interleague.opmodes.teleop

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.TeleOpMode
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.trajectories.switchAngle
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.interleague.controls.CompetitionControls
import org.firstinspires.ftc.teamcode.interleague.routines.TeleOpRoutines
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Drone
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.NewLid
import org.firstinspires.ftc.teamcode.mechanisms.Ramp

@TeleOp(name = "Competition TeleOp v2 - Red")
class CompetitionTeleOpRed: TeleOpMode(
    CompetitionControls(),
    Constants.Color.RED,
    null,
    null,
    { TeleOpRoutines.teleOpInitRoutine },
    MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ) { Pose2d(0.0, 0.0, -90.0.switchAngle.rad) },
    Intake,
    Lift,
    Arm,
    NewLid,
    Ramp,
    Drone
)