package org.firstinspires.ftc.teamcode.leagues.routines.advanced

import org.atomicrobotics3805.cflib.Command
import org.atomicrobotics3805.cflib.Constants
import org.atomicrobotics3805.cflib.parallel
import org.atomicrobotics3805.cflib.sequential
import org.atomicrobotics3805.cflib.utilCommands.Delay
import org.atomicrobotics3805.cflib.utilCommands.OptionCommand
import org.firstinspires.ftc.teamcode.mechanisms.Lid
import org.firstinspires.ftc.teamcode.mechanisms.DetectionMechanism
import org.firstinspires.ftc.teamcode.mechanisms.Intake
import org.firstinspires.ftc.teamcode.mechanisms.PropProcessor
import org.firstinspires.ftc.teamcode.mechanisms.Ramp
import org.firstinspires.ftc.teamcode.leagues.trajectoryFactory.AdvancedTrajectoryFactory

object TwoPlusTwoBackstage {
    val backstageTwoPlusTwo: Command
        get() = sequential {
            +Lid.close
            +OptionCommand("Different signature",
                { DetectionMechanism.selectedPosition },
                Pair(PropProcessor.Selected.LEFT, leftRoutine),
                Pair(PropProcessor.Selected.MIDDLE, centerRoutine),
                Pair(PropProcessor.Selected.RIGHT, rightRoutine))
        }

    val leftRoutine: Command
        get() = sequential {
            +OptionCommand("",
                { Constants.color },
                Pair(Constants.Color.BLUE, sequential {
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeBack)
                        +Lid.close
                    }
                    +Intake.slowEject
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeBackToBackdropOuter)
                        +MechanismRoutines.liftRaise
                    }
                    +parallel {
                        +MechanismRoutines.scoreSingle
                        +sequential {
                            +Delay(1.0)
                            +parallel {
                                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropOuterToInnerStack)
                                +Ramp.threeHigh
                            }
                        }
                    }
                    +MechanismRoutines.intake
                    +Lid.close
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropInner)
                        +sequential {
                            +Delay(2.0)
                            +MechanismRoutines.higherLiftRaise
                        }
                    }
                    +MechanismRoutines.scoreTwo
                    +GeneralShared.backdropInnerToPark
                }),
                Pair(Constants.Color.RED, sequential {
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeFront)
                        +Lid.close
                    }
                    +Intake.slowEject
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeFrontToBackdropInner)
                        +MechanismRoutines.liftRaise
                    }
                    +parallel {
                        +MechanismRoutines.scoreSingle
                        +sequential {
                            +Delay(1.0)
                            +parallel {
                                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropInnerToInnerStack)
                                +Ramp.threeHigh
                            }
                        }
                    }
                    +MechanismRoutines.intake
                    +Lid.close
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropOuter)
                        +sequential {
                            +Delay(2.0)
                            +MechanismRoutines.higherLiftRaise
                        }
                    }
                    +MechanismRoutines.scoreTwo
                    +GeneralShared.backdropOuterToPark
                }))
            }

    val centerRoutine: Command
        get() = sequential {
            +parallel {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeCenter)
                +Lid.close
            }
            +Intake.slowEject
            +parallel {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeCenterToBackdropCenter)
                +MechanismRoutines.liftRaise
            }
            +parallel {
                +MechanismRoutines.scoreSingle
                +sequential {
                    +Delay(1.0)
                    +parallel {
                        +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropCenterToInnerStack)
                        +Ramp.threeHigh
                    }
                }
            }
            +MechanismRoutines.intake
            +parallel {
                +Intake.slowEject
                +Lid.close
            }
            +parallel {
                +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropInner)
                +sequential {
                    +Delay(2.0)
                    +MechanismRoutines.higherLiftRaise
                }
            }
            +MechanismRoutines.scoreTwo
            +GeneralShared.backdropInnerToPark
        }

    val rightRoutine: Command
        get() = OptionCommand("",
            { Constants.color },
            Pair(Constants.Color.BLUE, sequential {
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeFront)
                    +Lid.close
                }
                +Intake.slowEject
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeFrontToBackdropInner)
                    +MechanismRoutines.liftRaise
                }
                +parallel {
                    +MechanismRoutines.scoreSingle
                    +sequential {
                        +Delay(1.0)
                        +parallel {
                            +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropInnerToInnerStack)
                            +Ramp.threeHigh
                        }
                    }
                }
                +MechanismRoutines.intake
                +Lid.close
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropOuter)
                    +sequential {
                        +Delay(2.0)
                        +MechanismRoutines.higherLiftRaise
                    }
                }
                +MechanismRoutines.scoreTwo
                +GeneralShared.backdropOuterToPark
            }),
            Pair(Constants.Color.RED, sequential {
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageStartToTapeBack)
                    +Lid.close
                }
                +Intake.slowEject
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backstageTapeBackToBackdropOuter)
                    +MechanismRoutines.liftRaise
                }
                +parallel {
                    +MechanismRoutines.scoreSingle
                    +sequential {
                        +Delay(1.0)
                        +parallel {
                            +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.backdropOuterToInnerStack)
                            +Ramp.threeHigh
                        }
                    }
                }
                +MechanismRoutines.intake
                +Lid.close
                +parallel {
                    +Constants.drive.followTrajectory(AdvancedTrajectoryFactory.innerStackToBackdropInner)
                    +sequential {
                        +Delay(2.0)
                        +MechanismRoutines.higherLiftRaise
                    }
                }
                +MechanismRoutines.scoreTwo
                +GeneralShared.backdropInnerToPark
            }))
}