package org.firstinspires.ftc.teamcode.visualization

import com.noahbres.meepmeep.MeepMeep
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.atomicrobotics3805.cflib.visualization.MeepMeepRobot
import org.atomicrobotics3805.cflib.visualization.MeepMeepVisualizer
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.routines.advanced.MechanismRoutines
import org.firstinspires.ftc.teamcode.routines.advanced.TwoPlusTwoBackstage
import org.firstinspires.ftc.teamcode.routines.advanced.TwoPlusTwoShared
import org.firstinspires.ftc.teamcode.trajectoryFactory.AdvancedTrajectoryFactory
import org.firstinspires.ftc.teamcode.trajectoryFactory.CompetitionTrajectoryFactory

fun main() {
    MeepMeepVisualizer.addRobot(
        MeepMeepRobot(
            MecanumDrive(
                CompetitionDriveConstants,
                TwoWheelOdometryLocalizer(CompetitionOdometryConstants)

            ) { AdvancedTrajectoryFactory.startBackstage },
            14.5,
            15.0,
            {
                sequential {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeBack)
                    +Intake.slowEject
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeBackToBackdropOuter)
                        +MechanismRoutines.liftRaise
                    }
                    +MechanismRoutines.scoreSingle
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropOuterToInnerStack)
                    +Intake.intakeStack
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropInner)
                        +sequential {
                            +Delay(2.0)
                            +MechanismRoutines.liftRaise
                        }
                    }
                    +MechanismRoutines.scoreTwo
                    +TwoPlusTwoShared.backdropInnerToPark
                }
            },
            Constants.Color.RED
        )
    )

    MeepMeepVisualizer.run(AdvancedTrajectoryFactory, background = MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
}