package org.firstinspires.ftc.teamcode.opmodes.autonomous.advanced.blue

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
import org.firstinspires.ftc.teamcode.routines.advanced.GeneralShared
import org.firstinspires.ftc.teamcode.routines.advanced.SimpleBackstage
import org.firstinspires.ftc.teamcode.routines.advanced.TwoPlusTwoBackstage
import org.firstinspires.ftc.teamcode.trajectoryFactory.AdvancedTrajectoryFactory

@Autonomous(name = "Simple Blue Backstage", group = "Simple Blue", preselectTeleOp="Competition TeleOp v1 - Blue")
class SimpleBackstageBlue : AutonomousOpMode(
    Constants.Color.BLUE,
    AdvancedTrajectoryFactory,
    { SimpleBackstage.simpleBackstage },
    { GeneralShared.initRoutineGamepadPark },
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