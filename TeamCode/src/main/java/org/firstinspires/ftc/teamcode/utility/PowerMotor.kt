package org.firstinspires.ftc.teamcode.utility

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.RobotLog
import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.hardware.MotorEx
import org.atomicrobotics3805.cflib.subsystems.Subsystem

class PowerMotor(
    private val motor: MotorEx,
    private val power: Double,
    private val mode: DcMotor.RunMode? = null,
    override val requirements: List<Subsystem> = arrayListOf(),
    override val interruptible: Boolean = true,
    private val logData: Boolean = false
) : Command() {

    override val _isDone: Boolean
        get() = true
    override fun start() {
        if (mode != null) {
            motor.mode = mode
        }
        motor.power = power
        if(logData) {
            RobotLog.i("PowerMotor", power)
        }

    }
}