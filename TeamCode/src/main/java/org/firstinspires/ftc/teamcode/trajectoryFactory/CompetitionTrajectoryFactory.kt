package org.firstinspires.ftc.teamcode.trajectoryFactory

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import org.atomicrobotics3805.cflib.trajectories.ParallelTrajectory
import org.atomicrobotics3805.cflib.trajectories.TrajectoryFactory
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.trajectories.switch
import org.atomicrobotics3805.cflib.trajectories.switchAngle
import org.atomicrobotics3805.cflib.trajectories.toRadians
import org.atomicrobotics3805.cflib.trajectories.translateAcrossField
import org.atomicrobotics3805.cflib.Constants.drive as d

object CompetitionTrajectoryFactory: TrajectoryFactory() {

    //region ***NEW POSES***
    // STARTING POSITIONS
    var startYPosition = 62.75 // y coordinates for starting, how far from side walls
    var wingStartPose = Pose2d() // The start pose that is closest to the opponent's wing
    var backstageStartPose = Pose2d() // The start pose that is closest to the backstage area

    // BACKDROP SCORING POSITIONS
    var scoreXPosition = 48.0 // x coordinates for the backdrop. Varies depending on the robot
    var scorePoseLeft = Pose2d() // When looking at the backdrop, the leftmost pair of slots
    var scorePoseCenter = Pose2d() // When looking at the backdrop, the center pair of slots
    var scorePoseRight = Pose2d() // When looking at the backdrop, the rightmost pair of slots

    // PARKING POSITIONS
    var parkXPosition = 60.0 // x coordinates for parking; how far from the wall
    var parkPoseCenter = Pose2d() // The tile next to the backdrop that is closest to the opponent
    var parkPoseEdge = Pose2d() // The tile next to the backdrop that is closest to the field wall

    // DETECTION POSITIONS
    var detectionXPosition = 33.8 // x coordinates for detection; depends on detection method
    var wingDetectPose = Pose2d() // The location for detecting closest to the wings
    var backstageDetectPose = Pose2d() // The location for detecting closest to the backstage

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
    lateinit var wingStartToDetect: ParallelTrajectory

    lateinit var wingDetectToFrontSpikeTape: ParallelTrajectory
    lateinit var wingDetectToCenterSpikeTape: ParallelTrajectory
    lateinit var wingDetectToBackSpikeTape: ParallelTrajectory

    lateinit var wingFrontSpikeTapeToScoreLeft: ParallelTrajectory
    lateinit var wingCenterSpikeTapeToScoreCenter: ParallelTrajectory
    lateinit var wingBackSpikeTapeToScoreRight: ParallelTrajectory

    // BACKSTAGE TRAJECTORIES
    lateinit var backstageStartToDetect: ParallelTrajectory

    lateinit var backstageDetectToFrontSpikeTape: ParallelTrajectory
    lateinit var backstageDetectToCenterSpikeTape: ParallelTrajectory
    lateinit var backstageDetectToBackSpikeTape: ParallelTrajectory

    lateinit var backstageFrontSpikeTapeToScoreLeft: ParallelTrajectory
    lateinit var backstageCenterSpikeTapeToScoreCenter: ParallelTrajectory
    lateinit var backstageBackSpikeTapeToScoreRight: ParallelTrajectory

    // BACKUP TRAJECTORIES
    lateinit var backstageStartToPark: ParallelTrajectory
    lateinit var wingStartToPark: ParallelTrajectory

    // GENERAL TRAJECTORIES
    lateinit var scoreRightToParkA : ParallelTrajectory
    lateinit var scoreCenterToParkA : ParallelTrajectory
    lateinit var scoreLeftToParkA : ParallelTrajectory
    lateinit var scoreRightToParkB : ParallelTrajectory
    lateinit var scoreCenterToParkB : ParallelTrajectory
    lateinit var scoreLeftToParkB : ParallelTrajectory

    // BACKUP TRAJECTORIES
    lateinit var backstageStartToParkA : ParallelTrajectory
    lateinit var backstageStartToParkB : ParallelTrajectory


    //endregion

    //region Old poses & paths
    var upstageStartPose = Pose2d()
    var upstageDetectPose = Pose2d()
    var upstageScorePose = Pose2d()
    var upstageParkPoseA = Pose2d()
    var upstageParkPoseB = Pose2d()

    var downstageStartPose = Pose2d()
    var downstageDetectPose = Pose2d()

    lateinit var upstageStartToDetect: ParallelTrajectory
    lateinit var upstageDetectToScore: ParallelTrajectory
    lateinit var upstageScoreToParkA: ParallelTrajectory

    lateinit var downstageStartToDetect: ParallelTrajectory
    lateinit var downstageDetectToScore: ParallelTrajectory

    // FALLBACK, IN CASE THINGS GO BADLY
    lateinit var upstageFallbackParkA: ParallelTrajectory
    lateinit var downstageFallbackParkA: ParallelTrajectory
    lateinit var upstageFallbackParkB: ParallelTrajectory
    lateinit var downstageFallbackParkB: ParallelTrajectory
    //endregion

