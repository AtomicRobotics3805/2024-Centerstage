package org.firstinspires.ftc.teamcode.controls

import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.controls.Controls
import org.atomicrobotics3805.cflib.driving.DriverControlled
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift

class CompetitionControls : Controls() {
    override fun registerCommands() {
        CommandScheduler.scheduleCommand(org.firstinspires.ftc.teamcode.utility.DriverControlled(Constants.opMode.gamepad1, pov = org.firstinspires.ftc.teamcode.utility.DriverControlled.POV.FIELD_CENTRIC, reverseStrafe = CompetitionDriveConstants.REVERSE_STRAFE, reverseStraight = CompetitionDriveConstants.REVERSE_STRAIGHT, reverseTurn = CompetitionDriveConstants.REVERSE_TURN))

        gamepad2.rightTrigger.pressedCommand = { parallel {
            +Claw.intake
            +Intake.start
        } }
        gamepad2.rightTrigger.releasedCommand = { parallel {
            +Intake.stop
            +Claw.close
        } }
        gamepad1.rightTrigger.pressedCommand = { parallel {
            +Claw.intake
            +Intake.start
        } }
        gamepad1.rightTrigger.releasedCommand = { parallel {
            +Intake.stop
            +Claw.close
        } }

        gamepad2.leftTrigger.pressedCommand = { Intake.eject }
        gamepad2.leftTrigger.releasedCommand = { Intake.stop }
        gamepad1.leftTrigger.pressedCommand = { Intake.eject }
        gamepad1.leftTrigger.releasedCommand = { Intake.stop }


        gamepad2.dpadDown.pressedCommand = { sequential {
            +parallel {
                +Claw.intake
                +Arm.close
                +Lift.toBottom
            }
            +Delay(1.0)
            +Claw.close
        } }

        // Lift automatic and manual controls
//        gamepad2.leftTrigger.pressedCommand = { Lift.manualControl }
//        gamepad2.leftTrigger.releasedCommand = { Lift.automaticControl }
        gamepad2.dpadUp.pressedCommand = { Lift.toHigh }
        gamepad2.dpadLeft.pressedCommand = { Lift.toMiddle }

        gamepad2.leftBumper.pressedCommand = { Claw.intake }
        gamepad1.a.pressedCommand = { Claw.drop }
        gamepad2.a.pressedCommand = { Claw.drop }
        gamepad2.y.pressedCommand = { Arm.open }
        gamepad2.b.pressedCommand = { Arm.close }
        gamepad1.x.pressedCommand = { Constants.drive.switchSpeed() }
    }
}