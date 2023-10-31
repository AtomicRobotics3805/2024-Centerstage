package org.firstinspires.ftc.teamcode.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.PropProcessor
import org.firstinspires.ftc.teamcode.trajectoryFactory.CompetitionTrajectoryFactory

object BackstageRoutines {
    val backstageFullPathAndPark: Command
        get() = sequential {
            +OptionCommand("Different signature",
                { DetectionMechanism.selectedPosition },
                Pair(PropProcessor.Selected.LEFT, backstageLeftRoutine),
                Pair(PropProcessor.Selected.MIDDLE, backstageCenterRoutine),
                Pair(PropProcessor.Selected.RIGHT, backstageRightRoutine))
        }

    val backstageLeftRoutine: Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        // Blue side ("left" is on the back side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToBackSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageBackSpikeTapeToScore)
                            +Lift.toLow
                            +Arm.open
                        }
                        +Claw.drop
                        +parallel {
                            +SharedRoutines.scoreOuterToPark
                            +Arm.close
                            +Claw.close
                            +Lift.toIntake
                        }
                    }
                ), Pair(
                    Constants.Color.RED,
                    sequential {
                        // Red side ("left" is on the front side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageFrontSpikeTapeToScore)
                            +Lift.toLow
                            +Arm.open
                        }
                        +Claw.drop
                        +parallel {
                            +SharedRoutines.scoreInnerToPark
                            +Arm.close
                            +Claw.close
                            +Lift.toIntake
                        }
                    }
                ))

        }

    val backstageCenterRoutine: Command
        get() = sequential {
            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToCenterSpikeTape)
            +Intake.slowEject
            +parallel {
                +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageCenterSpikeTapeToScore)
                +Lift.toLow
                +Arm.open
            }
            +Claw.drop
            +parallel {
                +SharedRoutines.scoreCenterToPark
                +Arm.close
                +Claw.close
                +Lift.toIntake
            }
        }

    val backstageRightRoutine: Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageFrontSpikeTapeToScore)
                            +Lift.toLow
                            +Arm.open
                        }
                        +Claw.drop
                        +parallel {
                            +SharedRoutines.scoreInnerToPark
                            +Arm.close
                            +Claw.close
                            +Lift.toIntake
                        }
                    }
                ), Pair(Constants.Color.RED,
                    sequential {
                        // Blue side ("left" is on the back side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToBackSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageBackSpikeTapeToScore)
                            +Lift.toLow
                            +Arm.open
                        }
                        +Claw.drop
                        +parallel {
                            +SharedRoutines.scoreOuterToPark
                            +Arm.close
                            +Claw.close
                            +Lift.toIntake
                        }
                    })
            )
        }
}