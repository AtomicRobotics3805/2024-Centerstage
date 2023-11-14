package org.firstinspires.ftc.teamcode.controls

import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.controls.Controls
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Drone
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.routines.TeleOpRoutines
import org.firstinspires.ftc.teamcode.utility.DriverControlled

class CompetitionControls: Controls() {
    override fun registerCommands() {
        val dc = DriverControlled(Constants.opMode.gamepad1, pov = DriverControlled.POV.FIELD_CENTRIC, reverseStrafe = CompetitionDriveConstants.REVERSE_STRAFE, reverseStraight = CompetitionDriveConstants.REVERSE_STRAIGHT, reverseTurn = CompetitionDriveConstants.REVERSE_TURN)
        CommandScheduler.scheduleCommand(dc)
        CommandScheduler.scheduleCommand(Lift.ManualControl(gamepad2)) // manual control using gamepad2.rightStick.y

        gamepad2.rightTrigger.pressedCommand = { parallel {
            +Claw.intake
            +sequential {
                +Delay(0.3)
                +Intake.start
            }
        } }
        gamepad2.rightTrigger.releasedCommand = { parallel {
            +sequential {
                +Delay(0.3)
                +Intake.stop
            }
            +Claw.close
        } }
        gamepad1.rightTrigger.pressedCommand = { parallel {
            +Claw.intake
            +sequential {
                +Delay(0.1)
                +Intake.start
            }
        } }
        gamepad1.rightTrigger.releasedCommand = { parallel {
            +sequential {
                +Delay(0.1)
                +Intake.stop
            }
            +Claw.close
        } }

        gamepad2.leftTrigger.pressedCommand = { parallel {
            +Intake.eject
            +Claw.intake
        } }
        gamepad2.leftTrigger.releasedCommand = { parallel {
            +Intake.stop
            +Claw.close
        } }
        gamepad1.leftTrigger.pressedCommand = { parallel {
            +Intake.eject
            +Claw.intake
        } }
        gamepad1.leftTrigger.releasedCommand = { parallel {
            +Intake.stop
            +Claw.close
        } }


        gamepad2.dpadDown.pressedCommand = { sequential {
            +parallel {
                +Claw.intake
                +Arm.close
                +Lift.toIntake
            }
            +Claw.close
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

        gamepad1.a.pressedCommand = { Claw.drop }
        gamepad1.rightBumper.pressedCommand = { Constants.drive.switchSpeed() }
        gamepad1.rightBumper.releasedCommand = { Constants.drive.switchSpeed() }
        gamepad1.leftBumper.pressedCommand = { Claw.intake }
        gamepad1.y.pressedCommand = { Arm.open }
        gamepad1.b.pressedCommand = { Arm.close }
        gamepad2.a.pressedCommand = { Claw.drop }
        gamepad2.x.pressedCommand = { Claw.close }
        gamepad2.leftBumper.pressedCommand = { Claw.intake }
        gamepad2.a.pressedCommand = { Claw.drop }
        gamepad2.y.pressedCommand = { Arm.open }
        gamepad2.b.pressedCommand = { Arm.close }
//        gamepad2.dpadRight.pressedCommand = { Lift.toHang }
        gamepad2.leftStick.button.pressedCommand = { Drone.launch }
        gamepad2.rightBumper.pressedCommand = { TeleOpRoutines.ejectRoutine }
        gamepad1.x.pressedCommand = { dc.resetRotation }

    }
}