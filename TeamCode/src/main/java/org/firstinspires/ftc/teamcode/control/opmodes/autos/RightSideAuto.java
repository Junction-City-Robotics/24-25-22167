package org.firstinspires.ftc.teamcode.control.opmodes.autos;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.control.systems.Link;
import org.firstinspires.ftc.teamcode.miscellaneous.Globals;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.control.Positions;

@Autonomous(name = "Right Side Auto", group = "autos")
@Config
public class RightSideAuto extends BaseAuto {
    // Non Dynamic Trajectories
    private TrajectorySequence parkTrajectory;
    private TrajectorySequence nearDepositSampleTrajectory;
    private TrajectorySequence extendedDepositSampleTrajectory;
    private TrajectorySequence specimenPickupTrajectory;
    private TrajectorySequence firstSpecimenPickupTrajectory;

    // Logic Structure
    private enum States {
        HANGING, // Hanging held specimen
        SCANNING, // Scanning to pick up sample
        RESETTING, // Resetting Scan
        COLLECTING, // Collecting samples that got turned into a specimen
        DEPOSITING // Depositing sample to get turned into a specimen
    }
    private boolean continueLoop = true;

    // Hanging Data
    private static final Vector2d HANG_START_POSITION = Positions.FRONT_CENTER.vec().plus(new Vector2d(7.5, -8));
    private double specimenBarOffset = 0;
    private int hungSpecimens = 0;

    // Held data
    private boolean hasSpecimen = true; // You start holding oen
    private boolean hasSpecimenReadyToPickup = false;

    // Scan Data
    public static Pose2d SAMPLE_SCAN_LOCATION = new Pose2d(-22.5, -36, Math.toRadians(270));

    public static Pose2d DROP_OFF_SPOT = Positions.PARKING.plus(new Pose2d(5, -16, Math.toRadians(180)));
    public static Pose2d COLLECTION_SPOT = DROP_OFF_SPOT.plus(new Pose2d(7, 6, 0));

    public static double SCAN_VELOCITY_PERCENT = 0.22;

    private int collectedSamples = 0;

    /**
     * <p>
     *     Full thing to hang a held specimen.
     * </p>
     */
    private void hangSpecimen(boolean firstHang) {
        TrajectorySequence hangTrajectory;
        if (firstHang) {
            hangTrajectory = drive.trajectorySequenceBuilder(Positions.RIGHT_START)
                    // Getting into proper position
                    .addTemporalMarker(() -> {
                        vs.hang();
                        vs.closeClaw();
                        link.startPosition();
                    })

                    .setReversed(true)
                    .splineTo(HANG_START_POSITION.plus(new Vector2d(0, specimenBarOffset)), Math.toRadians(0))
                    .setReversed(false)

                    .addDisplacementMarker(9.5, () -> vs.hingeForceHang())
                    .addTemporalMarker(9.5 + 0.15, () -> vs.openClaw())

                    // Useful move
                    .addTemporalMarker(0.5, () -> claw.crane())

                    .build();
        } else {
            hangTrajectory = drive.trajectorySequenceBuilder(Positions.RIGHT_START)
                    // Getting into proper position
                    .addTemporalMarker(() -> {
                        claw.deposit();

                        link.setCustomPosition(Link.IN + (Link.OUT * 0.3));

                        vs.openClaw();
                        vs.down();
                        vs.hingePickup();
                    })
                    .addTemporalMarker(0.7, () -> link.startPosition())
                    .addTemporalMarker(1.2, () -> vs.closeClaw())
                    .addTemporalMarker(1.4, () -> {
                        claw.fingerOpen();
                        claw.crane();
                        vs.hang();
                    })
                    .addTemporalMarker(1.5, () -> vs.hingeForceHang())

                    .setReversed(true)
                    .splineTo(HANG_START_POSITION.plus(new Vector2d(0, specimenBarOffset)), Math.toRadians(0))
                    .setReversed(false)
                    .waitSeconds(0.65)
                    .addTemporalMarker(() -> vs.openClaw())

                    .build();
        }


        drive.followTrajectorySequence(hangTrajectory);

        specimenBarOffset += 4;
        hungSpecimens++;
    }

