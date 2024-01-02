package org.firstinspires.ftc.teamcode.interleague.controls

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
import org.firstinspires.ftc.teamcode.utility.DriverControlled

class CompetitionControls: Controls() {
    override fun registerCommands() {
        val dc = DriverControlled(Constants.opMode.gamepad1, pov = DriverControlled.POV.FIELD_CENTRIC, reverseStrafe = CompetitionDriveConstants.REVERSE_STRAFE, reverseStraight = CompetitionDriveConstants.REVERSE_STRAIGHT, reverseTurn = CompetitionDriveConstants.REVERSE_TURN)
        CommandScheduler.scheduleCommand(dc)
        CommandScheduler.scheduleCommand(Lift.ManualControl(gamepad2)) // manual control using gamepad2.rightStick.y

        gamepad2.rightTrigger.pressedCommand = { Intake.start }
        gamepad2.rightTrigger.releasedCommand = { Intake.stop }
        gamepad1.rightTrigger.pressedCommand = { Intake.start }
        gamepad1.rightTrigger.releasedCommand = { Intake.stop }

        gamepad2.leftTrigger.pressedCommand = { Intake.eject }
        gamepad2.leftTrigger.releasedCommand = { Intake.stop }
        gamepad1.leftTrigger.pressedCommand = { Intake.eject }
        gamepad1.leftTrigger.releasedCommand = { Intake.stop }


        gamepad2.dpadDown.pressedCommand = { sequential {
            +parallel {
                +NewLid.open
                +Arm.close
                +Lift.toIntake
            }
        } }

        gamepad2.dpadUp.pressedCommand = { parallel {
            +sequential {
                +Lift.toHigh
            }
            +sequential {
                +Delay(1.0)
                +Arm.open
            }
        } }

        gamepad2.dpadLeft.pressedCommand = { parallel {
            +sequential {
                +Lift.toLow
            }
            +sequential {
                +Delay(0.5)
                +Arm.open
            }
        } }

        gamepad1.a.pressedCommand = { NewLid.open }
        gamepad1.rightBumper.pressedCommand = { Constants.drive.switchSpeed() }
        gamepad1.rightBumper.releasedCommand = { Constants.drive.switchSpeed() }
        gamepad1.leftBumper.pressedCommand = { NewLid.open }
        gamepad1.y.pressedCommand = { Arm.open }
        gamepad1.b.pressedCommand = { Arm.close }
        gamepad2.a.pressedCommand = { NewLid.open }
        gamepad2.x.pressedCommand = { NewLid.open }
        gamepad2.leftBumper.pressedCommand = { NewLid.open }
        gamepad2.a.pressedCommand = { NewLid.open }
        gamepad2.y.pressedCommand = { Arm.open }
        gamepad2.b.pressedCommand = { Arm.close }
        gamepad2.leftStick.button.pressedCommand = { Drone.launch }
        gamepad2.a.pressedCommand = { Drone.launch }
        gamepad2.rightBumper.pressedCommand = { TeleOpRoutines.ejectRoutine }
        gamepad1.x.pressedCommand = { dc.resetRotation }
        gamepad2.rightStick.button.pressedCommand = { NewLid.close }

        // Ramp controls
        gamepad1.dpadDown.pressedCommand = { Ramp.intake }
        gamepad1.dpadLeft.pressedCommand = { Ramp.oneHigh }
        gamepad1.dpadUp.pressedCommand = { Ramp.threeHigh }
        gamepad1.dpadRight.pressedCommand = { Ramp.fullHigh } // For hanging
    }
}