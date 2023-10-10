package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.atomicrobotics3805.cflib.CommandScheduler
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.controls.Controls
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.AutonomousOpMode
import org.atomicrobotics3805.cflib.opmodes.TeleOpMode
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand

@TeleOp(name = "ShippingElementDetection")

class ShippingElementDetection : LinearOpMode(

) {
    override fun runOpMode() {
        try {
            // setting constants
            Constants.opMode = this
            // initializing trajectory factory
            // controls stuff
            TestControls.registerGamepads()
            // this both registers & initializes the subsystems
            CommandScheduler.registerSubsystems(TelemetryController, DetectionMechanism)
            CommandScheduler.scheduleCommand(DetectionMechanism.DetectCommand())
            // if there is a routine that's supposed to be performed on init, then do it
            // wait for start
            while (!isStarted && !isStopRequested) {
                TelemetryController.telemetry.addLine("Woohoo")
                CommandScheduler.run()
            }
            // commands have to be registered after the subsystems are registered
            TestControls.registerCommands()
            // if there's a routine that's supposed to be performed on play, do it
//            if (mainRoutine != null) CommandScheduler.scheduleCommand(mainRoutine.invoke())
            // wait for stop
            while (opModeIsActive()) {
                CommandScheduler.run()
            }
        } catch (e: Exception) {
            // we have to catch the exception so that we can still cancel and unregister
            TelemetryController.telemetry.addLine("Error Occurred!")
            TelemetryController.telemetry.addLine(e.message)
            // have to update telemetry since CommandScheduler won't call it anymore
            TelemetryController.periodic()
        } finally {
            // cancels all commands and unregisters all gamepads & subsystems
            CommandScheduler.cancelAll()
            CommandScheduler.unregisterAll()
            while (!isStopRequested);
        }
    }
}