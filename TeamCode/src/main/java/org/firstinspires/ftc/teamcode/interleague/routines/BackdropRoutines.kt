package org.firstinspires.ftc.teamcode.interleague.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import org.firstinspires.ftc.teamcode.interleague.trajectories.InterleagueTrajectoryFactory
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.PropProcessor

object BackdropRoutines {
    val backdropFifty: Command
        get() = sequential {
            +CustomCommand(_execute = {
                if(Constants.color == Constants.Color.RED) {
                    when (DetectionMechanism.selectedPosition) {
                        PropProcessor.Selected.RIGHT -> {
                            CommandScheduler.scheduleCommand(backdropFiftyBack)
                        }
                        PropProcessor.Selected.MIDDLE -> {
                            CommandScheduler.scheduleCommand(backdropFiftyCenter)
                        }
                        PropProcessor.Selected.LEFT -> {
                            CommandScheduler.scheduleCommand(backdropFiftyFront)
                        }

                        else -> {
                            CommandScheduler.scheduleCommand(TelemetryCommand(10.0, { "There was an issue selecting the routine. "} ))
                        }
                    }
                }
                else if (Constants.color == Constants.Color.BLUE) {
                    when (DetectionMechanism.selectedPosition) {
                        PropProcessor.Selected.RIGHT -> {
                            CommandScheduler.scheduleCommand(backdropFiftyFront)
                        }
                        PropProcessor.Selected.MIDDLE -> {
                            CommandScheduler.scheduleCommand(backdropFiftyCenter)
                        }
                        PropProcessor.Selected.LEFT -> {
                            CommandScheduler.scheduleCommand(backdropFiftyBack)
                        }

                        else -> {
                            CommandScheduler.scheduleCommand(TelemetryCommand(10.0, { "There was an issue selecting the routine. "}))
                        }
                    }
                }
            })
        }

    val backdropFiftyFront: Command
        get() = sequential {
            +Lid.close
            +Constants.drive.followTrajectory(InterleagueTrajectoryFactory.backdropStartToFront)
            +Intake.slowEject
            +parallel {
                +Constants.drive.followTrajectory(InterleagueTrajectoryFactory.backdropFrontTapeToScoreInner)
                +sequential {
                    +Delay(5.0)
                    +Lift.toLow
                }
                +Arm.open
            }
            +Lid.intake
            +parallel {
                TODO("Score inner to park routine")
                +Arm.close
                +Lift.toIntake
            }
            +Lid.close
        }

    val backdropFiftyCenter: Command
        get() = sequential {
            +Lid.close
            +Constants.drive.followTrajectory(InterleagueTrajectoryFactory.backdropStartToCenter)
            +Intake.slowEject
            +parallel {
                +Constants.drive.followTrajectory(InterleagueTrajectoryFactory.backdropCenterTapeToScoreCenter)
                +Lift.toLow
                +Arm.open
            }
            +Lid.intake
            +parallel {
                TODO("Score center to park routine")
                +Arm.close
                +Lift.toIntake
            }
            +Lid.close
        }

    val backdropFiftyBack: Command
        get() = sequential {
            +Lid.close
            +Constants.drive.followTrajectory(InterleagueTrajectoryFactory.backdropStartToBack)
            +Intake.slowEject
            +parallel {
                +Constants.drive.followTrajectory(InterleagueTrajectoryFactory.backdropBackTapeToScoreOuter)
                +Lift.toLow
                +Arm.open
            }
            +Lid.intake
            +parallel {
                TODO("Score outer to park")
                +Arm.close
                +Lift.toIntake
            }
            +Lid.close
        }
}