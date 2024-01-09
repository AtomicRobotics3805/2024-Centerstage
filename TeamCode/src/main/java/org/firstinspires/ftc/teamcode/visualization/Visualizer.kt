package org.firstinspires.ftc.teamcode.visualization

import com.noahbres.meepmeep.MeepMeep
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.driving.drivers.MecanumDrive
import org.atomicrobotics3805.cflib.driving.localizers.TwoWheelOdometryLocalizer
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.visualization.MeepMeepRobot
import org.atomicrobotics3805.cflib.visualization.MeepMeepVisualizer
import org.firstinspires.ftc.teamcode.drive.CompetitionDriveConstants
import org.firstinspires.ftc.teamcode.interleague.trajectoryFactory.CompetitionTrajectoryFactory
import org.firstinspires.ftc.teamcode.localizers.CompetitionOdometryConstants

fun main() {
    MeepMeepVisualizer.addRobot(
        MeepMeepRobot(
            MecanumDrive(
                CompetitionDriveConstants,
                TwoWheelOdometryLocalizer(CompetitionOdometryConstants)

            ) { CompetitionTrajectoryFactory.backstageStartPose },
            14.5,
            15.0,
            {
                sequential {
                    +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingStartToBackSpikeTape)
                    +Constants.drive.followTrajectory(CompetitionTrajectoryFactory.wingBackSpikeTapeToScore)
                }
            },
            Constants.Color.RED
        )
    )

    MeepMeepVisualizer.run(CompetitionTrajectoryFactory, background = MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
}