package org.firstinspires.ftc.teamcode.trajectoryFactory

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import org.atomicrobotics3805.cflib.trajectories.ParallelTrajectory
import org.atomicrobotics3805.cflib.trajectories.TrajectoryFactory
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.trajectories.switch
import org.atomicrobotics3805.cflib.trajectories.switchAngle
import org.atomicrobotics3805.cflib.Constants.drive as d

@Suppress("MemberVisibilityCanBePrivate")
object TestingTrajectoryFactory: TrajectoryFactory() {

    lateinit var straightTest: ParallelTrajectory

    //endregion

    override fun initialize() {
        super.initialize()
        straightTest = d.trajectoryBuilder(Pose2d())
            .forward(40.0)
            .build()
        //endregion
    }
}