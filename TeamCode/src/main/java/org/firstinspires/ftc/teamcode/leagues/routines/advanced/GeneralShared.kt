package org.firstinspires.ftc.teamcode.leagues.routines.advanced

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.AutonomousGamepadSelection
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.leagues.trajectoryFactory.AdvancedTrajectoryFactory

object GeneralShared {
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
            +Lid.close
            +setParkTargetEdge
        }

    val initRoutineCenterPark: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Arm.close
            +Lid.close
            +setParkTargetCenter
        }

    val initRoutineGamepadPark: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Arm.close
            +Lid.close
            +AutonomousGamepadSelection.AutonomousGamepad()
        }

    val backdropOuterToPark: Command
        get() = sequential {
            +OptionCommand("",
                { parkTarget },
                Pair(
                    ParkTarget.EDGE, Constants.drive.followTrajectory(
                        AdvancedTrajectoryFactory.backdropOuterToParkOuter)),
                Pair(
                    ParkTarget.CENTER, Constants.drive.followTrajectory((
                        AdvancedTrajectoryFactory.backdropOuterToParkInner))))
        }
    val backdropCenterToPark: Command
        get() = sequential {
            +OptionCommand("",
                { parkTarget },
                Pair(
                    ParkTarget.EDGE, Constants.drive.followTrajectory(
                        AdvancedTrajectoryFactory.backdropCenterToParkOuter)),
                Pair(
                    ParkTarget.CENTER,
                    Constants.drive.followTrajectory((AdvancedTrajectoryFactory.backdropCenterToParkInner))))
        }
    val backdropInnerToPark: Command
        get() = sequential {
            +OptionCommand("",
                { parkTarget },
                Pair(
                    ParkTarget.EDGE, Constants.drive.followTrajectory(
                        AdvancedTrajectoryFactory.backdropInnerToParkOuter)),
                Pair(
                    ParkTarget.CENTER, Constants.drive.followTrajectory((
                            AdvancedTrajectoryFactory.backdropInnerToParkInner))))
        }
}