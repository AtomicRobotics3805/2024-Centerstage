package org.firstinspires.ftc.teamcode.mechanisms

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.hardware.MotorEx
import org.atomicrobotics3805.cflib.subsystems.MotorToPosition
import org.atomicrobotics3805.cflib.subsystems.PowerMotor
import org.atomicrobotics3805.cflib.subsystems.Subsystem

@Config
object Lift : Subsystem {
    @JvmField
    var NAME = "lift"

    @JvmField
    var INTAKE_POSITION = 0.5 // in
    var LOW_POSITION = 12.0 // in
    var HIGH_POSITION = 25.0 // in

    @JvmField
    var DIRECTION = DcMotorSimple.Direction.FORWARD
    @JvmField
    var SPEED = 1.0

    val MOTOR = MotorEx(NAME, MotorEx.MotorType.GOBILDA_YELLOWJACKET, 19.2, DIRECTION)

    private const val PULLEY_RADIUS = 0.5 // in
    private const val DRIVE_GEAR_REDUCTION = 1.0
    private var COUNTS_PER_INCH: Double = MOTOR.ticksPerRev * DRIVE_GEAR_REDUCTION / (2 * PULLEY_RADIUS * Math.PI)

    val toIntake: Command
        get() = MotorToPosition(MOTOR, (INTAKE_POSITION * COUNTS_PER_INCH).toInt(), SPEED, requirements = listOf(this@Lift))
    val toLow: Command
        get() = MotorToPosition(MOTOR, (LOW_POSITION * COUNTS_PER_INCH).toInt(), SPEED, requirements = listOf(this@Lift))
    val toHigh: Command
        get() = MotorToPosition(MOTOR, (HIGH_POSITION * COUNTS_PER_INCH).toInt(), SPEED, requirements = listOf(this@Lift))

    val raise: Command
        get() = PowerMotor(MOTOR, SPEED)
    val lower: Command
        get() = PowerMotor(MOTOR, -SPEED)
    val stop: Command
        get() = PowerMotor(MOTOR, 0.0)

    override fun initialize() {
        MOTOR.initialize()
        MOTOR.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        MOTOR.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }

}

