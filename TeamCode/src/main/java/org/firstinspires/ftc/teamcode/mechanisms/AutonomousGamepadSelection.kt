package org.firstinspires.ftc.teamcode.mechanisms

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.leagues.routines.advanced.GeneralShared

object AutonomousGamepadSelection: Subsystem {
    class AutonomousGamepad: Command() {
        override val _isDone
            get() = Constants.opMode.isStopRequested || Constants.opMode.isStarted

        override fun execute() {
            TelemetryController.telemetry.addData("A", "Park target CENTER")
            TelemetryController.telemetry.addData("B", "Park target EDGE")
            TelemetryController.telemetry.addData("Current selection", GeneralShared.parkTarget)
            TelemetryController.telemetry.update()
            if(Constants.opMode.gamepad1.a) {
                GeneralShared.parkTarget = GeneralShared.ParkTarget.CENTER
            }
            else if(Constants.opMode.gamepad1.b) {
                GeneralShared.parkTarget = GeneralShared.ParkTarget.EDGE
            }
        }
    }
}