    /**
     * <p>
     *     Resets the bot back to the sample scanning location. If on last pixel, approach from the side.
     *     Else, approach normally, with a lateral strategy. If already grabbed first one, scoot to the right
     *     a little bit first.
     * </p>
     */
    private void resetScan(boolean reverse) {
        link.startPosition();

        double added = 0;
        if (collectedSamples == 1)
            added = 4;
        else if (collectedSamples == 2)
            added = 11;

        TrajectorySequence resetTrajectory;
        if (reverse) {
            resetTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .setReversed(true)
                    .splineTo(SAMPLE_SCAN_LOCATION.vec().plus(new Vector2d(0, -added)), -SAMPLE_SCAN_LOCATION.getHeading())
                    .setReversed(false)
                    .build();
        } else {
            resetTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .splineTo(HANG_START_POSITION.plus(new Vector2d(-11, -12)), SAMPLE_SCAN_LOCATION.getHeading())
                    .splineTo(SAMPLE_SCAN_LOCATION.vec().plus(new Vector2d(0, -added)), SAMPLE_SCAN_LOCATION.getHeading())
                    .build();
        }

        drive.followTrajectorySequence(resetTrajectory);
    }

    /**
     * <p>
     *     Function to start the async scan, based on amount of already collected samples. Moves
     *     in accordance to velocity constraint.
     * </p>
     */
    private void startScan() {
        TrajectorySequence scanTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .setVelConstraint(new MecanumVelocityConstraint(DriveConstants.MAX_VEL * SCAN_VELOCITY_PERCENT, DriveConstants.TRACK_WIDTH))
                    .splineTo(drive.getPoseEstimate().plus(new Pose2d(0.5, -20, 0)).vec(), Math.toRadians(270))
                    .build();

            claw.wristVertical();

        drive.followTrajectorySequenceAsync(scanTrajectory);
    }

    @Override
    public void beforeStart() {
        // Pre start vs setup
        vs.completeSetup();
        vs.setPower(0.0);

        telemetry.addData("Trajectories", "(Starting) WAIT . . .");
        telemetry.update();

        // Basic parking trajectory to finish the auto strong
        parkTrajectory = drive.trajectorySequenceBuilder(new Pose2d(HANG_START_POSITION.getX(), HANG_START_POSITION.getY(), Math.toRadians(180)))
                .addTemporalMarker(() -> {
                    link.startPosition();

                    claw.crane();

                    vs.down();
                    vs.hingePickup();
                    vs.openClaw();
                })
                .splineTo(Positions.PARKING.vec().plus(new Vector2d(7, - 5 )), Math.toRadians(0))
                .build();

        // Depositing sample to get turned into a specimen
        extendedDepositSampleTrajectory = drive.trajectorySequenceBuilder(SAMPLE_SCAN_LOCATION)
                .addTemporalMarker(() -> {
                    claw.crane();
                })
                .addTemporalMarker(0.6, () -> link.extendedPosition())
                .splineTo(DROP_OFF_SPOT.vec().plus(new Vector2d(12, 6)), Math.toRadians(200))
                .addTemporalMarker(1.0, () -> claw.fingerOpen())
                .addTemporalMarker(() -> link.startPosition())
                .build();

        nearDepositSampleTrajectory = drive.trajectorySequenceBuilder(SAMPLE_SCAN_LOCATION)
                .addTemporalMarker(() -> {
                    claw.crane();
                    link.startPosition();
                })
                .splineTo(DROP_OFF_SPOT.vec(), DROP_OFF_SPOT.getHeading())
                .addTemporalMarker(() -> claw.fingerOpen())
                .waitSeconds(0.3)
                .addTemporalMarker(() -> link.startPosition())
                .build();

        telemetry.addData("Trajectories", "(Halfway) WAIT . . .");
        telemetry.update();

        // Specimen Pickup
        firstSpecimenPickupTrajectory = drive.trajectorySequenceBuilder(extendedDepositSampleTrajectory.end())
                // Important things that need to be set
                .addTemporalMarker(() -> {
                    claw.crane();
                    claw.fingerOpen();
                    claw.wristCenter();

                    link.setCustomPosition(Link.IN + (Link.OUT * 0.3));

                    vs.down();
                    vs.hingePickup();
                    vs.clawIsOpen();
                })

                .setReversed(true)
                .splineToConstantHeading(COLLECTION_SPOT.vec().plus(new Vector2d(10, 0)), Math.toRadians(0))
                .setReversed(false)

                .setVelConstraint(new MecanumVelocityConstraint(DriveConstants.MAX_VEL * 0.33, DriveConstants.TRACK_WIDTH))
                .splineTo(COLLECTION_SPOT.vec(), COLLECTION_SPOT.getHeading())
                .resetConstraints()

                .addTemporalMarker(() -> claw.pickup())
                .waitSeconds(0.3)
                .addTemporalMarker(() -> claw.fingerClose())
                .waitSeconds(0.3)

                .build();

        specimenPickupTrajectory = drive.trajectorySequenceBuilder(Positions.RIGHT_START)
                // Important things that need to be set
                .addTemporalMarker(() -> {
                    claw.crane();
                    claw.fingerOpen();
                    claw.wristCenter();

                    link.setCustomPosition(Link.IN + (Link.OUT * 0.3));

                    vs.down();
                    vs.hingePickup();
                    vs.clawIsOpen();
                })

                // Navigation
                .splineTo(COLLECTION_SPOT.vec(), COLLECTION_SPOT.getHeading())

                // Pickup
                .waitSeconds(0.4)
                .addTemporalMarker(() -> claw.pickup())
                .waitSeconds(0.4)
                .addTemporalMarker(() -> claw.fingerClose())
                .waitSeconds(0.2)

                // Transfer
                .addTemporalMarker(() -> {
                    claw.crane();
                })
                .waitSeconds(0.3)

                .addTemporalMarker(() -> link.startPosition())
                .waitSeconds(0.3)
                .addTemporalMarker(() -> vs.closeClaw())
                .waitSeconds(0.2)
                .addTemporalMarker(() -> claw.fingerOpen())

                .build();

        telemetry.addData("Trajectories", "(Complete) READY TO RUN");
        telemetry.update();

        drive.setPoseEstimate(Positions.RIGHT_START);
    }

