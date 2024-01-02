package org.firstinspires.ftc.teamcode.leagues.opmodes.teleop

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.TeleOpMode
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.trajectories.switchAngle
import org.firstinspires.ftc.teamcode.leagues.controls.CompetitionControls
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Drone
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.Ramp
import org.firstinspires.ftc.teamcode.leagues.routines.TeleOpRoutines
import org.firstinspires.ftc.teamcode.mechanisms.NewLid

@TeleOp(name = "Competition TeleOp v1 - Blue")
class CompetitionTeleOpBlue: TeleOpMode(
    CompetitionControls(),
    Constants.Color.BLUE,
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