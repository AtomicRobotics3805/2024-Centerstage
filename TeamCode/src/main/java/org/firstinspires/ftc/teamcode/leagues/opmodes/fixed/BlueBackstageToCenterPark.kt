package org.firstinspires.ftc.teamcode.leagues.opmodes.fixed

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
import org.firstinspires.ftc.teamcode.mechanisms.Ramp
import org.firstinspires.ftc.teamcode.leagues.routines.fixed.FixedBackstage
import org.firstinspires.ftc.teamcode.leagues.trajectoryFactory.AdvancedTrajectoryFactory

@Autonomous(name = "Blue Backstage - CENTER")
@Disabled
class BlueBackstageToCenterPark: AutonomousOpMode(
    Constants.Color.BLUE,
    AdvancedTrajectoryFactory,
    { FixedBackstage.backstageOpMode },
    { FixedBackstage.initCenter },
    MecanumDrive(
        CompetitionDriveConstants,
        TwoWheelOdometryLocalizer(CompetitionOdometryConstants)
    ) { AdvancedTrajectoryFactory.startBackstage },
    Arm,
    Lid,
    Lift,
    Intake,
    DetectionMechanism,
    Ramp
)