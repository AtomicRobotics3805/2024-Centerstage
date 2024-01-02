package org.firstinspires.ftc.teamcode.utility

import com.qualcomm.hardware.lynx.LynxModule
import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.hardware.MotorEx
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants

class VoltageWatcher: Command() {
//    companion object {
//        var motorsToUpdate: MutableList<MotorEx> = mutableListOf()
//        var lowerDriveSpeed = true
//    }

    override val _isDone = false

    val CONTROL_HUB_NAME: String = "Control Hub" // TODO: Get Control Hub Name

    var unit = VoltageUnit.VOLTS
    var threshold = 8.0

    lateinit var controlHub: LynxModule;

    override fun start() {
        controlHub = Constants.opMode.hardwareMap.get(LynxModule::class.java, CONTROL_HUB_NAME)
    }

    override fun execute() {
        if (controlHub.getInputVoltage(unit) <= threshold) {
            // The voltage is too low, respond
            // Let's set the drive power to 0.5 until the voltage drop stops
            Constants.drive.driverSpeedIndex++
            if (Constants.drive.driverSpeedIndex >= CompetitionDriveConstants.DRIVER_SPEEDS.size)
                Constants.drive.driverSpeedIndex = 0

            // Set all motor powers to half their previous power
//            motorsToUpdate.forEach {
//                it.power = it.power / 2
//            }
        } else {
            // Reset drive power
            Constants.drive.driverSpeedIndex++
            if (Constants.drive.driverSpeedIndex >= CompetitionDriveConstants.DRIVER_SPEEDS.size)
                Constants.drive.driverSpeedIndex = 0
        }
    }

//    fun resetNextMotor() {
//        if(motorsToUpdate.isNotEmpty()) {
//            motorsToUpdate[0].first.power = motorsToUpdate[0].second
//        }
//    }
}