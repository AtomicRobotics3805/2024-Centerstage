package org.firstinspires.ftc.teamcode.leagues.opmodes.autonomous.old.blue

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
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

@Autonomous(name = "Blue Wing to Center Park Two Pixels", group = "Blue Wing", preselectTeleOp="Competition TeleOp v1 - Blue")
class BlueWingToCenterFull: AutonomousOpMode(
    Constants.Color.BLUE,
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