package org.firstinspires.ftc.teamcode.opmodes.autonomous.advanced.red

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
import org.firstinspires.ftc.teamcode.routines.advanced.TwoPlusTwoBackstage
import org.firstinspires.ftc.teamcode.routines.advanced.TwoPlusTwoShared
import org.firstinspires.ftc.teamcode.routines.old.BackstageRoutines
import org.firstinspires.ftc.teamcode.routines.old.SharedRoutines
import org.firstinspires.ftc.teamcode.trajectoryFactory.AdvancedTrajectoryFactory
import org.firstinspires.ftc.teamcode.trajectoryFactory.CompetitionTrajectoryFactory

@Autonomous(name = "2+2 Red Backstage Center Park", group = "2+2 Red", preselectTeleOp="Competition TeleOp v1 - Red")
class TwoPlusTwoRedBackstageFarPark : AutonomousOpMode(
    Constants.Color.RED,
    AdvancedTrajectoryFactory,
    { TwoPlusTwoBackstage.backstageTwoPlusTwo },
    { SharedRoutines.initRoutineCenterPark },
    MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ) { AdvancedTrajectoryFactory.startBackstage },
    Arm,
    Claw,
    Lift,
    Intake,
    DetectionMechanism
)