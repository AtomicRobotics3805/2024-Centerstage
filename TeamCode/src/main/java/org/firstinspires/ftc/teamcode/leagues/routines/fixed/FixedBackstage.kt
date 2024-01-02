package org.firstinspires.ftc.teamcode.leagues.routines.fixed

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.PropProcessor
import org.firstinspires.ftc.teamcode.mechanisms.Ramp
import org.firstinspires.ftc.teamcode.leagues.trajectoryFactory.AdvancedTrajectoryFactory

object FixedBackstage {
    //region Parking
    enum class Target {
        CENTER,
        EDGE
    }

    var target: Target = Target.CENTER

    val targetCenter: Command
        get() = CustomCommand(_start = { target = Target.CENTER })
    val targetEdge: Command
        get() = CustomCommand(_start = { target = Target.EDGE })

    val outerToPark: Command
        get() = CustomCommand(_start = {
            if(target == Target.EDGE) {
                CommandScheduler.scheduleCommand(outerToParkOuter)
            } else if (target == Target.CENTER) {
                CommandScheduler.scheduleCommand(outerToParkInner)
            }
        })

    val centerToPark: Command
        get() = CustomCommand(_start = {
            if(target == Target.EDGE) {
                CommandScheduler.scheduleCommand(centerToParkOuter)
            } else if (target == Target.CENTER) {
                CommandScheduler.scheduleCommand(centerToParkInner)
            }
        })

    val innerToPark: Command
        get() = CustomCommand(_start = {
            if(target == Target.EDGE) {
                CommandScheduler.scheduleCommand(innerToParkOuter)
            } else if (target == Target.CENTER) {
                CommandScheduler.scheduleCommand(innerToParkInner)
            }
        })

    val outerToParkOuter: Command
        get() = Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropOuterToParkOuter)

    val outerToParkInner: Command
        get() = Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropOuterToParkInner)

    val centerToParkOuter: Command
        get() = Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropCenterToParkOuter)

    val centerToParkInner: Command
        get() = Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropCenterToParkInner)

    val innerToParkOuter: Command
        get() = Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropInnerToParkOuter)

    val innerToParkInner: Command
        get() = Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropInnerToParkInner)
    //endregion
    val initCenter: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Lid.close
            +Arm.close
            +Ramp.oneHigh
            +targetCenter
        }
    val initEdge: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Lid.close
            +Arm.close
            +Ramp.oneHigh
            +targetEdge
        }

    val backstageOpMode: Command
        get() = sequential {
            +CustomCommand(_start = {
                if (Constants.color == Constants.Color.RED) {
                    when (DetectionMechanism.selectedPosition) {
                        PropProcessor.Selected.LEFT -> {
                            CommandScheduler.scheduleCommand(nearRoutine)
                        }
                        PropProcessor.Selected.MIDDLE -> {
                            CommandScheduler.scheduleCommand(centerRoutine)
                        }
                        PropProcessor.Selected.RIGHT -> {
                            CommandScheduler.scheduleCommand(farRoutine)
                        }
                        else -> {
                            TelemetryController.telemetry.addLine("Couldn't determine team prop location.")
                            TelemetryController.telemetry.update()
                        }
                    }
                } else if (Constants.color == Constants.Color.BLUE) {
                when (DetectionMechanism.selectedPosition) {
                    PropProcessor.Selected.LEFT -> {
                        CommandScheduler.scheduleCommand(farRoutine)
                    }
                    PropProcessor.Selected.MIDDLE -> {
                        CommandScheduler.scheduleCommand(centerRoutine)
                    }
                    PropProcessor.Selected.RIGHT -> {
                        CommandScheduler.scheduleCommand(nearRoutine)
                    }
                    else -> {
                        TelemetryController.telemetry.addLine("Couldn't determine team prop location.")
                        TelemetryController.telemetry.update()
                    }
                }
            }
            })
        }

    val nearRoutine: Command
        get() = sequential {
            +Lid.close
            +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeFront)
            +Intake.slowEject
            +parallel {
                +Lift.toAuto
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeFrontToBackdropInner)
            }
            +scorePreload
            +parallel {
                +resetMechanisms
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropInnerToInnerStack)
                +Ramp.threeHigh
            }
            +intakeStack
            +parallel {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropOuter)
                +sequential {
                    +Delay(2.0)
                    +Lift.toAuto
                }
            }
            +scoreTwo
            +parallel {
                +resetMechanisms
                +Ramp.intake // Change if going for second cycle
                // Add driving to stack here if going for second cycle
                +outerToPark
            }
        }

    val centerRoutine: Command
        get() = sequential {
            +Lid.close
            +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeCenter)
            +Intake.slowEject
            +parallel {
                +Lift.toAuto
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeCenterToBackdropCenter)
            }
            +scorePreload
            +parallel {
                +resetMechanisms
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropCenterToInnerStack)
                +Ramp.threeHigh
            }
            +intakeStack
            +parallel {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropInner)
                +sequential {
                    +Delay(2.0)
                    +Lift.toAuto
                }
            }
            +scoreTwo
            +parallel {
                +resetMechanisms
                +Ramp.intake // Change if going for second cycle
                // Add driving to stack here if going for second cycle
                +innerToPark
            }
        }

    val farRoutine: Command
        get() = sequential {
            +Lid.close
            +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeBack)
            +Intake.slowEject
            +parallel {
                +Lift.toAuto
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeBackToBackdropOuter)
            }
            +scorePreload
            +parallel {
                +resetMechanisms
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropOuterToInnerStack)
                +Ramp.threeHigh
            }
            +intakeStack
            +parallel {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropInner)
                +sequential {
                    +Delay(2.0)
                    +Lift.toAuto
                }
            }
            +scoreTwo
            +parallel {
                +resetMechanisms
                +Ramp.intake // Change if going for second cycle
                // Add driving to stack here if going for second cycle
                +innerToPark
            }
        }

    val scorePreload: Command
        get() = sequential {
            +Arm.open
            +Lid.intake
            +Lift.toLow
        }

    val scoreTwo: Command
        get() = sequential {
            +Arm.open
            +Lid.drop
            +parallel {
                +Lid.slightlyBigger
                +Lift.toLow
            }
        }

    val resetMechanisms: Command
        get() = sequential {
            +parallel {
                +Arm.close
                +Lift.toIntake
            }
            +Lid.close
        }

    val intakeStack: Command
        get() = sequential {
            +Lid.intake
            +Intake.intakeStack
            +Lid.close
        }
}