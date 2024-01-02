package org.firstinspires.ftc.teamcode.leagues.opmodes.autonomous.old.red

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.AutonomousOpMode
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.leagues.routines.old.SharedRoutines
import org.firstinspires.ftc.teamcode.leagues.routines.old.WingRoutines
import org.firstinspires.ftc.teamcode.leagues.trajectoryFactory.CompetitionTrajectoryFactory

@Disabled
@Autonomous(name = "Red Wing to Center Park Two Pixels", group = "Red Wing", preselectTeleOp="Competition TeleOp v1 - Red")
class RedWingToCenterFull: AutonomousOpMode(
    Constants.Color.RED,
    CompetitionTrajectoryFactory,
    { WingRoutines.wingFullPathAndPark },
    { SharedRoutines.initRoutineCenterPark },
    MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ) { CompetitionTrajectoryFactory.wingStartPose },
    Arm,
    Lid,
    Lift,
    Intake,
    DetectionMechanism
)