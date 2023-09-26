package org.firstinspires.ftc.teamcode.trajectoryFactory

import com.acmerobotics.roadrunner.geometry.Pose2d
import org.atomicrobotics3805.cflib.trajectories.ParallelTrajectory
import org.atomicrobotics3805.cflib.trajectories.TrajectoryFactory
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.trajectories.switch
import org.atomicrobotics3805.cflib.trajectories.switchAngle
import org.atomicrobotics3805.cflib.Constants.drive as d

object CompetitionTrajectoryFactory : TrajectoryFactory() {
    var startPoseA = Pose2d()
    var poseB = Pose2d()

    lateinit var startAtoPoseB : ParallelTrajectory

    override fun initialize() {
        super.initialize()

        startPoseA = Pose2d(35.0, 62.75.switch, 270.0.switchAngle.rad)
        poseB = Pose2d(60.75, 12.15.switch, 0.0.switchAngle.rad)

        startAtoPoseB = d.trajectoryBuilder(startPoseA)
            .splineToSplineHeading(poseB, 180.0.rad)
            .build()
    }
}