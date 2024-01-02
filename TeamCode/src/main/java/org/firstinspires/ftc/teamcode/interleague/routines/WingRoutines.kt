package org.firstinspires.ftc.teamcode.interleague.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.firstinspires.ftc.teamcode.interleague.trajectoryFactory.CompetitionTrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.PropProcessor
import org.firstinspires.ftc.teamcode.mechanisms.NewLid

object WingRoutines {
    val wingFullPathAndPark: Command
        get() = sequential {
            +Delay(12.0)
            +OptionCommand("Different signature",
                { DetectionMechanism.selectedPosition },
                Pair(PropProcessor.Selected.LEFT, wingLeftRoutine),
                Pair(PropProcessor.Selected.MIDDLE, wingCenterRoutine),
                Pair(PropProcessor.Selected.RIGHT, wingRightRoutine))
        }

    val wingLeftRoutine: Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToBackSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingBackSpikeTapeToScore)
                            +sequential {
                                +Delay(5.0)
                                +Lift.toLow
                            }
                            +Arm.open
                        }
                        +NewLid.open
                        +parallel {
                            +SharedRoutines.scoreOuterToPark
                            +sequential {
                                Delay(1.0)
                                +Arm.close
                            }
                            +Lift.toLow
                            +sequential {
                                +Delay(1.0)
                                +Lift.toIntake
                            }
                        }
                        +NewLid.close
                    }
                ), Pair(
                    Constants.Color.RED,
                    sequential {
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToFrontSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingFrontSpikeTapeToScore)
                            +sequential {
                                +Delay(5.0)
                                +Lift.toLow
                            }
                            +Arm.open
                        }
                        +NewLid.open
                        +parallel {
                            +SharedRoutines.scoreInnerToPark
                            +sequential {
                                Delay(1.0)
                                +Arm.close
                            }
                            +Lift.toLow
                            +sequential {
                                +Delay(1.0)
                                +Lift.toIntake
                            }
                        }
                        +NewLid.close
                    }
                )
            )
        }

    val wingCenterRoutine: Command
        get() = sequential {
            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToCenterSpikeTape)
            +Intake.slowEject
            +parallel {
                +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingCenterSpikeTapeToScore)
                +sequential {
                    +Delay(5.0)
                    +Lift.toLow
                }
                +Arm.open
            }
            +NewLid.open
            +parallel {
                +SharedRoutines.scoreCenterToPark
                +sequential {
                    Delay(1.0)
                    +Arm.close
                }
                +Lift.toLow
                +sequential {
                    +Delay(1.0)
                    +Lift.toIntake
                }
            }
            +NewLid.close
        }

    val wingRightRoutine: Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToFrontSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingFrontSpikeTapeToScore)
                            +sequential {
                                +Delay(5.0)
                                +Lift.toLow
                            }
                            +Arm.open
                        }
                        +NewLid.open
                        +parallel {
                            +SharedRoutines.scoreInnerToPark
                            +sequential {
                                Delay(1.0)
                                +Arm.close
                            }
                            +Lift.toLow
                            +sequential {
                                +Delay(1.0)
                                +Lift.toIntake
                            }
                        }
                        +NewLid.close
                    }
                ), Pair(Constants.Color.RED,
                    sequential {
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToBackSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingBackSpikeTapeToScore)
                            +sequential {
                                +Delay(5.0)
                                +Lift.toLow
                            }
                            +Arm.open
                        }
                        +NewLid.open
                        +parallel {
                            +SharedRoutines.scoreOuterToPark
                            +sequential {
                                Delay(1.0)
                                +Arm.close
                            }
                            +Lift.toLow
                            +sequential {
                                +Delay(1.0)
                                +Lift.toIntake
                            }
                        }
                        +NewLid.close
                    })
            )
        }
}