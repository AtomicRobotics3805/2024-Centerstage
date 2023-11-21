package org.firstinspires.ftc.teamcode.trajectoryFactory

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import org.atomicrobotics3805.cflib.trajectories.ParallelTrajectory
import org.atomicrobotics3805.cflib.trajectories.TrajectoryFactory
import org.atomicrobotics3805.cflib.trajectories.rad
import org.atomicrobotics3805.cflib.trajectories.switch
import org.atomicrobotics3805.cflib.trajectories.switchAngle
import org.atomicrobotics3805.cflib.trajectories.v
import org.atomicrobotics3805.cflib.Constants.drive as d

/**
 * The trajectory factory containing advanced paths beyond 2+0
 */
object AdvancedTrajectoryFactory: TrajectoryFactory() {
    //region Variables
    val startY    =  62.75
    val backdropX =  54.0
    val stackX    = -55.0
    val parkX     =  60.0
    val corridor  =  12.0
    //endregion

    //region Initialization
    var startBackstage      = Pose2d()
    var backstageFrontTape  = Pose2d()
    var backstageCenterTape = Pose2d()
    var backstageBackTape   = Pose2d()
    var backdropOuter       = Pose2d()
    var backdropCenter      = Pose2d()
    var backdropInner       = Pose2d()
    var centerStack         = Pose2d()
    var innerStack          = Pose2d()
    var parkEdge            = Pose2d()
    var parkCenter          = Pose2d()

    lateinit var backstageStartToTapeFront:           ParallelTrajectory
    lateinit var backstageStartToTapeCenter:          ParallelTrajectory
    lateinit var backstageStartToTapeBack:            ParallelTrajectory
    lateinit var backstageTapeFrontToBackdropInner:   ParallelTrajectory
    lateinit var backstageTapeCenterToBackdropCenter: ParallelTrajectory
    lateinit var backstageTapeBackToBackdropOuter:    ParallelTrajectory

    lateinit var backdropOuterToCenterStack:          ParallelTrajectory
    lateinit var backdropCenterToCenterStack:         ParallelTrajectory
    lateinit var backdropInnerToCenterStack:          ParallelTrajectory
    lateinit var centerStackToBackdropOuter:          ParallelTrajectory
    lateinit var centerStackToBackdropCenter:         ParallelTrajectory
    lateinit var centerStackToBackdropInner:          ParallelTrajectory

    lateinit var backdropOuterToInnerStack:           ParallelTrajectory
    lateinit var backdropCenterToInnerStack:          ParallelTrajectory
    lateinit var backdropInnerToInnerStack:           ParallelTrajectory
    lateinit var innerStackToBackdropOuter:           ParallelTrajectory
    lateinit var innerStackToBackdropCenter:          ParallelTrajectory
    lateinit var innerStackToBackdropInner:           ParallelTrajectory

    lateinit var backdropOuterToParkOuter:            ParallelTrajectory
    lateinit var backdropCenterToParkOuter:           ParallelTrajectory
    lateinit var backdropInnerToParkOuter:            ParallelTrajectory
    lateinit var backdropOuterToParkInner:            ParallelTrajectory
    lateinit var backdropCenterToParkInner:           ParallelTrajectory
    lateinit var backdropInnerToParkInner:            ParallelTrajectory
    //endregion

    private fun backstagePoses() {
        startBackstage      = Pose2d(12.0,   startY.switch, 270.0.switchAngle.rad)
        backstageFrontTape  = Pose2d(0.5,    38.0.switch,   270.0.switchAngle.rad)
        backstageCenterTape = Pose2d(23.0,   24.0.switch,   180.0.switchAngle.rad)
        backstageBackTape   = Pose2d(24.0,   38.0.switch,   270.0.switchAngle.rad)
        backdropOuter       = Pose2d(backdropX, 42.0.switch,   180.0.rad)
        backdropCenter      = Pose2d(backdropX, 36.0.switch,   180.0.rad)
        backdropInner       = Pose2d(backdropX, 29.0.switch,   180.0.rad)
        centerStack         = Pose2d(stackX,    24.0.switch,   180.0.rad)
        innerStack          = Pose2d(stackX,    12.0.switch,   180.0.rad)
        parkEdge            = Pose2d(parkX,     60.0.switch,   180.0.rad)
        parkCenter          = Pose2d(parkX,     12.0.switch,   180.0.rad)
    }

    private fun wingPoses() { }

    private fun backstageTrajectories() {
        backstageStartToTapeFront = d.trajectoryBuilder(startBackstage)
            .forward(20.0)
            .splineToLinearHeading(backstageFrontTape, 180.0.rad)
            .build()
        backstageStartToTapeCenter = d.trajectoryBuilder(startBackstage)
            .splineToSplineHeading(backstageCenterTape, 270.0.switchAngle.rad)
            .build()
        backstageStartToTapeBack = d.trajectoryBuilder(startBackstage)
            .forward(20.0)
            .splineToLinearHeading(backstageBackTape, 0.0.rad)
            .build()
        backstageTapeFrontToBackdropInner = d.trajectoryBuilder(backstageFrontTape, 0.0.rad)
            .splineToSplineHeading(backdropInner, 0.0.rad)
            .build()
        backstageTapeCenterToBackdropCenter = d.trajectoryBuilder(backstageCenterTape, 0.0.rad)
            .splineToSplineHeading(backdropCenter, 0.0.switchAngle.rad)
            .build()
        backstageTapeBackToBackdropOuter = d.trajectoryBuilder(backstageBackTape, 0.0.rad)
            .splineToSplineHeading(backdropOuter, 0.0.rad)
            .build()
    }

