package org.firstinspires.ftc.teamcode.routines.advanced

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.PropProcessor
import org.firstinspires.ftc.teamcode.routines.old.BackstageRoutines
import org.firstinspires.ftc.teamcode.trajectoryFactory.AdvancedTrajectoryFactory
import org.firstinspires.ftc.teamcode.utility.SwitchCommand

object TwoPlusTwoBackstage {
    val backstageTwoPlusTwo: Command
        get() = SwitchCommand({ DetectionMechanism.selectedPosition },
            Pair(PropProcessor.Selected.LEFT, leftRoutine),
            Pair(PropProcessor.Selected.MIDDLE, centerRoutine),
            Pair(PropProcessor.Selected.RIGHT, rightRoutine))
    val leftRoutine: Command
        get() = SwitchCommand({ Constants.color },
            Pair(Constants.Color.BLUE, sequential {
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
            }),
            Pair(Constants.Color.RED, sequential {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeFront)
                +Intake.slowEject
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeFrontToBackdropInner)
                    +MechanismRoutines.liftRaise
                }
                +MechanismRoutines.scoreSingle
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropInnerToInnerStack)
                +Intake.intakeStack
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropOuter)
                    +sequential {
                        +Delay(2.0)
                        +MechanismRoutines.liftRaise
                    }
                }
                +MechanismRoutines.scoreTwo
                +TwoPlusTwoShared.backdropOuterToPark
            }))

    val centerRoutine: Command
        get() = sequential {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeCenter)
                +Intake.slowEject
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeCenterToBackdropCenter)
                    +MechanismRoutines.liftRaise
                }
                +MechanismRoutines.scoreSingle
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropCenterToInnerStack)
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

    val rightRoutine: Command
        get() = SwitchCommand({ Constants.color },
            Pair(Constants.Color.BLUE, sequential {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeFront)
                +Intake.slowEject
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeFrontToBackdropInner)
                    +MechanismRoutines.liftRaise
                }
                +MechanismRoutines.scoreSingle
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropInnerToInnerStack)
                +Intake.intakeStack
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropOuter)
                    +sequential {
                        +Delay(2.0)
                        +MechanismRoutines.liftRaise
                    }
                }
                +MechanismRoutines.scoreTwo
                +TwoPlusTwoShared.backdropOuterToPark
            }),
            Pair(Constants.Color.RED, sequential {
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
            }))
}