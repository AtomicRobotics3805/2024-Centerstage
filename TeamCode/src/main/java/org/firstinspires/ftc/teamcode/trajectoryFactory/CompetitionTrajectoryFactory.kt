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
object CompetitionTrajectoryFactory: TrajectoryFactory() {

    //region ***NEW POSES***
    // STARTING POSITIONS
    var startYPosition = 62.75 // y coordinates for starting, how far from side walls
    var wingStartPose = Pose2d() // The start pose that is closest to the opponent's wing
    var backstageStartPose = Pose2d() // The start pose that is closest to the backstage area

    // BACKDROP SCORING POSITIONS
    var scoreXPosition = 48.0 // x coordinates for the backdrop. Varies depending on the robot
    var scorePoseInner = Pose2d() // When looking at the backdrop, the leftmost pair of slots
    var scorePoseCenter = Pose2d() // When looking at the backdrop, the center pair of slots
    var scorePoseOuter = Pose2d() // When looking at the backdrop, the rightmost pair of slots

    // PARKING POSITIONS
    var parkXPosition = 60.0 // x coordinates for parking; how far from the wall
    var parkPoseCenter = Pose2d() // The tile next to the backdrop that is closest to the opponent
    var parkPoseEdge = Pose2d() // The tile next to the backdrop that is closest to the field wall

    // LOCATIONS FOR SCORING PURPLE PIXEL ON SPIKE TAPE
    var wingFrontSpikeTape = Pose2d() // Closest to the wing; spike tape closest to audience
    var wingCenterSpikeTape = Pose2d() // Closest to the wing; spike tape in between other two
    var wingBackSpikeTape = Pose2d() // Closest to the wing; spike tape farthest from audience
    var backstageFrontSpikeTape = Pose2d() // Closest to backstage; spike tape closest to audience
    var backstageCenterSpikeTape = Pose2d() // Closest to backstage; spike tape between other two
    var backstageBackSpikeTape = Pose2d() // Closest to backstage; spike tape farthest from audience
    //endregion

    //region ***NEW TRAJECTORIES***
    // WING TRAJECTORIES
    lateinit var wingStartToFrontSpikeTape: ParallelTrajectory
    lateinit var wingStartToCenterSpikeTape: ParallelTrajectory
    lateinit var wingStartToBackSpikeTape: ParallelTrajectory

    lateinit var wingFrontSpikeTapeToScore: ParallelTrajectory
    lateinit var wingCenterSpikeTapeToScore: ParallelTrajectory
    lateinit var wingBackSpikeTapeToScore: ParallelTrajectory

    // BACKSTAGE TRAJECTORIES
    lateinit var backstageStartToFrontSpikeTape: ParallelTrajectory
    lateinit var backstageStartToCenterSpikeTape: ParallelTrajectory
    lateinit var backstageStartToBackSpikeTape: ParallelTrajectory

    lateinit var backstageFrontSpikeTapeToScore: ParallelTrajectory
    lateinit var backstageCenterSpikeTapeToScore: ParallelTrajectory
    lateinit var backstageBackSpikeTapeToScore: ParallelTrajectory

    // GENERAL TRAJECTORIES
    lateinit var scoreInnerToParkCenter: ParallelTrajectory
    lateinit var scoreCenterToParkCenter: ParallelTrajectory
    lateinit var scoreOuterToParkCenter: ParallelTrajectory

    lateinit var scoreInnerToParkEdge: ParallelTrajectory
    lateinit var scoreCenterToParkEdge: ParallelTrajectory
    lateinit var scoreOuterToParkEdge: ParallelTrajectory

    // BACKUP TRAJECTORIES
    lateinit var backstageStartToParkCenter: ParallelTrajectory
    lateinit var backstageStartToParkEdge: ParallelTrajectory
    lateinit var wingStartToParkCenter: ParallelTrajectory
    lateinit var wingStartToParkEdge: ParallelTrajectory

