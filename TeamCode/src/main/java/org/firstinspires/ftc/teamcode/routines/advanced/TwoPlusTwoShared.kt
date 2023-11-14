package org.firstinspires.ftc.teamcode.routines.advanced

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.routines.old.SharedRoutines
import org.firstinspires.ftc.teamcode.trajectoryFactory.AdvancedTrajectoryFactory
import org.firstinspires.ftc.teamcode.utility.SwitchCommand

object TwoPlusTwoShared {
    enum class ParkTarget {
        EDGE,
        CENTER,
        NONE
    }

    var parkTarget: ParkTarget = ParkTarget.NONE

    val setParkTargetCenter: Command
        get() = CustomCommand(_start = { parkTarget = ParkTarget.CENTER })
    val setParkTargetEdge: Command
        get() = CustomCommand(_start = { parkTarget = ParkTarget.EDGE })

    val initRoutineEdgePark: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Arm.close
            +Claw.close
            +setParkTargetEdge
        }

    val initRoutineCenterPark: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Arm.close
            +Claw.close
            +setParkTargetCenter
        }

    val backdropOuterToPark: Command
        get() = sequential {
            +SwitchCommand({ SharedRoutines.parkTarget },
                Pair(SharedRoutines.ParkTarget.EDGE, Constants.drive.followTrajectory(
                        AdvancedTrajectoryFactory.backdropOuterToParkOuter)),
                Pair(SharedRoutines.ParkTarget.CENTER, Constants.drive.followTrajectory((
                        AdvancedTrajectoryFactory.backdropOuterToParkInner))))
        }
    val backdropCenterToPark: Command
        get() = sequential {
            +SwitchCommand({ SharedRoutines.parkTarget },
                Pair(SharedRoutines.ParkTarget.EDGE, Constants.drive.followTrajectory(
                        AdvancedTrajectoryFactory.backdropCenterToParkOuter)),
                Pair(SharedRoutines.ParkTarget.CENTER,
                    Constants.drive.followTrajectory((AdvancedTrajectoryFactory.backdropCenterToParkInner))))
        }
    val backdropInnerToPark: Command
        get() = sequential {
            +SwitchCommand({ SharedRoutines.parkTarget },
                Pair(SharedRoutines.ParkTarget.EDGE, Constants.drive.followTrajectory(
                        AdvancedTrajectoryFactory.backdropInnerToParkOuter)),
                Pair(SharedRoutines.ParkTarget.CENTER, Constants.drive.followTrajectory((
                            AdvancedTrajectoryFactory.backdropInnerToParkInner))))
        }
}