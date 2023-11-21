package org.firstinspires.ftc.teamcode.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.subsystems.DisplayRobot
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object TeleOpRoutines {
    val teleOpInitRoutine: Command
        get() = parallel {
            +Claw.close
            +Arm.close
            +DisplayRobot()
        }

    val ejectRoutine: Command
        get() = sequential {
            +parallel {
                +Lift.toLow
                +Arm.open
                +sequential {
                    +Delay(0.8)
                    +Claw.intake
                }
            }
            +parallel {
                +Claw.intake
                +Arm.close
                +Lift.toIntake
            }
            +Claw.close
        }
}