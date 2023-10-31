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

object WingRoutines {
    val wingFullPathAndPark : Command
        get() = sequential {
            +OptionCommand("Different signature",
                { DetectionMechanism.selectedPosition },
                Pair(PropProcessor.Selected.LEFT, wingLeftRoutine),
                Pair(PropProcessor.Selected.MIDDLE, wingCenterRoutine),
                Pair(PropProcessor.Selected.RIGHT, wingRightRoutine))
        }

    val wingLeftRoutine : Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToBackSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingBackSpikeTapeToScore)
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
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToFrontSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingFrontSpikeTapeToScore)
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
                )
            )
        }

    val wingCenterRoutine : Command
        get() = sequential {
            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToCenterSpikeTape)
            +Intake.slowEject
            +parallel {
                +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingCenterSpikeTapeToScore)
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

    val wingRightRoutine : Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToFrontSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingFrontSpikeTapeToScore)
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
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToBackSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingBackSpikeTapeToScore)
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