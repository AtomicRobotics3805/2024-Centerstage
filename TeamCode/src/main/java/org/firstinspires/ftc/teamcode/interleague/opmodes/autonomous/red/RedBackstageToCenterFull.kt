package org.firstinspires.ftc.teamcode.interleague.opmodes.autonomous.red

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.AutonomousOpMode
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.interleague.routines.BackstageRoutines
import org.firstinspires.ftc.teamcode.interleague.routines.SharedRoutines
import org.firstinspires.ftc.teamcode.interleague.routines.WingRoutines
import org.firstinspires.ftc.teamcode.interleague.trajectoryFactory.CompetitionTrajectoryFactory
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.NewLid

@Autonomous(name = "Red Backstage to Center Park Two Pixels", group = "Red Backstage", preselectTeleOp="Competition TeleOp v1 - Red")
class RedBackstageToCenterFull: AutonomousOpMode(
    Constants.Color.RED,
    CompetitionTrajectoryFactory,
    { BackstageRoutines.backstageFullPathAndPark },
    { SharedRoutines.initRoutineCenterPark },
    MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ) { CompetitionTrajectoryFactory.backstageStartPose },
    Arm,
    NewLid,
    Lift,
    Intake,
    DetectionMechanism
)