package org.firstinspires.ftc.teamcode.interleague.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import org.firstinspires.ftc.teamcode.interleague.trajectoryFactory.CompetitionTrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.PropProcessor
import org.firstinspires.ftc.teamcode.mechanisms.NewLid
import org.firstinspires.ftc.teamcode.mechanisms.VerticalIntake

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
                        +NewLid.close
                        // Blue side ("left" is on the back side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToBackSpikeTape)
                        +VerticalIntake.slowEject
                        +VerticalIntake.stop
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageBackSpikeTapeToScore)
                            +Lift.toLow
                            +Arm.open
                        }
                        +NewLid.open
                        +TelemetryCommand(3.0, "Waiting 3 seconds")
                        +parallel {
                            +SharedRoutines.scoreOuterToPark
                            +Arm.close
                            +sequential {
                                +Delay(0.3)
                                +Lift.toIntake
                            }                        }
                        +NewLid.close
                    }
                ), Pair(
                    Constants.Color.RED,
                    sequential {
                        +NewLid.close
                        // Red side ("left" is on the front side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape2)
                        +VerticalIntake.slowEject
                        +VerticalIntake.stop
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageFrontSpikeTapeToScore)
                            +sequential {
                                +Delay(2.0)
                                +Lift.toLow
                            }
                            +Arm.open
                        }
                        +NewLid.open
                        +parallel {
                            +SharedRoutines.scoreInnerToPark
                            +Arm.close
                            +sequential {
                                +Delay(0.3)
                                +Lift.toIntake
                            }                        }
                        +NewLid.close
                    }
                ))

        }

    val backstageCenterRoutine: Command
        get() = sequential {
            +NewLid.close
            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToCenterSpikeTape)
            +VerticalIntake.slowEject
            +VerticalIntake.stop
            +parallel {
                +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageCenterSpikeTapeToScore)
                +Lift.toLow
                +Arm.open
            }
            +NewLid.open
            +parallel {
                +SharedRoutines.scoreCenterToPark
                +Arm.close
                +sequential {
                    +Delay(0.3)
                    +Lift.toIntake
                }
            }
            +NewLid.close
        }

    val backstageRightRoutine: Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        +NewLid.close
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape2)
                        +VerticalIntake.slowEject
                        +VerticalIntake.stop
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageFrontSpikeTapeToScore)
                            +Lift.toLow
                            +Arm.open
                        }
                        +NewLid.open
                        +parallel {
                            +SharedRoutines.scoreInnerToPark
                            +Arm.close
                            +sequential {
                                +Delay(0.3)
                                +Lift.toIntake
                            }                        }
                        +NewLid.close
                    }
                ), Pair(Constants.Color.RED,
                    sequential {
                        +NewLid.close
                        // Blue side ("left" is on the back side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToBackSpikeTape)
                        +VerticalIntake.slowEject
                        +VerticalIntake.stop
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageBackSpikeTapeToScore)
                            +Lift.toLow
                            +Arm.open
                        }
                        +NewLid.open
                        +parallel {
                            +SharedRoutines.scoreOuterToPark
                            +Arm.close
                            +sequential {
                                +Delay(0.3)
                                +Lift.toIntake
                            }                        }
                        +NewLid.close
                    })
            )
        }
}