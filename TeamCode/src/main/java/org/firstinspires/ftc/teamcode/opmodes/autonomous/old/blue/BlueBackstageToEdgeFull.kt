package org.firstinspires.ftc.teamcode.opmodes.autonomous.old.blue

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.AutonomousOpMode
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.routines.old.BackstageRoutines
import org.firstinspires.ftc.teamcode.routines.old.SharedRoutines
import org.firstinspires.ftc.teamcode.trajectoryFactory.CompetitionTrajectoryFactory

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
    Claw,
    Lift,
    Intake,
    DetectionMechanism
)