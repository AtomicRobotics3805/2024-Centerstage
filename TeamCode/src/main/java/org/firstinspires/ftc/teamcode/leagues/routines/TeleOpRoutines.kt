package org.firstinspires.ftc.teamcode.leagues.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.subsystems.DisplayRobot
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.Lift
import org.firstinspires.ftc.teamcode.mechanisms.NewLid
import org.firstinspires.ftc.teamcode.mechanisms.Ramp
import org.firstinspires.ftc.teamcode.utility.CurrentWatcher
import org.firstinspires.ftc.teamcode.utility.VoltageWatcher

object TeleOpRoutines {
    val teleOpInitRoutine: Command
        get() = parallel {
            +CurrentWatcher()
            +CustomCommand(_start = {
                CurrentWatcher.motorsToWatch += Lift.MOTOR
                CurrentWatcher.motorsToWatch += Intake.MOTOR
                CurrentWatcher.motorsToWatch += CompetitionDriveConstants._LEFT_FRONT_MOTOR
                CurrentWatcher.motorsToWatch += CompetitionDriveConstants._LEFT_BACK_MOTOR
                CurrentWatcher.motorsToWatch += CompetitionDriveConstants._RIGHT_FRONT_MOTOR
                CurrentWatcher.motorsToWatch += CompetitionDriveConstants._RIGHT_BACK_MOTOR
            })
            +VoltageWatcher()
            +NewLid.close
            +Arm.close
            +DisplayRobot()
            +Ramp.intake
        }

    val ejectRoutine: Command
        get() = sequential {
            +parallel {
                +Lift.toLow
                +Arm.open
                +sequential {
                    +Delay(0.8)
                    +NewLid.open
                }
            }
            +parallel {
                +NewLid.open
                +Arm.close
                +Lift.toIntake
            }
        }
}