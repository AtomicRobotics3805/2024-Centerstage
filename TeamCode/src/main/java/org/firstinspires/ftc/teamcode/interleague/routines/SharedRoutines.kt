package org.firstinspires.ftc.teamcode.interleague.routines

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.utilCommands.CustomCommand
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import org.firstinspires.ftc.teamcode.mechanisms.Arm
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism

object SharedRoutines {
    /** 0: Unselected, 1: Center, 2: Edge */
    var choosePark: Int = 0
    val parkCenter = CustomCommand(_start = { choosePark = 1 })
    val parkEdge = CustomCommand(_start = { choosePark = 2 })

    val initializationRoutine: Command
        get() {
            CommandScheduler.scheduleCommand(TelemetryCommand(10.0, "A: Park Center\nB: Park Edge"))
            if(Constants.opMode.gamepad1.a) {
                return initializeCenter
            }
            else if (Constants.opMode.gamepad1.b) {
                return initializeEdge
            }
            return TelemetryCommand(1.0, {"Couldn't initialize."})
        }

    val initializeCenter: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Arm.close
            +Lid.close
            +parkCenter
        }

    val initializeEdge: Command
        get() = parallel {
            +DetectionMechanism.DetectCommand()
            +Arm.close
            +Lid.close
            +parkEdge
        }
}