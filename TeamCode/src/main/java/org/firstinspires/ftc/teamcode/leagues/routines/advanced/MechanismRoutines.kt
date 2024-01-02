package org.firstinspires.ftc.teamcode.leagues.routines.advanced

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift

object MechanismRoutines {
    val liftRaise: Command
        get() = sequential {
            +Lift.toAuto
            +Arm.open
        }

    val higherLiftRaise: Command
        get() = parallel {
            +Lift.toLow
            +Arm.open
        }
    val scoreSingle: Command
        get() = sequential {
            +Lid.intake
            +Lift.toLow
            +parallel {
                +Arm.close
                +Lift.toIntake
            }
            +Lid.close
        }
    val scoreTwo: Command
        get() = sequential {
            +Lid.intake
            +Delay(1.0)
            +parallel {
                +Arm.close
                +Lift.toIntake
            }
            +Lid.close
        }
    val intake: Command
        get() = parallel {
            +Lid.intake
            +sequential {
                +Delay(0.25)
                +Intake.intakeStack
            }
        }
}