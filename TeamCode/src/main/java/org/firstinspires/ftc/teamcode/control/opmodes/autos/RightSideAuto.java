package org.firstinspires.ftc.teamcode.control.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.control.systems.Link;
import org.firstinspires.ftc.teamcode.miscellaneous.Globals;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.control.Positions;

@Autonomous(name = "Right Side Auto", group = "autos")
public class RightSideAuto extends BaseAuto {
    // Non Dynamic Trajectories
    private TrajectorySequence parkTrajectory;
    private TrajectorySequence depositSampleTrajectory;
    private TrajectorySequence specimenPickupTrajectory;

    public static Pose2d COLLECTION_SPOT = Positions.PARKING.plus(new Pose2d());

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
    private static final Vector2d HANG_START_POSITION = Positions.FRONT_CENTER.vec().plus(new Vector2d(7.5, 12));
    private double specimenBarOffset = 0;
    private int hungSpecimens = 0;

    // Held data
    private boolean hasSpecimen = true; // You start holding oen
    private boolean hasSpecimenReadyToPickup = false;

    // Scan Data
    private static final Pose2d SAMPLE_SCAN_LOCATION = new Pose2d(-32.75, -45, Math.toRadians(0));
    private static final Pose2d LAST_SAMPLE_SCAN_LOCATION = new Pose2d(-23.5, -55, Math.toRadians(90));

    public static double SCAN_VELOCITY_PERCENT = 0.3;

    private int collectedSamples = 0;

    /**
     * <p>
     *     Full thing to hang a held specimen.
     * </p>
     */
    private void hangSpecimen() {
        TrajectorySequence hangTrajectory = drive.trajectorySequenceBuilder(Positions.RIGHT_START)
                // Getting into proper position
                .setReversed(true)
                .splineTo(HANG_START_POSITION.plus(new Vector2d(0, specimenBarOffset)), Math.toRadians(0))
                .setReversed(false)

                .build();

        drive.followTrajectorySequence(hangTrajectory);

        specimenBarOffset -= 3;
        hungSpecimens++;
    }

    /**
     * <p>
     *     Resets the bot back to the sample scanning location. If on last pixel, approach from the side.
     *     Else, approach normally, with a lateral strategy. If already grabbed first one, scoot to the right
     *     a little bit first.
     * </p>
     */
    private void resetScan() {
        TrajectorySequence resetTrajectory;
        if (collectedSamples == 2) {
            resetTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .splineTo(LAST_SAMPLE_SCAN_LOCATION.vec(), LAST_SAMPLE_SCAN_LOCATION.getHeading())
                    .build();
        } else if (collectedSamples == 1) {
            resetTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .splineTo(SAMPLE_SCAN_LOCATION.vec().plus(new Vector2d(0, -8)), SAMPLE_SCAN_LOCATION.getHeading())
                    .build();
        } else {
            resetTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .splineTo(SAMPLE_SCAN_LOCATION.vec(), SAMPLE_SCAN_LOCATION.getHeading())
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
        TrajectorySequence scanTrajectory;
        if (collectedSamples == 2) {
            scanTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .setVelConstraint(new MecanumVelocityConstraint(DriveConstants.MAX_VEL * SCAN_VELOCITY_PERCENT, DriveConstants.TRACK_WIDTH))
                    .forward(8)
                    .build();

            claw.wristVertical();
        } else {
            scanTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .setVelConstraint(new MecanumVelocityConstraint(DriveConstants.MAX_VEL * SCAN_VELOCITY_PERCENT, DriveConstants.TRACK_WIDTH))
                    .strafeRight(24)
                    .build();

            claw.wristCenter();
        }

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
        parkTrajectory = drive.trajectorySequenceBuilder(Positions.RIGHT_START)
                .addTemporalMarker(() -> {
                    link.startPosition();

                    claw.crane();

                    vs.down();
                    vs.hingePickup();
                    vs.openClaw();
                })
                .splineTo(Positions.PARKING.vec(), Math.toRadians(0))
                .build();

        // Depositing sample to get turned into a specimen
        depositSampleTrajectory = drive.trajectorySequenceBuilder(SAMPLE_SCAN_LOCATION)
                .addTemporalMarker(() -> claw.crane())
                .splineTo(Positions.PARKING.vec().plus(new Vector2d(3, -5)), Math.toRadians(180))
                .addTemporalMarker(() -> claw.fingerOpen())
                .build();

        // Specimen Pickup
        specimenPickupTrajectory = drive.trajectorySequenceBuilder(Positions.PARKING.plus(new Pose2d(3, -5, Math.toRadians(180))))
                // Important things that need to be set
                .addTemporalMarker(() -> {
                    claw.crane();
                    claw.fingerOpen();

                    vs.down();
                    vs.hingePickup();
                    vs.clawIsOpen();
                })

                // Navigation
                .splineTo(COLLECTION_SPOT.vec(), COLLECTION_SPOT.getHeading())

                // Pickup
                .addTemporalMarker(() -> claw.pickup())
                .waitSeconds(0.2)
                .addTemporalMarker(() -> claw.fingerClose())
                .waitSeconds(0.2)

                // Transfer
                .addTemporalMarker(() -> {
                    link.setCustomPosition(Link.IN + (Link.OUT * 0.3));
                    claw.crane();
                })
                .waitSeconds(0.2)

                .addTemporalMarker(() -> link.startPosition())
                .waitSeconds(0.3)
                .addTemporalMarker(() -> vs.closeClaw())
                .waitSeconds(0.1)
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
        hangSpecimen();

        // Run hanging loop
        States state = States.RESETTING;
        while (!isStopRequested() && opModeIsActive() && continueLoop) {
            telemetry.addData("Hung Specimens", hungSpecimens);
            telemetry.addData("Has Specimen", hasSpecimen);
            telemetry.addData("Has Specimen Ready for Pickup", hasSpecimenReadyToPickup);
            telemetry.addData("Collected Samples", collectedSamples);
            telemetry.addData("Current State", state);

            switch (state) {
                case RESETTING: {
                    resetScan();

                    state = States.SCANNING;

                    break;
                } case SCANNING: {
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

                            state = States.DEPOSITING;
                            break;
                        }
                    }
                    collectedSamples++;
                    break;
                } case DEPOSITING: {
                    drive.followTrajectorySequence(depositSampleTrajectory);

                    if (hasSpecimenReadyToPickup) {
                        state = States.COLLECTING;
                    } else {
                        state = States.RESETTING;
                    }

                    hasSpecimenReadyToPickup = true;

                    break;
                } case COLLECTING: {
                    drive.followTrajectorySequence(specimenPickupTrajectory);

                    hasSpecimen = true;

                    state = States.HANGING;
                    break;
                } case HANGING: {
                    hangSpecimen();

                    hasSpecimen = false;

                    if (collectedSamples > 3) {
                        state = States.RESETTING;
                    } else if (hasSpecimenReadyToPickup) {
                        state = States.COLLECTING;
                    } else {
                        continueLoop = false;
                        break;
                    }

                    break;
                }
            }
        }

        // Ending
        drive.followTrajectorySequence(parkTrajectory);

        Globals.teleopEntryPose = drive.getPoseEstimate();
    }
}