    private fun wingTrajectories() {

    }

    private fun sharedTrajectories() {
        backdropInnerToCenterStack = d.trajectoryBuilder(backdropInner, 180.0.rad)
            .splineToSplineHeading(Pose2d(24.0, corridor.switch, 180.0.rad), 180.0.rad)
            .forward(74.0)
            .splineToConstantHeading(centerStack.v, 180.0.rad)
            .build()
        backdropCenterToCenterStack = d.trajectoryBuilder(backdropCenter, 180.0.rad)
            .splineToSplineHeading(Pose2d(24.0, corridor.switch, 180.0.rad), 180.0.rad)
            .forward(74.0)
            .splineToConstantHeading(centerStack.v, 180.0.rad)
            .build()
        backdropOuterToCenterStack = d.trajectoryBuilder(backdropOuter, 180.0.rad)
            .splineToSplineHeading(Pose2d(24.0, corridor.switch, 180.0.rad), 180.0.rad)
            .forward(74.0)
            .splineToConstantHeading(centerStack.v, 180.0.rad)
            .build()
        centerStackToBackdropOuter = d.trajectoryBuilder(centerStack)
            .splineToConstantHeading(Vector2d(-50.0, corridor.switch), 0.0.rad)
            .lineTo(Vector2d(24.0, corridor.switch))
            .splineToSplineHeading(backdropOuter, 0.0.rad)
            .build()
        centerStackToBackdropCenter = d.trajectoryBuilder(centerStack)
            .splineToConstantHeading(Vector2d(-50.0, corridor.switch), 0.0.rad)
            .lineTo(Vector2d(24.0, 12.0.switch))
            .splineToSplineHeading(backdropCenter, 0.0.rad)
            .build()
        centerStackToBackdropInner = d.trajectoryBuilder(centerStack)
            .splineToConstantHeading(Vector2d(-50.0, 12.0.switch), 0.0.rad)
            .lineTo(Vector2d(24.0, 12.0.switch))
            .splineToSplineHeading(backdropInner, 0.0.rad)
            .build()
        backdropInnerToInnerStack = d.trajectoryBuilder(backdropInner, 180.0.rad)
            .splineToSplineHeading(Pose2d(24.0, 12.0.switch, 180.0.rad), 180.0.rad)
            .splineToConstantHeading(innerStack.v, 180.0.rad)
            .build()
        backdropCenterToInnerStack = d.trajectoryBuilder(backdropCenter, 180.0.rad)
            .splineToSplineHeading(Pose2d(24.0, 12.0.switch, 180.0.rad), 180.0.rad)
            .splineToConstantHeading(innerStack.v, 180.0.rad)
            .build()
        backdropOuterToInnerStack = d.trajectoryBuilder(backdropOuter, 180.0.rad)
            .splineToSplineHeading(Pose2d(24.0, 12.0.switch, 180.0.rad), 180.0.rad)
            .splineToConstantHeading(innerStack.v, 180.0.rad)
            .build()
        innerStackToBackdropOuter = d.trajectoryBuilder(innerStack)
            .lineTo(Vector2d(24.0, 12.0.switch))
            .splineToSplineHeading(backdropOuter, 0.0.rad)
            .build()
        innerStackToBackdropCenter = d.trajectoryBuilder(innerStack)
            .lineTo(Vector2d(24.0, 12.0.switch))
            .splineToSplineHeading(backdropCenter, 0.0.rad)
            .build()
        innerStackToBackdropInner = d.trajectoryBuilder(innerStack)
            .lineTo(Vector2d(24.0, 12.0.switch))
            .splineToSplineHeading(backdropInner, 0.0.rad)
            .build()
        backdropOuterToParkOuter = d.trajectoryBuilder(backdropOuter, 90.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(backdropX-5.0, 55.0.switch), 90.0.switchAngle.rad)
            .splineToConstantHeading(parkEdge.vec(), 0.0.rad)
            .build()
        backdropCenterToParkOuter = d.trajectoryBuilder(backdropCenter, 90.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(backdropX-5.0, 55.0.switch), 90.0.switchAngle.rad)
            .splineToConstantHeading(parkEdge.vec(), 0.0.rad)
            .build()
        backdropInnerToParkOuter = d.trajectoryBuilder(backdropInner, 90.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(backdropX-5.0, 55.0.switch), 90.0.switchAngle.rad)
            .splineToConstantHeading(parkEdge.vec(), 0.0.rad)
            .build()
        backdropOuterToParkInner = d.trajectoryBuilder(backdropOuter, 270.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(backdropX-5.0, 17.0.switch), 270.0.switchAngle.rad)
            .splineToConstantHeading(parkCenter.vec(), 0.0.rad)
            .build()
        backdropCenterToParkInner = d.trajectoryBuilder(backdropCenter, 270.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(backdropX-5.0, 17.0.switch), 270.0.switchAngle.rad)
            .splineToConstantHeading(parkCenter.vec(), 0.0.rad)
            .build()
        backdropInnerToParkInner = d.trajectoryBuilder(backdropInner, 270.0.switchAngle.rad)
            .forward(1.0)
            .splineToConstantHeading(Vector2d(backdropX-5.0, 17.0.switch), 270.0.switchAngle.rad)
            .splineToConstantHeading(parkCenter.vec(), 0.0.rad)
            .build()
    }

    override fun initialize() {
        super.initialize()
        initializePoses()
        initializeTrajectories()
    }
    private fun initializePoses() {
        backstagePoses()
        wingPoses()
    }
    private fun initializeTrajectories() {
        backstageTrajectories()
        wingTrajectories()
        sharedTrajectories()
    }
}