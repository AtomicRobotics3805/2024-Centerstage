package org.firstinspires.ftc.teamcode.mechanisms

import android.widget.Switch
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.apache.commons.math3.analysis.function.Pow
import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.GamepadEx
import org.atomicrobotics3805.cflib.hardware.MotorEx
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.subsystems.MotorToPosition
import org.atomicrobotics3805.cflib.subsystems.Subsystem
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.controls.CompetitionControls
import org.firstinspires.ftc.teamcode.utility.SwitchCommand
import org.firstinspires.ftc.teamcode.utility.PowerMotor

@Config
object Lift: Subsystem {
    @JvmField
    var NAME = "lift"

    @JvmField
    var INTAKE_POSITION = 0.0 // in
    var LOW_POSITION = 12.0 // in
    var HIGH_POSITION = 19.0 // in
    var HANG_POSITION = 18.0 // in
    var AUTO_POSITION = 10.5 // in

    @JvmField
    var DIRECTION = DcMotorSimple.Direction.FORWARD
    @JvmField
    var SPEED = 1.0

    val MOTOR = MotorEx(NAME, MotorEx.MotorType.GOBILDA_YELLOWJACKET, 19.2, DIRECTION)

    //enum class LiftHeights {
    //    INTAKE,
    //    LOW,
    //    HIGH,
    //    HANG,
    //    ZERO
    //}

    //private var currPos: LiftHeights = LiftHeights.ZERO

    var _allowManual: Boolean = false

    private const val PULLEY_RADIUS = 0.5 // in
    private const val DRIVE_GEAR_REDUCTION = 1.0
    private var COUNTS_PER_INCH: Double = MOTOR.ticksPerRev * DRIVE_GEAR_REDUCTION / (2 * PULLEY_RADIUS * Math.PI)

    val toIntake: Command
        get() = sequential {
            +MotorToPosition(MOTOR, (INTAKE_POSITION * COUNTS_PER_INCH).toInt(), SPEED, requirements = listOf(this@Lift), kP = 0.003)
           // +org.firstinspires.ftc.teamcode.utility.PowerMotor(MOTOR, 0.0, requirements = listOf(this@Lift))
        }
     //   get() = CustomCommand(_start = { currPos = LiftHeights.INTAKE })
    val toAuto: Command
         get() = MotorToPosition(MOTOR, (AUTO_POSITION * COUNTS_PER_INCH).toInt(), SPEED, requirements = listOf(this@Lift), kP = 0.003)
    val toLow: Command
        get() = MotorToPosition(MOTOR, (LOW_POSITION * COUNTS_PER_INCH).toInt(), SPEED, requirements = listOf(this@Lift), kP = 0.003)
    //    get() = CustomCommand(_start = { currPos = LiftHeights.LOW })
    val toHigh: Command
        get() = MotorToPosition(MOTOR, (HIGH_POSITION * COUNTS_PER_INCH).toInt(), SPEED, requirements = listOf(this@Lift), kP = 0.003)
    //    get() = CustomCommand(_start = { currPos  = LiftHeights.HIGH })
    val toHang: Command
        get() = MotorToPosition(MOTOR, (HANG_POSITION * COUNTS_PER_INCH).toInt(), SPEED, requirements = listOf(this@Lift), kP = 0.003)
//        get() = CustomCommand(_start = { currPos = LiftHeights.HANG })

    //val applyHeight: Command
    //    get() = MotorToPosition(MOTOR, ((if(currPos == LiftHeights.INTAKE) INTAKE_POSITION else if (currPos == LiftHeights.LOW) LOW_POSITION else if (currPos == LiftHeights.HIGH) HIGH_POSITION else if (currPos == LiftHeights.HANG) HANG_POSITION else 0.0) * COUNTS_PER_INCH).toInt(),SPEED, requirements = listOf(this@Lift))

    val stop: Command
        get() = PowerMotor(MOTOR, 0.0, requirements = listOf(this@Lift))
    val forceStop: Command
        get() = PowerMotor(MOTOR, 0.0, requirements = listOf(this@Lift))

    class ManualControl(val gamepad: GamepadEx): Command() {
        override val _isDone = false

        override fun execute() {
            if(gamepad.rightStick.y > 0.2 || gamepad.rightStick.y < -0.2) {
                PowerMotor(MOTOR, SPEED * -gamepad.rightStick.y, requirements = listOf(Lift))
            }
            else PowerMotor(MOTOR, 0.0, requirements = listOf(Lift))
        }
    }

    override fun initialize() {
        MOTOR.initialize()
        MOTOR.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        MOTOR.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }

}

