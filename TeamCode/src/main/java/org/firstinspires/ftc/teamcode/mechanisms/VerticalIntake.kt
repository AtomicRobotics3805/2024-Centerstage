package org.firstinspires.ftc.teamcode.mechanisms

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.hardware.MotorEx
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.subsystems.Subsystem
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.utility.PowerMotor

object VerticalIntake: Subsystem {
    @JvmField
    var NAME1 = "IL"
    var NAME2 = "IR"

    @JvmField
    var DIRECTION1 = DcMotorSimple.Direction.REVERSE
    var DIRECTION2 = DcMotorSimple.Direction.FORWARD

    @JvmField
    var SPEED = 1.0

    @JvmField
    var EJECTION_SPEED = 0.8

    @JvmField
    var SLOW_EJECT_SPEED = 0.2

    @JvmField
    var STACK_INTAKE_TIME = 0.5

    val MOTOR1 = MotorEx(NAME1, MotorEx.MotorType.GOBILDA_YELLOWJACKET, 19.2, DIRECTION1)
    val MOTOR2 = MotorEx(NAME2, MotorEx.MotorType.GOBILDA_YELLOWJACKET, 19.2, DIRECTION2)

    val start: Command
        get() = parallel {
            +PowerMotor(MOTOR1, SPEED, requirements = listOf(this@VerticalIntake), logData = true)
            +PowerMotor(MOTOR2, SPEED, requirements = listOf(this@VerticalIntake), logData = true)
        }
    val stop: Command
        get() = parallel {
            +PowerMotor(MOTOR1, 0.0, requirements = listOf(this@VerticalIntake), logData = true)
            +PowerMotor(MOTOR2, 0.0, requirements = listOf(this@VerticalIntake), logData = true)
        }
    val reverse: Command
        get() = parallel {
            +PowerMotor(MOTOR1, -SPEED, requirements = listOf(this@VerticalIntake), logData = true)
            +PowerMotor(MOTOR2, -SPEED, requirements = listOf(this@VerticalIntake), logData = true)
        }
    val eject: Command
        get() = parallel {
            +PowerMotor(MOTOR1, -EJECTION_SPEED, requirements = listOf(this@VerticalIntake), logData = true)
            +PowerMotor(MOTOR2, -EJECTION_SPEED, requirements = listOf(this@VerticalIntake), logData = true)
        }
    val slowEject: Command
        get() = parallel {
            +PowerMotor(MOTOR1, -SLOW_EJECT_SPEED, requirements = listOf(this@VerticalIntake), logData = true)
            +PowerMotor(MOTOR2, -SLOW_EJECT_SPEED, requirements = listOf(this@VerticalIntake), logData = true)
            +sequential {
                +Delay(1.0)
                +stop
            }
        }
    val intakeStack: Command
        get() = parallel {
            +PowerMotor(MOTOR1, SPEED, requirements = listOf(this@VerticalIntake), logData = true)
            +PowerMotor(MOTOR2, SPEED, requirements = listOf(this@VerticalIntake), logData = true)
            +sequential {
                +Delay(STACK_INTAKE_TIME)
                +stop
            }
        }

    override fun initialize() {
        MOTOR1.initialize()
        MOTOR1.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        MOTOR2.initialize()
        MOTOR2.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }
}