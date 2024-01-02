package org.firstinspires.ftc.teamcode.leagues.routines.advanced

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.PropProcessor
import org.firstinspires.ftc.teamcode.leagues.trajectoryFactory.AdvancedTrajectoryFactory

object SimpleBackstage {
    val simpleBackstage: Command
        get() = sequential {
            +Lid.close
            +OptionCommand("Different signature",
                { DetectionMechanism.selectedPosition },
                Pair(PropProcessor.Selected.LEFT, leftRoutine),
                Pair(PropProcessor.Selected.MIDDLE, centerRoutine),
                Pair(PropProcessor.Selected.RIGHT, rightRoutine))
        }

    val leftRoutine: Command
        get() = sequential {
            +OptionCommand("",
                { Constants.color },
                Pair(Constants.Color.BLUE, sequential {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeBack)
                    +Intake.slowEject
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeBackToBackdropOuter)
                        +MechanismRoutines.liftRaise
                    }
                    +MechanismRoutines.scoreSingle
                    +GeneralShared.backdropOuterToPark
                }),
                Pair(Constants.Color.RED, sequential {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeFront)
                    +Intake.slowEject
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeFrontToBackdropInner)
                        +MechanismRoutines.liftRaise
                    }
                    +MechanismRoutines.scoreSingle
                    +GeneralShared.backdropInnerToPark
                }))
        }

    val centerRoutine: Command
        get() = sequential {
            +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeCenter)
            +Intake.slowEject
            +parallel {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeCenterToBackdropCenter)
                +MechanismRoutines.liftRaise
            }
            +MechanismRoutines.scoreSingle
            +GeneralShared.backdropCenterToPark
        }

    val rightRoutine: Command
        get() = OptionCommand("",
            { Constants.color },
            Pair(Constants.Color.BLUE, sequential {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeFront)
                +Intake.slowEject
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeFrontToBackdropInner)
                    +MechanismRoutines.liftRaise
                }
                +MechanismRoutines.scoreSingle
                +GeneralShared.backdropInnerToPark
            }),
            Pair(Constants.Color.RED, sequential {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeBack)
                +Intake.slowEject
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeBackToBackdropOuter)
                    +MechanismRoutines.liftRaise
                }
                +MechanismRoutines.scoreSingle
                +GeneralShared.backdropOuterToPark
            }))
}