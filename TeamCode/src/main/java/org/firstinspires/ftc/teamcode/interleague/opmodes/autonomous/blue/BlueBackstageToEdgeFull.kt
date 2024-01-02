package org.firstinspires.ftc.teamcode.interleague.opmodes.autonomous.blue

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
import org.firstinspires.ftc.teamcode.mechanisms.VerticalIntake

@Autonomous(name = "Blue Backstage to Edge Park Two Pixels", group = "Blue Backstage", preselectTeleOp="Competition TeleOp v1 - Blue")
class BlueBackstageToEdgeFull: AutonomousOpMode(
    Constants.Color.BLUE,
    CompetitionTrajectoryFactory,
    { BackstageRoutines.backstageFullPathAndPark },
    { SharedRoutines.initRoutineEdgePark },
    MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ) { CompetitionTrajectoryFactory.backstageStartPose },
    Arm,
    NewLid,
    Lift,
    Intake,
    DetectionMechanism,
    VerticalIntake
)