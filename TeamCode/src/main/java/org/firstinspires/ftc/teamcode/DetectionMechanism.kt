package org.firstinspires.ftc.teamcode

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.subsystems.Subsystem
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.vision.VisionPortal

object DetectionMechanism : Subsystem {
    var visionPortal: VisionPortal? = null


    lateinit var propProcessor: PropProcessor
    class DetectCommand : Command(

    ) {
        override val _isDone: Boolean
            get() = Constants.opMode.isStarted
        override fun start() {

            fun initAprilTag() {

                propProcessor = PropProcessor()


                // Create the vision portal by using a builder.
                val builder = VisionPortal.Builder()

                // Set the camera (webcam vs. built-in RC phone camera).

                builder.setCamera(
                    Constants.opMode.hardwareMap.get<WebcamName>(
                        WebcamName::class.java,
                        "Webcam 1"
                    )
                )


                // Choose a camera resolution. Not all cameras support all resolutions.
                //builder.setCameraResolution(new Size(640, 480));

                // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
                //builder.enableCameraMonitoring(true);

                // Set the stream format; MJPEG uses less bandwidth than default YUY2.
                //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

                // Choose whether or not LiveView stops if no processors are enabled.
                // If set "true", monitor shows solid orange screen if no processors enabled.
                // If set "false", monitor shows camera view without annotations.
                //builder.setAutoStopLiveView(false);

                // Set and enable the processor.
                //builder.addProcessor(aprilTag)
                builder.addProcessor(propProcessor)


                // Build the Vision Portal, using the above settings.
                visionPortal = builder.build()

                // Disable or re-enable the aprilTag processor at any time.
                //visionPortal.setProcessorEnabled(aprilTag, true);
            } // end method initAprilTag()

            initAprilTag()



        /**
             * Add telemetry about AprilTag detections.
             */

        }

        override fun execute() {
            TelemetryController.telemetry.addData("satRectLeft", propProcessor.satRectLeft)
            TelemetryController.telemetry.addData("satRectMiddle", propProcessor.satRectMiddle)
            TelemetryController.telemetry.addData("satRectRight", propProcessor.satRectRight)
            TelemetryController.telemetry.addData("selection", propProcessor.selection)

        }

        override fun end(interrupted: Boolean) {
            visionPortal?.stopStreaming()
        }
    }
}