package org.firstinspires.ftc.teamcode.mechanisms

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.hardware.ServoEx
import org.atomicrobotics3805.cflib.subsystems.MoveServo
import org.atomicrobotics3805.cflib.subsystems.Subsystem

object NewLid: Subsystem {
    val name = "claw"
    val openPos = 0.5
    val closePos = 0.2

    val time = 1.0
    val clawServo = ServoEx(name)

    val open: Command
        get() = MoveServo(clawServo, openPos, time, listOf(this))
    val close: Command
        get() = MoveServo(clawServo, closePos, time, listOf(this))

    override fun initialize() {
        clawServo.initialize()
    }
}