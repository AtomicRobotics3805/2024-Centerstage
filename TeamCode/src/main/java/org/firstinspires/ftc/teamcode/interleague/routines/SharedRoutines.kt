package org.firstinspires.ftc.teamcode.interleague.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.firstinspires.ftc.teamcode.interleague.trajectoryFactory.CompetitionTrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.NewLid

object SharedRoutines {

    enum class ParkTarget {
        CENTER,
        EDGE,
        NONE
    }

    var parkTarget: ParkTarget = ParkTarget.NONE

    // Set the park target; will get triggered by the gamepad in initialization of auton (?), either way minimizes the # of routines
    val setParkTargetCenter: Command
        get() = CustomCommand(_start = { parkTarget = ParkTarget.CENTER })
    val setParkTargetEdge: Command
        get() = CustomCommand(_start = { parkTarget = ParkTarget.EDGE })


    val initRoutineEdgePark: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Arm.close
            +NewLid.close
            +setParkTargetEdge
        }

    val initRoutineCenterPark: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Arm.close
            +NewLid.close
            +setParkTargetCenter
        }



    val scoreInnerToPark: Command
        get() = sequential {
            +OptionCommand("Different signature", { parkTarget },
                Pair(ParkTarget.EDGE, Constants.drive.followTrajectory(CompetitionTrajectoryFactory.scoreInnerToParkEdge)),
                Pair(ParkTarget.CENTER, Constants.drive.followTrajectory((CompetitionTrajectoryFactory.scoreInnerToParkCenter))))
        }
    
    val scoreCenterToPark: Command
        get() = sequential {
            +OptionCommand("Different signature", { parkTarget },
                Pair(ParkTarget.EDGE, Constants.drive.followTrajectory(CompetitionTrajectoryFactory.scoreCenterToParkEdge)),
                Pair(ParkTarget.CENTER, Constants.drive.followTrajectory((CompetitionTrajectoryFactory.scoreCenterToParkCenter))))
        }

    val scoreOuterToPark: Command
        get() = sequential {
            +OptionCommand("Different signature", { parkTarget },
                Pair(ParkTarget.EDGE, Constants.drive.followTrajectory(CompetitionTrajectoryFactory.scoreOuterToParkEdge)),
                Pair(ParkTarget.CENTER, Constants.drive.followTrajectory((CompetitionTrajectoryFactory.scoreOuterToParkCenter))))
        }
}