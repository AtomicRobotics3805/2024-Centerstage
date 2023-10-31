package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.RobotLog
import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.hardware.MotorEx
import org.atomicrobotics3805.cflib.subsystems.PowerMotor
import org.atomicrobotics3805.cflib.subsystems.Subsystem

object Intake : Subsystem {
    @JvmField
    var  NAME = "intake"

    @JvmField
    var DIRECTION = DcMotorSimple.Direction.FORWARD

    @JvmField
    var SPEED = 0.8

    @JvmField
    var EJECTION_SPEED = 0.8

    val MOTOR = MotorEx(NAME, MotorEx.MotorType.ANDYMARK_NEVEREST, 3.7, DIRECTION)

    val start : Command
        get() = PowerMotor(MOTOR, SPEED, requirements = listOf(this@Intake), logData = true)
    val stop : Command
        get() = PowerMotor(MOTOR, 0.0, requirements = listOf(this@Intake), logData = true)
    val reverse: Command
        get() = PowerMotor(MOTOR, -SPEED, requirements = listOf(this@Intake), logData = true)
    val eject: Command
        get() = PowerMotor(MOTOR, -EJECTION_SPEED, requirements = listOf(this@Intake), logData = true)

    override fun initialize() {
        MOTOR.initialize()
        MOTOR.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }
}

