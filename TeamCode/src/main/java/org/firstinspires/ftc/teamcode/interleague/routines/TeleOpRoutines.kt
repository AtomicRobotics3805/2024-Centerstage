package org.firstinspires.ftc.teamcode.interleague.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.subsystems.DisplayRobot
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.Ramp

object TeleOpRoutines {
    val teleOpInitialize: Command
        get() = parallel {
            +Lid.close
            +Arm.close
            +DisplayRobot()
            +Ramp.intake
        }

    val easyEject: Command
        get() = sequential {
            +parallel {
                +Lift.toLow
                +Arm.open
                +sequential {
                    +Delay(0.8)
                    +Lid.intake
                }
            }
            +parallel {
                +Lid.intake
                +Arm.close
                +Lift.toIntake
            }
            +Lid.close
        }
}