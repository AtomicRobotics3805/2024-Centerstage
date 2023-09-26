package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.controls.Controls
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.opmodes.TeleOpMode
import org.atomicrobotics3805.cflib.utilCommands.TelemetryCommand

@TeleOp(name = "Test OpMode")
class TestOpMode : TeleOpMode(
    controls = TestControls,
    Constants.Color.UNKNOWN,
    mainRoutine = { TelemetryCommand(100.0, "Woohoo!") },
    drive = MecanumDrive(
        PracticeMecanumDriveConstants,
        TwoWheelOdometryLocalizer(PracticeOdometryConstants)
    )
)