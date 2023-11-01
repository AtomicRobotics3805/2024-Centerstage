package org.firstinspires.ftc.teamcode.mechanisms

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.hardware.ServoEx
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.subsystems.MoveServo
import org.atomicrobotics3805.cflib.subsystems.Subsystem
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand

object Drone: Subsystem {
    var NAME = "drone"
    var CLOSE_POSITION = 1.0
    var LAUNCH_POSITION = 0.0
    var TIME = 1.0
    val droneServo = ServoEx(NAME)
    var isPrimed: Boolean = false

    override fun initialize() {
        droneServo.initialize()
    }

    val reset: Command
        get() = MoveServo(droneServo, CLOSE_POSITION, TIME)
    val launch: Command
        get() = if (isPrimed){ MoveServo(droneServo, LAUNCH_POSITION, TIME) } else TelemetryCommand(2.0, "Drone not primed yet.")
    val prime: Command
        get() = CustomCommand(_start = { isPrimed = true })
    val unPrime: Command
        get() = CustomCommand(_start = { isPrimed = false })

}