package org.firstinspires.ftc.teamcode.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
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
                        // Blue side (left is on the "BACK" side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToBackSpikeTape)
                        // +intake.spikeTapeEject
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingBackSpikeTapeToScore)
                        // Score the yellow pixel
                        +SharedRoutines.scoreOuterToPark
                    }
                ), Pair(
                    Constants.Color.RED,
                    sequential {
                        // Red side (left is on the "FRONT" side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToFrontSpikeTape)
                        // Score purple pixel
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingFrontSpikeTapeToScore)
                        // Score yellow pixel
                        +SharedRoutines.scoreInnerToPark
                    }
                )
            )
        }

    val wingCenterRoutine : Command
        get() = sequential {
            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToCenterSpikeTape)
            +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingCenterSpikeTapeToScore)
            +SharedRoutines.scoreCenterToPark
        }

    val wingRightRoutine : Command
        get() = sequential {
            +OptionCommand("Different signature", { Constants.color },
                Pair(
                    Constants.Color.BLUE,
                    sequential {
                        // Blue side ("right" is on the front side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToFrontSpikeTape)
                        // +intake.spikeTapeEjects
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingFrontSpikeTapeToScore)
                        +SharedRoutines.scoreInnerToPark
                    }
                ), Pair(Constants.Color.RED,
                    sequential {
                        // Red side ("right" is on the back side of the field)
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToBackSpikeTape)
                        // +intake.spikeTapeEject
                        +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingBackSpikeTapeToScore)
                        +SharedRoutines.scoreOuterToPark
                    })
            )
        }
}