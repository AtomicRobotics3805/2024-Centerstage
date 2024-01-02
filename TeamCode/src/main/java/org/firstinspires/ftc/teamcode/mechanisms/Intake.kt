package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.RobotLog
import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.hardware.MotorEx
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.firstinspires.ftc.teamcode.utility.PowerMotor
import org.atomicrobotics3805.cflib.subsystems.Subsystem
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.utility.CurrentWatcher

object Intake: Subsystem {
    @JvmField
    var  NAME = "intake"

    @JvmField
    var DIRECTION = DcMotorSimple.Direction.FORWARD

    @JvmField
    var SPEED = 1.0

    @JvmField
    var EJECTION_SPEED = 0.8

    @JvmField
    var SLOW_EJECT_SPEED = 0.4

    @JvmField
    var STACK_INTAKE_TIME = 0.5

    val MOTOR = MotorEx(NAME, MotorEx.MotorType.GOBILDA_YELLOWJACKET, 3.7, DIRECTION)

    val start: Command
        get() = PowerMotor(MOTOR, SPEED, requirements = listOf(this@Intake), logData = true)
    val stop: Command
        get() = PowerMotor(MOTOR, 0.0, requirements = listOf(this@Intake), logData = true)
    val reverse: Command
        get() = PowerMotor(MOTOR, -SPEED, requirements = listOf(this@Intake), logData = true)
    val eject: Command
        get() = PowerMotor(MOTOR, -EJECTION_SPEED, requirements = listOf(this@Intake), logData = true)
    val slowEject: Command
        get() = parallel {
            +PowerMotor(MOTOR, -SLOW_EJECT_SPEED, requirements = listOf(this@Intake), logData = true)
            +sequential {
                +Delay(1.0)
                +stop
            }
        }
    val intakeStack: Command
        get() = parallel {
            +PowerMotor(MOTOR, SPEED, requirements = listOf(this@Intake), logData = true)
            +sequential {
                +Delay(STACK_INTAKE_TIME)
                +stop
            }
        }

    override fun initialize() {
        MOTOR.initialize()
        MOTOR.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

    }
}

