package org.firstinspires.ftc.teamcode.mechanisms

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.hardware.ServoEx
import org.atomicrobotics3805.cflib.subsystems.MoveServo
import org.atomicrobotics3805.cflib.subsystems.Subsystem

object Ramp: Subsystem {
    var NAME = "ramp"
    var THREE_HIGH = 0.66
    var ONE_HIGH = 0.74
    var INTAKE = 0.8
    var FULL_UP = 1.0
    var TIME = 1.0
    val rampServo = ServoEx(NAME)

    override fun initialize() {
        rampServo.initialize()
    }

    val intake: Command
        get() = MoveServo(rampServo, INTAKE, TIME, listOf(this@Ramp))
    val oneHigh: Command
        get() = MoveServo(rampServo, ONE_HIGH, TIME, listOf(this@Ramp))
    val threeHigh: Command
        get() = MoveServo(rampServo, THREE_HIGH, TIME, listOf(this@Ramp))
    val fullHigh: Command
        get() = MoveServo(rampServo, FULL_UP, TIME, listOf(this@Ramp))
}