    override fun initialize() {
        super.initialize()

        wingStartPose = Pose2d(-36.0, startYPosition.switch, 270.0.switchAngle.rad)
        backstageStartPose = Pose2d(12.0, startYPosition.switch, 270.0.switchAngle.rad)

        scorePoseLeft = Pose2d(scoreXPosition, 43.0.translateAcrossField, 180.0.rad)
        scorePoseCenter = Pose2d(scoreXPosition, 36.0.translateAcrossField, 180.0.rad)
        scorePoseRight = Pose2d(scoreXPosition, 29.0.translateAcrossField, 180.0.rad)

        parkPoseCenter = Pose2d(parkXPosition, 12.0.switch, 180.0.rad)
        parkPoseEdge = Pose2d(parkXPosition, 60.0.switch, 180.0.rad)

        wingDetectPose = Pose2d(-36.0, detectionXPosition.switch, 270.0.switchAngle.rad)
        backstageDetectPose = Pose2d(12.0, detectionXPosition.switch, 270.0.switchAngle.rad)

        wingFrontSpikeTape = Pose2d(-46.0, 38.0.switch, 270.0.switchAngle.rad)
        wingCenterSpikeTape = Pose2d(-45.0, 24.0.switch, 0.0.switchAngle.rad)
        wingBackSpikeTape = Pose2d(-24.0, 38.0.switch, 270.0.switchAngle.rad)

        backstageFrontSpikeTape = Pose2d(0.5, 38.0.switch, 270.0.switchAngle.rad)
        backstageCenterSpikeTape = Pose2d(23.0, 24.0.switch, 180.0.switchAngle.rad)
        backstageBackSpikeTape = Pose2d(24.0, 38.0.switch, 270.0.switchAngle.rad)

        wingStartToDetect = d.trajectoryBuilder(wingStartPose)
            .lineToLinearHeading(wingDetectPose)
            .build()
        backstageStartToDetect = d.trajectoryBuilder(backstageStartPose)
            .lineToLinearHeading(backstageDetectPose)
            .build()


        //region DETECT TO PURPLE PIXEL SCORE
        wingDetectToFrontSpikeTape = d.trajectoryBuilder(wingDetectPose, 90.0.switchAngle.rad)
            .splineToLinearHeading(wingFrontSpikeTape, 180.0.rad)
            .build()
        wingDetectToCenterSpikeTape = d.trajectoryBuilder(wingDetectPose, wingDetectPose.heading - 90.0.switchAngle.toRadians)
            .splineToSplineHeading(wingCenterSpikeTape, 270.0.switchAngle.rad)
            .build()
        wingDetectToBackSpikeTape = d.trajectoryBuilder(wingDetectPose, 90.0.switchAngle.rad)
            .splineToLinearHeading(wingBackSpikeTape, 0.0.rad)
            .build()
        backstageDetectToFrontSpikeTape = d.trajectoryBuilder(backstageDetectPose, 90.0.switchAngle.rad)
            .splineToLinearHeading(backstageFrontSpikeTape, 180.0.rad)
            .build()
        backstageDetectToCenterSpikeTape = d.trajectoryBuilder(backstageDetectPose, backstageDetectPose.heading + 90.0.switchAngle.toRadians)
            .splineToSplineHeading(backstageCenterSpikeTape, 270.0.switchAngle.rad)
            .build()
        backstageDetectToBackSpikeTape = d.trajectoryBuilder(backstageDetectPose, 90.0.switchAngle.rad)
            .splineToLinearHeading(backstageBackSpikeTape, 0.0.rad)
            .build()
        //endregion

        backstageFrontSpikeTapeToScoreLeft = d.trajectoryBuilder(backstageFrontSpikeTape, 0.0.rad)
            .splineToSplineHeading(scorePoseLeft, 0.0.rad)
            .build()
        backstageCenterSpikeTapeToScoreCenter = d.trajectoryBuilder(backstageCenterSpikeTape, 0.0.rad)
            .splineToSplineHeading(scorePoseCenter, 0.0.rad)
            .build()
        backstageBackSpikeTapeToScoreRight = d.trajectoryBuilder(backstageBackSpikeTape, 0.0.rad)
            .splineToSplineHeading(scorePoseRight, 0.0.rad)
            .build()

        wingFrontSpikeTapeToScoreLeft = d.trajectoryBuilder(wingFrontSpikeTape, 0.0.rad)
            .splineTo(Vector2d(-12.0, 36.0.switch), 0.0.rad)
            .splineToSplineHeading(scorePoseLeft, 0.0.rad)
            .build()
        wingCenterSpikeTapeToScoreCenter = d.trajectoryBuilder(wingCenterSpikeTape, 90.0.switchAngle.rad)
            .splineToConstantHeading(Vector2d(-12.0, 36.0.switch), 0.0.rad) // Can also be splineTo() instead of splineToConstantHeading()
            .splineToSplineHeading(scorePoseCenter, 0.0.rad)
            .build()
        wingBackSpikeTapeToScoreRight = d.trajectoryBuilder(wingBackSpikeTape)
            .splineTo(Vector2d(-12.0, 36.0.switch), 0.0.rad)
            .splineToSplineHeading(scorePoseRight, 0.0.rad)
            .build()

        //region Old Trajectories
        // ***POSES***

        // Upstage (Closer to backdrop)
        upstageStartPose = Pose2d(12.0, 62.75.switch, 270.0.switchAngle.rad)
        upstageDetectPose = Pose2d(12.0, 33.8.switch, 270.0.switchAngle.rad)

        upstageScorePose = Pose2d(48.0, 36.0.switch, 0.0.rad)
        upstageParkPoseA = Pose2d(60.0, 60.0.switch, 180.0.rad)
        upstageParkPoseB = Pose2d(60.0, 12.0.switch, 180.0.rad)

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

        // FALLBACK
        upstageFallbackParkA = d.trajectoryBuilder(upstageStartPose)
            .splineToSplineHeading(upstageParkPoseA, 0.0.rad)
            .build()
        upstageFallbackParkB = d.trajectoryBuilder(upstageStartPose)
            .splineToSplineHeading(upstageParkPoseB, 0.0.rad)
            .build()
        //endregion
    }
}