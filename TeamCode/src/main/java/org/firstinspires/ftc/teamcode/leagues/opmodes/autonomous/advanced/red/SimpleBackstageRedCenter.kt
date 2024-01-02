package org.firstinspires.ftc.teamcode.leagues.opmodes.autonomous.advanced.red

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
import org.firstinspires.ftc.teamcode.leagues.routines.advanced.GeneralShared
import org.firstinspires.ftc.teamcode.leagues.routines.advanced.SimpleBackstage
import org.firstinspires.ftc.teamcode.leagues.trajectoryFactory.AdvancedTrajectoryFactory

@Disabled
@Autonomous(name = "Simple Red Backstage - CENTER", group = "Simple", preselectTeleOp="Competition TeleOp v1 - Red")
class SimpleBackstageRedCenter : AutonomousOpMode(
    Constants.Color.RED,
    AdvancedTrajectoryFactory,
    { SimpleBackstage.simpleBackstage },
    { GeneralShared.initRoutineCenterPark },
    MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ) { AdvancedTrajectoryFactory.startBackstage },
    Arm,
    Lid,
    Lift,
    Intake,
    DetectionMechanism
)