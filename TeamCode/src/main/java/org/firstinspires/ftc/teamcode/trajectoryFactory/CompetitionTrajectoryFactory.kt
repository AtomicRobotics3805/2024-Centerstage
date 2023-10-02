package org.firstinspires.ftc.teamcode.trajectoryFactory

import com.acmerobotics.roadrunner.geometry.Pose2d
import org.atomicrobotics3805.cflib.trajectories.ParallelTrajectory
import org.atomicrobotics3805.cflib.trajectories.TrajectoryFactory
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.trajectories.switch
import org.atomicrobotics3805.cflib.trajectories.switchAngle
import org.atomicrobotics3805.cflib.Constants.drive as d

object CompetitionTrajectoryFactory : TrajectoryFactory() {
    var upstageStartPose = Pose2d()
    var upstageDetectPose = Pose2d()
    var upstageScorePose = Pose2d()
    var upstageParkPoseA = Pose2d()
    var upstageParkPoseB = Pose2d()

    var downstageStartPose = Pose2d()
    var downstageDetectPose = Pose2d()

    lateinit var upstageStartToDetect : ParallelTrajectory
    lateinit var upstageDetectToScore : ParallelTrajectory
    lateinit var upstageScoreToParkA : ParallelTrajectory

    lateinit var downstageStartToDetect: ParallelTrajectory
    lateinit var downstageDetectToScore: ParallelTrajectory

    override fun initialize() {
        super.initialize()

        // ***POSES***

        // Upstage (Closer to backdrop)
        upstageStartPose = Pose2d(12.0, 62.75.switch, 270.0.switchAngle.rad)
        upstageDetectPose = Pose2d(12.0, 33.8.switch, 270.0.switchAngle.rad)

        upstageScorePose = Pose2d(48.0, 36.0.switch, 0.0.rad)
        upstageParkPoseA = Pose2d(60.0, 60.0.switch, 0.0.rad)

        // Downstage (Closer to audience)
        downstageStartPose = Pose2d(-36.0, 62.75.switch, 270.0.switchAngle.rad)
        downstageDetectPose = Pose2d(-36.0, 33.8.switch, 270.0.switchAngle.rad)

        // ***TRAJECTORIES***

        // Upstage (Closer to backdrop)
        upstageStartToDetect = d.trajectoryBuilder(upstageStartPose)
            .splineToSplineHeading(upstageDetectPose, 270.0.switchAngle.rad)
            .build()
        upstageDetectToScore = d.trajectoryBuilder(upstageDetectPose)
            .lineToLinearHeading(upstageScorePose)
            .build()
        upstageScoreToParkA = d.trajectoryBuilder(upstageScorePose)
            .forward(-1.0) // Added to change shape of spline path
            .splineToConstantHeading(upstageParkPoseA.vec(), 0.0.rad)
            .build()

        // Downstage (Closer to audience)
        downstageStartToDetect = d.trajectoryBuilder(downstageStartPose)
            .splineToSplineHeading(downstageDetectPose, 270.0.switchAngle.rad)
            .build()
        downstageDetectToScore = d.trajectoryBuilder(downstageDetectPose)
            .lineToLinearHeading(upstageScorePose)
            .build()
    }
}