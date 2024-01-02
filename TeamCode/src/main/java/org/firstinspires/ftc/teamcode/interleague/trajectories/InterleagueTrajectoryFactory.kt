package org.firstinspires.ftc.teamcode.interleague.trajectories

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import org.atomicrobotics3805.cflib.Constants.drive
import org.atomicrobotics3805.cflib.trajectories.ParallelTrajectory
import org.atomicrobotics3805.cflib.trajectories.TrajectoryFactory
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.trajectories.switch
import org.atomicrobotics3805.cflib.trajectories.switchAngle

object InterleagueTrajectoryFactory: TrajectoryFactory() {
    val startYPos = 62.75
    lateinit var backdropStartPose: Pose2d

    /** Spike tape */
    lateinit var backdropFrontSpikeTape: Pose2d
    /** Spike tape */
    lateinit var backdropCenterSpikeTape: Pose2d
    /** Spike tape */
    lateinit var backdropBackSpikeTape: Pose2d

    /** Start to spike tape */
    lateinit var backdropStartToFront: ParallelTrajectory
    /** Start to spike tape */
    lateinit var backdropStartToCenter: ParallelTrajectory
    /** Start to spike tape */
    lateinit var backdropStartToBack: ParallelTrajectory

    val scoreXPos = 54.0
    /** Backdrop */
    lateinit var scoreInner: Pose2d
    /** Backdrop */
    lateinit var scoreCenter: Pose2d
    /** Backdrop */
    lateinit var scoreOuter: Pose2d

    /** Spike tape to backdrop */
    lateinit var backdropFrontTapeToScoreInner: ParallelTrajectory
    /** Spike tape to backdrop */
    lateinit var backdropCenterTapeToScoreCenter: ParallelTrajectory
    /** Spike tape to backdrop */
    lateinit var backdropBackTapeToScoreOuter: ParallelTrajectory

    val parkXPos = 60.0
    lateinit var parkCenter: Pose2d
    lateinit var parkOuter: Pose2d

    lateinit var scoreInnerToParkCenter: ParallelTrajectory
    lateinit var scoreInnerToParkOuter: ParallelTrajectory

    lateinit var scoreCenterToParkCenter: ParallelTrajectory
    lateinit var scoreCenterToParkOuter: ParallelTrajectory

    lateinit var scoreOuterToParkCenter: ParallelTrajectory
    lateinit var scoreOuterToParkOuter: ParallelTrajectory

    private fun poses() {
        backdropStartPose = Pose2d(-12.0, startYPos.switch, 270.0.switchAngle.rad)

        backdropFrontSpikeTape = Pose2d(-0.2, 38.0.switch, 270.0.switchAngle.rad)
        backdropCenterSpikeTape = Pose2d(23.0, 24.0.switch, 180.0.switchAngle.rad)
        backdropBackSpikeTape = Pose2d(24.0, 42.0.switch, 270.0.switchAngle.rad)

        scoreInner = Pose2d(scoreXPos, 29.0.switch, 180.0.rad)
        scoreCenter = Pose2d(scoreXPos, 36.0.switch, 180.0.rad)
        scoreOuter = Pose2d(scoreXPos, 42.0.switch, 180.0.rad)

        parkCenter = Pose2d(parkXPos, 12.0.switch, 180.0.rad)
        parkOuter = Pose2d(parkXPos, 60.0.switch, 180.0.rad)
    }

    private fun backdropSide() {
        backdropStartToFront = drive.trajectoryBuilder(backdropStartPose)
            .forward(20.0)
            .splineToLinearHeading(backdropFrontSpikeTape, 180.0.rad)
            .build()
        backdropStartToCenter = drive.trajectoryBuilder(backdropStartPose)
            .splineToSplineHeading(backdropCenterSpikeTape, 270.0.switchAngle.rad)
            .build()
        backdropStartToBack = drive.trajectoryBuilder(backdropStartPose)
            .forward(20.0)
            .splineToLinearHeading(backdropBackSpikeTape, 180.0)
            .build()

        backdropFrontTapeToScoreInner = drive.trajectoryBuilder(backdropFrontSpikeTape, 0.0.rad)
            .splineToSplineHeading(scoreInner, 0.0.rad)
            .build()
        backdropCenterTapeToScoreCenter = drive.trajectoryBuilder(backdropFrontSpikeTape, 0.0.rad)
            .splineToSplineHeading(scoreCenter, 0.0.rad)
            .build()
        backdropBackTapeToScoreOuter = drive.trajectoryBuilder(backdropFrontSpikeTape, 0.0.rad)
            .splineToSplineHeading(scoreOuter, 0.0.rad)
            .build()
    }

    fun shared() {
        scoreInnerToParkCenter = drive.trajectoryBuilder(scoreInner, 270.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(scoreXPos -5.0, 17.0.switch), 270.0.switchAngle.rad)
            .splineToConstantHeading(parkCenter.vec(), 0.0.rad)
            .build()
        scoreInnerToParkOuter = drive.trajectoryBuilder(scoreInner, 90.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(scoreXPos -5.0, 55.0.switch), 90.0.switchAngle.rad)
            .splineToConstantHeading(parkOuter.vec(), 0.0.rad)
            .build()

        scoreCenterToParkCenter = drive.trajectoryBuilder(scoreCenter, 270.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(scoreXPos -5.0, 17.0.switch), 270.0.switchAngle.rad)
            .splineToConstantHeading(parkCenter.vec(), 0.0.rad)
            .build()
        scoreCenterToParkOuter = drive.trajectoryBuilder(scoreCenter, 90.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(scoreXPos -5.0, 55.0.switch), 90.0.switchAngle.rad)
            .splineToConstantHeading(parkOuter.vec(), 0.0.rad)
            .build()

        scoreOuterToParkCenter = drive.trajectoryBuilder(scoreOuter, 270.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(scoreXPos -5.0, 17.0.switch), 270.0.switchAngle.rad)
            .splineToConstantHeading(parkCenter.vec(), 0.0.rad)
            .build()
        scoreOuterToParkOuter = drive.trajectoryBuilder(scoreOuter, 90.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(scoreXPos -5.0, 55.0.switch), 90.0.switchAngle.rad)
            .splineToConstantHeading(parkOuter.vec(), 0.0.rad)
            .build()
    }

    override fun initialize() {
        super.initialize()
        poses()
        backdropSide()
        shared()
    }
}