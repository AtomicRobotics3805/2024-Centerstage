package org.firstinspires.ftc.teamcode.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.PropProcessor
import org.firstinspires.ftc.teamcode.trajectoryFactory.CompetitionTrajectoryFactory

object BackstageRoutines {
    val backstageFullPathAndPark : Command
        get() = sequential {
            +OptionCommand("Different signature",
                { DetectionMechanism.selectedPosition },
                Pair(PropProcessor.Selected.LEFT, backstageLeftRoutine),
                Pair(PropProcessor.Selected.MIDDLE, backstageCenterRoutine),
                Pair(PropProcessor.Selected.RIGHT, backstageRightRoutine))
        }

    val backstageLeftRoutine : Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        // Blue side ("left" is on the back side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToBackSpikeTape)
                        // +intake.spikeTapeEject
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageBackSpikeTapeToScore)
                        // Score the yellow pixel
                        +SharedRoutines.scoreOuterToPark
                    }
                ), Pair(
                    Constants.Color.RED,
                    sequential {
                        // Red side ("left" is on the front side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape)
                        // Score purple pixel
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageFrontSpikeTapeToScore)
                        // Score yellow pixel
                        +SharedRoutines.scoreInnerToPark
                    }
                ))

        }

    val backstageCenterRoutine : Command
        get() = sequential {
            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToCenterSpikeTape)
            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageCenterSpikeTapeToScore)
            +SharedRoutines.scoreCenterToPark
        }

    val backstageRightRoutine : Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        // Blue side ("right" is on the front side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToFrontSpikeTape)
                        // +intake.spikeTapeEjects
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageFrontSpikeTapeToScore)
                        +SharedRoutines.scoreInnerToPark
                    }
                ), Pair(Constants.Color.RED,
                    sequential {
                        // Red side ("right" is on the back side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageStartToBackSpikeTape)
                        // +intake.spikeTapeEject
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.backstageBackSpikeTapeToScore)
                        +SharedRoutines.scoreOuterToPark
                    })
            )
        }
}