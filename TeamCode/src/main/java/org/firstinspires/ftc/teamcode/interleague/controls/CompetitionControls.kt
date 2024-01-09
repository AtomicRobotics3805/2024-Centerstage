package org.firstinspires.ftc.teamcode.interleague.controls

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.controls.Controls
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Drone
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.Ramp
import org.firstinspires.ftc.teamcode.leagues.routines.TeleOpRoutines
import org.firstinspires.ftc.teamcode.mechanisms.NewLid
import org.firstinspires.ftc.teamcode.mechanisms.VerticalIntake
import org.firstinspires.ftc.teamcode.utility.DriverControlled

class CompetitionControls: Controls() {
    val useFancyIntake = true
    override fun registerCommands() {
        val dc = DriverControlled(Constants.opMode.gamepad1, pov = DriverControlled.POV.FIELD_CENTRIC, reverseStrafe = CompetitionDriveConstants.REVERSE_STRAFE, reverseStraight = CompetitionDriveConstants.REVERSE_STRAIGHT, reverseTurn = CompetitionDriveConstants.REVERSE_TURN)
        CommandScheduler.scheduleCommand(dc)
        CommandScheduler.scheduleCommand(Lift.ManualControl(gamepad2)) // manual control using gamepad2.rightStick.y

        if (useFancyIntake) {
            CommandScheduler.scheduleCommand(IntakeControlManager())
        } else {
            gamepad2.rightTrigger.pressedCommand = { Intake.start }
            gamepad2.rightTrigger.releasedCommand = { Intake.stop }
            gamepad1.rightTrigger.pressedCommand = { Intake.start }
            gamepad1.rightTrigger.releasedCommand = { Intake.stop }
        }
        //region Ejection
        gamepad2.leftTrigger.pressedCommand = {
            parallel {
                +Intake.eject
                +VerticalIntake.eject
            } }
        gamepad2.leftTrigger.releasedCommand = {
            parallel {
                +Intake.stop
                +VerticalIntake.stop
            } }
        gamepad1.leftTrigger.pressedCommand = {
            parallel {
                +Intake.eject
                +VerticalIntake.eject
            } }
        gamepad1.leftTrigger.releasedCommand = {
            parallel {
                +Intake.stop
                +VerticalIntake.stop
            } }
        //endregion


        gamepad2.dpadDown.pressedCommand = { sequential {
            +parallel {
                +NewLid.open
                +Arm.close
                +Lift.toIntake
            }
        } }

        gamepad2.dpadUp.pressedCommand = { parallel {
            +NewLid.close
            +sequential {
                +Lift.toHigh
            }
            +sequential {
                +Delay(1.0)
                +Arm.open
            }
        } }

        gamepad2.dpadLeft.pressedCommand = { parallel {
            +NewLid.close
            +sequential {
                +Lift.toLow
            }
            +sequential {
                +Delay(0.5)
                +Arm.open
            }
        } }

        gamepad1.rightBumper.pressedCommand = { Constants.drive.switchSpeed() }
        gamepad1.rightBumper.releasedCommand = { Constants.drive.switchSpeed() }

        gamepad1.y.pressedCommand = { Arm.open }
        gamepad1.b.pressedCommand = { Arm.close }
        gamepad2.y.pressedCommand = { Arm.open }
        gamepad2.b.pressedCommand = { Arm.close }

        gamepad2.leftStick.button.pressedCommand = { Drone.launch }
        gamepad2.rightBumper.pressedCommand = { TeleOpRoutines.ejectRoutine }

        gamepad1.x.pressedCommand = { dc.resetRotation }

        // Ramp controls
        gamepad1.dpadDown.pressedCommand = { Ramp.intake }
        gamepad1.dpadLeft.pressedCommand = { Ramp.oneHigh }
        gamepad1.dpadUp.pressedCommand = { Ramp.threeHigh }
        gamepad1.dpadRight.pressedCommand = { Ramp.fullHigh } // For hanging

        // Lid controls
        gamepad2.a.pressedCommand = { NewLid.open }
        gamepad2.leftBumper.pressedCommand = { NewLid.close }

        gamepad1.a.pressedCommand = { NewLid.open }
        gamepad1.leftBumper.pressedCommand = { NewLid.close }
    }

    class IntakeControlManager: Command() {
        val minThreshold = 0.1
        val secondThreshold = 0.7

        override val _isDone = false

        var wasPressing = false

        override fun execute() {
            if (Constants.opMode.gamepad1.right_trigger > minThreshold || Constants.opMode.gamepad2.right_trigger > minThreshold) {
                // If we're holding it down at all, turn on the vertical intake
                CommandScheduler.scheduleCommand(VerticalIntake.start)
                wasPressing = true
            } else if (wasPressing) {
                // If we just stopped pressing it, stop both intakes
                CommandScheduler.scheduleCommand(VerticalIntake.stop)
                CommandScheduler.scheduleCommand(Intake.stop)
                wasPressing = false
                return;
            }

            if (Constants.opMode.gamepad1.right_trigger > secondThreshold || Constants.opMode.gamepad2.right_trigger > secondThreshold) {
                // If we're pressing it more than halfway, turn on noodle intake
                CommandScheduler.scheduleCommand(Intake.start)
            }
        }
    }
}