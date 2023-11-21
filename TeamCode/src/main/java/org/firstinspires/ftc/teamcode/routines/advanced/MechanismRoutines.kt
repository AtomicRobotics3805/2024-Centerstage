package org.firstinspires.ftc.teamcode.routines.advanced

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Claw
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object MechanismRoutines {
    val liftRaise: Command
        get() = parallel {
            +Lift.toAuto
            +Arm.open
        }
    val scoreSingle: Command
        get() = sequential {
            +Claw.intake
            +parallel {
                +Arm.close
                +Lift.toIntake
            }
            +Claw.close
        }
    val scoreTwo: Command
        get() = sequential {
            +Claw.drop
            +Delay(1.0)
            +parallel {
                +Arm.close
                +Lift.toIntake
            }
            +Claw.close
        }
    val intake: Command
        get() = parallel {
            +Claw.intake
            +sequential {
                +Intake.intakeStack
                +Claw.close
            }
        }
}