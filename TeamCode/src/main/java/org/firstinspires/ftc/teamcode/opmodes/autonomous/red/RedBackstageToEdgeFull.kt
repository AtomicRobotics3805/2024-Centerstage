package org.firstinspires.ftc.teamcode.opmodes.autonomous.red

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
import org.firstinspires.ftc.teamcode.routines.BackstageRoutines
import org.firstinspires.ftc.teamcode.routines.SharedRoutines
import org.firstinspires.ftc.teamcode.trajectoryFactory.CompetitionTrajectoryFactory

@Autonomous(name = "Red Backstage to Edge Park Two Pixels", group = "Red Backstage")
class RedBackstageToEdgeFull: AutonomousOpMode(
    Constants.Color.RED,
    CompetitionTrajectoryFactory,
    { BackstageRoutines.backstageFullPathAndPark },
    { SharedRoutines.initRoutineEdgePark },
    MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ),
    Arm,
    Claw,
    Lift,
    Intake,
    DetectionMechanism
)