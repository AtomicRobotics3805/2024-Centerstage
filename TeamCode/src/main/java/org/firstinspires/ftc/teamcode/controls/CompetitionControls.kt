package org.firstinspires.ftc.teamcode.controls

import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.controls.Controls
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift

class CompetitionControls : Controls() {
    override fun registerCommands() {
        CommandScheduler.scheduleCommand(Constants.drive.driverControlled(Constants.opMode.gamepad1))

        gamepad2.rightTrigger.pressedCommand = { Intake.start }
        gamepad2.rightTrigger.releasedCommand = { Intake.stop }

        gamepad2.dpadDown.pressedCommand = { Lift.toBottom }

        // Lift automatic and manual controls
        gamepad2.leftTrigger.pressedCommand = { Lift.manualControl }
        gamepad2.leftTrigger.releasedCommand = { Lift.automaticControl }
        gamepad2.dpadUp.pressedCommand = { Lift.dpadUp }
        gamepad2.dpadRight.pressedCommand = { Lift.dpadLeft }
        gamepad2.dpadUp.releasedCommand = { Lift.stop }
        gamepad2.dpadDown.releasedCommand = { Lift.stop }

        gamepad2.leftBumper.pressedCommand = { Claw.open }
        gamepad2.rightBumper.pressedCommand = { Claw.close }
        gamepad2.y.pressedCommand = { Arm.open }
        gamepad2.b.pressedCommand = { Arm.close }
    }
}