package org.firstinspires.ftc.teamcode

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.TelemetryController
import org.atomicrobotics3805.cflib.TelemetryController.telemetry
import org.atomicrobotics3805.cflib.subsystems.Subsystem
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor

object DetectionMechanism : Subsystem {
    var visionPortal: VisionPortal? = null

    // Create the AprilTag processor.
    var aprilTag = AprilTagProcessor.Builder() //.setDrawAxes(false)
        //.setDrawCubeProjection(false)
        //.setDrawTagOutline(true)
        //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
        //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
        //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
        // == CAMERA CALIBRATION ==
        // If you do not manually specify calibration parameters, the SDK will attempt
        // to load a predefined calibration for your camera.
        //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
        // ... these parameters are fx, fy, cx, cy.
        .build()

    lateinit var propProcessor: PropProcessor


    enum class ElementPosition {
        LEFT,
        CENTER,
        RIGHT,
        DETECTING
    }
    var elementPosition = ElementPosition.DETECTING
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
            fun telemetryAprilTag() {
                val currentDetections: List<AprilTagDetection> = aprilTag.getDetections()
                telemetry.addData("# AprilTags Detected", currentDetections.size)

                // Step through the list of detections and display info for each one.
                for (detection in currentDetections) {
                    if (detection.metadata != null) {
                        telemetry.addLine(
                            String.format(
                                "\n==== (ID %d) %s",
                                detection.id,
                                detection.metadata.name
                            )
                        )
                        telemetry.addLine(
                            String.format(
                                "XYZ %6.1f %6.1f %6.1f  (inch)",
                                detection.ftcPose.x,
                                detection.ftcPose.y,
                                detection.ftcPose.z
                            )
                        )
                        telemetry.addLine(
                            String.format(
                                "PRY %6.1f %6.1f %6.1f  (deg)",
                                detection.ftcPose.pitch,
                                detection.ftcPose.roll,
                                detection.ftcPose.yaw
                            )
                        )
                        telemetry.addLine(
                            String.format(
                                "RBE %6.1f %6.1f %6.1f  (inch, deg, deg)",
                                detection.ftcPose.range,
                                detection.ftcPose.bearing,
                                detection.ftcPose.elevation
                            )
                        )
                    } else {
                        telemetry.addLine(String.format("\n==== (ID %d) Unknown", detection.id))
                        telemetry.addLine(
                            String.format(
                                "Center %6.0f %6.0f   (pixels)",
                                detection.center.x,
                                detection.center.y
                            )
                        )
                    }
                } // end for() loop

                // Add "key" information to telemetry
                telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.")
                telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)")
                telemetry.addLine("RBE = Range, Bearing & Elevation")
            } // end method telemetryAprilTag()
            //telemetryAprilTag()
        }

        override fun end(interrupted: Boolean) {
//            VisionPortal.stopStreaming()
        }
    }
}