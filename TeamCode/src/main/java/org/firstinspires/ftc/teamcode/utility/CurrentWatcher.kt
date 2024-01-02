package org.firstinspires.ftc.teamcode.utility

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.hardware.MotorEx
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.leagues.controls.CompetitionControls
import org.firstinspires.ftc.teamcode.mechanisms.Lift

class CurrentWatcher: Command() {

    companion object {
        var motorsToWatch: MutableList<MotorEx> = mutableListOf()
    }

    var totalMax: Double = 20.0
    var unit: CurrentUnit = CurrentUnit.AMPS

    override val _isDone = false

    fun addMotor(motor: MotorEx) {
        motorsToWatch.add(motor)
    }

    override fun execute() {
        var sum = 0.0
        motorsToWatch.forEach {
            sum += it.getCurrent(unit)
        }

        CommandScheduler.scheduleCommand(TelemetryCommand(0.5, { "Current: " + sum.toString() }))

        if (sum > totalMax) {
            motorsToWatch.forEach {
                it.power = 0.0
            }
        }
    }
}