    @Override
    public void autoContents() {
        // Locking things into place from the get-go
        vs.closeClaw();
        vs.setPower(1.0);
        claw.fingerOpen();
        link.startPosition();
        claw.wristCenter();

        // Hanging first specimen (Held)
        hangSpecimen(true);

        // Run hanging loop
        boolean firstReset = true;
        boolean firstCollect = true;

        States state = States.RESETTING;
        while (!isStopRequested() && opModeIsActive() && continueLoop) {
            telemetry.addData("Hung Specimens", hungSpecimens);
            telemetry.addData("Has Specimen", hasSpecimen);
            telemetry.addData("Has Specimen Ready for Pickup", hasSpecimenReadyToPickup);
            telemetry.addData("Collected Samples", collectedSamples);
            telemetry.addData("Current State", state);
            telemetry.update();

            switch (state) {
                case RESETTING: {
                    vs.down();
                    vs.hingePickup();
                    vs.openClaw();

                    resetScan(!firstReset);
                    firstReset = false;

                    state = States.SCANNING;

                    break;
                } case SCANNING: {
                    vs.down();
                    vs.hingePickup();
                    vs.openClaw();

                    startScan();
                    while (!isStopRequested() && opModeIsActive()) {
                        drive.update();

                        if (sensors.getDistance() <= 4.5) {
                            drive.setWeightedDrivePower(new Pose2d());
                            waitSeconds(0.3);

                            claw.pickup();
                            waitSeconds(0.3);

                            claw.fingerClose();
                            waitSeconds(0.25);

                            claw.crane();
                            link.startPosition();

                            state = States.DEPOSITING;
                            break;
                        }
                    }
                    collectedSamples++;
                    break;
                } case DEPOSITING: {
                    drive.followTrajectorySequence(extendedDepositSampleTrajectory);

                    if (collectedSamples == 2) { // STOPPING AT 2 COLLECTED
                        state = States.COLLECTING;
                    } else {
                        state = States.RESETTING;
                    }

                    hasSpecimenReadyToPickup = true;

                    break;
                } case COLLECTING: {
                    drive.followTrajectorySequence(firstSpecimenPickupTrajectory);

                    hasSpecimen = true;

                    state = States.HANGING;
                    break;
                } case HANGING: {
                    telemetry.addData("Hung", hungSpecimens);
                    telemetry.update();
                    hangSpecimen(false);

                    hasSpecimen = false;
                    telemetry.addData("Hung", hungSpecimens);
                    telemetry.update();
                    if (hungSpecimens == 3) {
                        continueLoop = false;
                    }

//                    if (collectedSamples > 2) { // USUALLY 3
//                        state = States.RESETTING;
//                    } else
//                        if (hungSpecimens < 4) {
                        state = States.COLLECTING;
//                    } else {
//                        continueLoop = false;
//                        break;
//                    }

                    break;
                }
            }
        }

        // Ending
        drive.followTrajectorySequence(parkTrajectory);

        Globals.teleopEntryPose = drive.getPoseEstimate();
    }
}
