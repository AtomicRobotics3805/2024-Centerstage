package org.firstinspires.ftc.teamcode.routines.old

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
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
                        +Claw.close
                        // Blue side ("left" is on the back side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToBackSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageBackSpikeTapeToScore)
                            +Lift.toAuto
                            +Arm.open
                        }
                        +Claw.intake
                        +TelemetryCommand(3.0, "Waiting 3 seconds")
                        +parallel {
                            +SharedRoutines.scoreOuterToPark
                            +Arm.close
                            +Lift.toIntake
                        }
                        +Claw.close
                    }
                ), Pair(
                    Constants.Color.RED,
                    sequential {
                        +Claw.close
                        // Red side ("left" is on the front side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageFrontSpikeTapeToScore)
                            +sequential {
                                +Delay(5.0)
                                +Lift.toAuto
                            }
                            +Arm.open
                        }
                        +Claw.intake
                        +parallel {
                            +SharedRoutines.scoreInnerToPark
                            +Arm.close
                            +Lift.toIntake
                        }
                        +Claw.close
                    }
                ))

        }

    val backstageCenterRoutine: Command
        get() = sequential {
            +Claw.close
            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToCenterSpikeTape)
            +Intake.slowEject
            +parallel {
                +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageCenterSpikeTapeToScore)
                +Lift.toAuto
                +Arm.open
            }
            +Claw.intake
            +parallel {
                +SharedRoutines.scoreCenterToPark
                +Arm.close
                +Lift.toIntake
            }
            +Claw.close
        }

    val backstageRightRoutine: Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        +Claw.close
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageFrontSpikeTapeToScore)
                            +sequential {
                                +Delay(5.0)
                                +Lift.toAuto
                            }
                            +Arm.open
                        }
                        +Claw.intake
                        +parallel {
                            +SharedRoutines.scoreInnerToPark
                            +Arm.close
                            +Lift.toIntake
                        }
                        +Claw.close
                    }
                ), Pair(Constants.Color.RED,
                    sequential {
                        +Claw.close
                        // Blue side ("left" is on the back side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToBackSpikeTape)
                        +Intake.slowEject
                        +parallel {
                            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageBackSpikeTapeToScore)
                            +Lift.toAuto
                            +Arm.open
                        }
                        +Claw.intake
                        +parallel {
                            +SharedRoutines.scoreOuterToPark
                            +Arm.close
                            +Lift.toIntake
                        }
                        +Claw.close
                    })
            )
        }
}