    //endregion

    override fun initialize() {
        super.initialize()

        //region Poses
        wingStartPose = Pose2d(-36.0, startYPosition.switch, 270.0.switchAngle.rad)
        backstageStartPose = Pose2d(12.0, startYPosition.switch, 270.0.switchAngle.rad)

        scorePoseOuter = Pose2d(scoreXPosition, 43.0.switch, 180.0.rad)
        scorePoseCenter = Pose2d(scoreXPosition, 36.0.switch, 180.0.rad)
        scorePoseInner = Pose2d(scoreXPosition, 29.0.switch, 180.0.rad)

        parkPoseCenter = Pose2d(parkXPosition, 12.0.switch, 180.0.rad)
        parkPoseEdge = Pose2d(parkXPosition, 60.0.switch, 180.0.rad)

        wingFrontSpikeTape = Pose2d(-46.0, 38.0.switch, 270.0.switchAngle.rad)
        wingCenterSpikeTape = Pose2d(-45.0, 24.0.switch, 0.0.switchAngle.rad)
        wingBackSpikeTape = Pose2d(-24.0, 38.0.switch, 270.0.switch.rad)

        backstageFrontSpikeTape = Pose2d(0.5, 38.0.switch, 270.0.switchAngle.rad)
        backstageCenterSpikeTape = Pose2d(23.0, 24.0.switch, 180.0.switchAngle.rad)
        backstageBackSpikeTape = Pose2d(24.0, 38.0.switch, 270.0.switchAngle.rad)
        //endregion

        //region PURPLE PIXEL
        wingStartToFrontSpikeTape = d.trajectoryBuilder(wingStartPose)
            .forward(20.0)
            .splineToLinearHeading(wingFrontSpikeTape, 180.0.rad)
            .build()
        wingStartToCenterSpikeTape = d.trajectoryBuilder(wingStartPose)
            .splineToSplineHeading(wingCenterSpikeTape, 270.0.switchAngle.rad)
            .build()
        wingStartToBackSpikeTape = d.trajectoryBuilder(wingStartPose)
            .forward(20.0)
            .splineToLinearHeading(wingBackSpikeTape, 0.0.rad)
            .build()

        backstageStartToFrontSpikeTape = d.trajectoryBuilder(backstageStartPose)
            .forward(20.0)
            .splineToLinearHeading(backstageFrontSpikeTape, 180.0.rad)
            .build()
        backstageStartToCenterSpikeTape = d.trajectoryBuilder(backstageStartPose)
            .splineToSplineHeading(backstageCenterSpikeTape, 270.0.switchAngle.rad)
            .build()
        backstageStartToBackSpikeTape = d.trajectoryBuilder(backstageStartPose)
            .forward(20.0)
            .splineToLinearHeading(backstageBackSpikeTape, 0.0.rad)
            .build()
        //endregion

        //region PURPLE PIXEL SCORE TO YELLOW PIXEL SCORE
        backstageFrontSpikeTapeToScore = d.trajectoryBuilder(backstageFrontSpikeTape, 0.0.rad)
            .splineToSplineHeading(scorePoseInner, 0.0.rad)
            .build()
        backstageCenterSpikeTapeToScore = d.trajectoryBuilder(backstageCenterSpikeTape, 0.0.rad)
            .splineToSplineHeading(scorePoseCenter, 0.0.rad)
            .build()
        backstageBackSpikeTapeToScore = d.trajectoryBuilder(backstageBackSpikeTape, 0.0.rad)
            .splineToSplineHeading(scorePoseOuter, 0.0.rad)
            .build()
        wingFrontSpikeTapeToScore = d.trajectoryBuilder(wingFrontSpikeTape, 0.0.rad)
            .splineTo(Vector2d(-12.0, 36.0.switch), 0.0.rad)
            .splineToSplineHeading(scorePoseInner, 0.0.rad)
            .build()
        wingCenterSpikeTapeToScore = d.trajectoryBuilder(wingCenterSpikeTape, 90.0.switchAngle.rad)
            .splineToConstantHeading(Vector2d(-12.0, 36.0.switch), 0.0.rad) // Can also be splineTo() instead of splineToConstantHeading()
            .splineToSplineHeading(scorePoseCenter, 0.0.rad)
            .build()
        wingBackSpikeTapeToScore = d.trajectoryBuilder(wingBackSpikeTape)
            .splineTo(Vector2d(-12.0, 36.0.switch), 0.0.rad)
            .splineToSplineHeading(scorePoseOuter, 0.0.rad)
            .build()
        //endregion

        scoreInnerToParkCenter = d.trajectoryBuilder(scorePoseInner, 270.0.switchAngle.rad)
            .splineToConstantHeading(Vector2d(scoreXPosition, 20.0.switch), 270.0.switchAngle.rad)
            .splineToConstantHeading(parkPoseCenter.vec(), 0.0.rad)
            .build()
        scoreCenterToParkCenter = d.trajectoryBuilder(scorePoseCenter, 270.0.switchAngle.rad)
            .splineToConstantHeading(Vector2d(scoreXPosition, 20.0.switch), 270.0.switchAngle.rad)
            .splineToConstantHeading(parkPoseCenter.vec(), 0.0.rad)
            .build()
        scoreOuterToParkCenter = d.trajectoryBuilder(scorePoseOuter, 270.0.switchAngle.rad)
            .splineToConstantHeading(Vector2d(scoreXPosition, 20.0.switch), 270.0.switchAngle.rad)
            .splineToConstantHeading(parkPoseCenter.vec(), 0.0.rad)
            .build()

        scoreInnerToParkEdge = d.trajectoryBuilder(scorePoseInner, 90.0.switchAngle.rad)
            .splineToConstantHeading(Vector2d(scoreXPosition, 52.0.switch), 90.0.switchAngle.rad)
            .splineToConstantHeading(parkPoseEdge.vec(), 0.0.rad)
            .build()
        scoreCenterToParkEdge = d.trajectoryBuilder(scorePoseCenter, 90.0.switchAngle.rad)
            .splineToConstantHeading(Vector2d(scoreXPosition, 52.0.switch), 90.0.switchAngle.rad)
            .splineToConstantHeading(parkPoseEdge.vec(), 0.0.rad)
            .build()
        scoreOuterToParkEdge = d.trajectoryBuilder(scorePoseOuter, 90.0.switchAngle.rad)
            .splineToConstantHeading(Vector2d(scoreXPosition, 52.0.switch), 90.0.switchAngle.rad)
            .splineToConstantHeading(parkPoseEdge.vec(), 0.0.rad)
            .build()

        //region BACKUP TRAJECTORIES
        backstageStartToParkCenter = d.trajectoryBuilder(backstageStartPose)
            .splineToSplineHeading(parkPoseCenter, 0.0.rad)
            .build()
        backstageStartToParkEdge = d.trajectoryBuilder(backstageStartPose)
            .splineToSplineHeading(parkPoseEdge, 25.0.switchAngle.rad)
            .build()
        wingStartToParkCenter = d.trajectoryBuilder(wingStartPose)
            .splineTo(Vector2d(-36.0, 48.0.switch), 270.0.switchAngle.rad)
            .splineTo(Vector2d(-12.0, 36.0.switch), 0.0.rad)
            .splineToSplineHeading(parkPoseCenter, 0.0.rad)
            .build()
        wingStartToParkEdge = d.trajectoryBuilder(wingStartPose)
            .splineTo(Vector2d(-36.0, 48.0.switch), 270.0.switchAngle.rad)
            .splineTo(Vector2d(-12.0, 36.0.switch), 0.0.rad)
            .splineToSplineHeading(parkPoseEdge, 25.0.switchAngle.rad)
            .build()
        //endregion
    }
}