package org.firstinspires.ftc.teamcode.mechanisms

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.hardware.ServoEx
import org.atomicrobotics3805.cflib.subsystems.MoveServo
import org.atomicrobotics3805.cflib.subsystems.Subsystem
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand

object Drone: Subsystem {
    var NAME = "drone"
    var LAUNCH_POSITION = 0.4
    var TIME = 1.0
    val droneServo = ServoEx(NAME)
    var isPrimed: Boolean = false

    override fun initialize() {
        droneServo.initialize()
    }

    val launch: Command
        get() = MoveServo(droneServo, LAUNCH_POSITION, TIME, listOf(this@Drone))
}