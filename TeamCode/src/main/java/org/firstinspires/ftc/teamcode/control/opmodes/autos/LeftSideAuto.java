package org.firstinspires.ftc.teamcode.control.opmodes.autos;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.control.Positions;
import org.firstinspires.ftc.teamcode.control.systems.Link;
import org.firstinspires.ftc.teamcode.miscellaneous.Globals;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Left Side Auto", group = "autos")
@Config
public class LeftSideAuto extends BaseAuto {
    // Scan locations
    private static final Pose2d SAMPLE_SCAN_LOCATION = new Pose2d(-32.75, 45, Math.toRadians(0));
    private static final Pose2d LAST_SAMPLE_SCAN_LOCATION = new Pose2d(-23.5, 55, Math.toRadians(90));

    // Trajectories
    private TrajectorySequence firstHangTrajectory;
    private TrajectorySequence parkingTrajectory;
    private TrajectorySequence sampleToBucketTrajectory;

    // Sample collection data
    private int collectedSamples = 0;
    private int totalResets = 0;

    // Scan Constants (Can be changed through dashboard)
    private enum States {
        RESETTING,
        SEARCHING,
        DEPOSITING
    }

    public static double VELOCITY_PERCENT = 0.3;

    public static boolean TIMED_SCAN = true;
    public static double SCAN_TIME = 20; // (Seconds)

    public static boolean COUNT_RESETS_TILL_ABORT = false;
    public static int RESETS_TILL_ABORT = 3;

    /**
     * <p>
     * A dynamic way to create trajectories and put samples into the top basket.
     * This creates a complete trajectory, that navigates to buckets, drops held sample off,
     * and resets the IO systems for next activity. It is dynamic because it creates a new
     * trajectory based off of current position, and not a static preset.
     * </p>
     */
    private void depositSampleIntoBucket() {
        drive.followTrajectorySequence(sampleToBucketTrajectory);
    }

    private void scan() {
        telemetry.addData("Scan", "BUILDING");
        telemetry.update();

        TrajectorySequence scanTrajectory;
        if (collectedSamples == 0)
            scanTrajectory = drive.trajectorySequenceBuilder(SAMPLE_SCAN_LOCATION)
                .setVelConstraint(new MecanumVelocityConstraint(DriveConstants.MAX_VEL * VELOCITY_PERCENT, DriveConstants.TRACK_WIDTH))
                .strafeLeft(24)
                .build();

        else if (collectedSamples == 1)
            scanTrajectory = drive.trajectorySequenceBuilder(SAMPLE_SCAN_LOCATION.plus(new Pose2d(0.0, 8.0, 0)))
                    .setVelConstraint(new MecanumVelocityConstraint(DriveConstants.MAX_VEL * VELOCITY_PERCENT, DriveConstants.TRACK_WIDTH))
                    .strafeLeft(24)
                    .build();

        else // Last One
            scanTrajectory = drive.trajectorySequenceBuilder(LAST_SAMPLE_SCAN_LOCATION)
                    .setVelConstraint(new MecanumVelocityConstraint(DriveConstants.MAX_VEL * VELOCITY_PERCENT, DriveConstants.TRACK_WIDTH))
                    .forward(8)
                    .build();

        drive.followTrajectorySequenceAsync(scanTrajectory);

        telemetry.addData("Scan", "RUNNING");
        telemetry.update();
    }

    /**
     * <p>
     * When a scan has been ran, and failed, it needs to be reset (Like a typewriter).
     * This function assembles a new trajectory to get there, based on current position,
     * and then executes it.
     * </p>
     */
    private void resetScan() {
        // Changing scan location based on last scan method, or first two (See other comments for more detail)
        TrajectorySequence resetToSampleScanTrajectory;
        if (collectedSamples == 2) {
            resetToSampleScanTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .splineToLinearHeading(LAST_SAMPLE_SCAN_LOCATION, LAST_SAMPLE_SCAN_LOCATION.getHeading())
                    .build();
        } else {
            resetToSampleScanTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                    .splineToLinearHeading(SAMPLE_SCAN_LOCATION.plus(new Pose2d(0.0, collectedSamples == 1 ? 8.0 : 0.0, 0)), SAMPLE_SCAN_LOCATION.getHeading())
                    .build();
        }
        drive.followTrajectorySequence(resetToSampleScanTrajectory);
    }

    @Override
    public void beforeStart() {
        // Pre start vs setup
        vs.completeSetup();
        vs.setPower(0.0);

        telemetry.addData("Trajectories", "(Starting) WAIT . . .");
        telemetry.update();

        // Creating first hang trajectory
        firstHangTrajectory = drive.trajectorySequenceBuilder(Positions.LEFT_START)
                .addTemporalMarker(0.75, () -> claw.crane())
                .addTemporalMarker(() -> {
                    vs.hang();
                    vs.closeClaw();
                    link.startPosition();
                })
                // Getting into position
                .setReversed(true)
                .splineToConstantHeading(Positions.FRONT_CENTER.vec().plus(new Vector2d(7.5, 12)), Math.toRadians(0)) // Move into position
                .setReversed(false)

                // Slamming down and hanging
                .addTemporalMarker(0.9, () -> vs.hingeForceHang()) // Slam into place
                .addTemporalMarker(() -> vs.openClaw()) // Let Go
                .splineTo(SAMPLE_SCAN_LOCATION.vec().plus(new Vector2d(0, -1.0)), SAMPLE_SCAN_LOCATION.getHeading())
                .addTemporalMarker(() -> { // Retract back to normal
                    vs.down();
                    vs.hingePickup();
                    claw.crane();
                })
                .build();

        telemetry.addData("Trajectories", "(Halfway) WAIT . . .");
        telemetry.update();

        // Pixel to bucket trajectory
        sampleToBucketTrajectory = drive.trajectorySequenceBuilder(SAMPLE_SCAN_LOCATION)
                .addTemporalMarker(() -> {
                    claw.deposit();
                    vs.openClaw();
                    vs.hingePickup();
                    vs.down();
                })

                .addTemporalMarker(0.7, () -> link.startPosition())
                .addTemporalMarker(1.25, () -> vs.closeClaw())
                .addTemporalMarker(1.35, () -> claw.fingerOpen())

                // Navigating to buckets
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading())

                // Bringing VS Up to deposit
                .addTemporalMarker(1.45, () -> vs.up())
                .waitSeconds(0.7)

                // Dropping sample in
                .addTemporalMarker(() -> vs.hingeBucket())
                .waitSeconds(0.4)
                .addTemporalMarker(() -> {
                    vs.openClaw();
                    claw.crane();
                })
                .waitSeconds(0.3)

                // Retracting back to normal
                .addTemporalMarker(() -> {
                    vs.down();
                    vs.hingePickup();
                })
                .build();

        // Creating parking trajectory
        parkingTrajectory = drive.trajectorySequenceBuilder(Positions.BUCKETS)
                // Getting everything set for moving
                .addTemporalMarker(() -> {
                    vs.down();
                    vs.hingePickup();
                })

                // Parking at 1st level ascent
                .splineTo(Positions.LEFT_NEAR.vec().plus(new Vector2d(15, 15)), Math.toRadians(90))
                .setReversed(true)
                .splineTo(Positions.LEFT_NEAR.vec(), Math.toRadians(-90))
                .setReversed(false)

                // Touching lower bar
//                .addTemporalMarker(() -> vs.hingeForceHang())
                .build();

        telemetry.addData("Trajectories", "COMPLETE");
        telemetry.update();

        // Setting start position
        drive.setPoseEstimate(Positions.LEFT_START);
    }

    @Override
    public void autoContents() {
        // Locking things into place
        vs.closeClaw();
        vs.setPower(1.0);
        claw.fingerOpen();
        link.startPosition();
        claw.wristCenter();

        // Starting timer
        time.reset();
        time.startTime();

        // Initial hanging
        drive.followTrajectorySequence(firstHangTrajectory);

        // Loop checks for the following:
        //      - Within allotted time (If scan is timed)
        //      - Not going over total allotted resets (If resets are counted)
        //      - Opmode hasn't been ended / Told to end
        States state = States.SEARCHING;
        while ((time.startTime() > SCAN_TIME || !TIMED_SCAN) && (!COUNT_RESETS_TILL_ABORT || totalResets <= RESETS_TILL_ABORT)
                && opModeIsActive() && !isStopRequested()) {

            // Informing about current state of scan
            telemetry.addData("Current State", state);
            telemetry.addData("Total Resets", totalResets);
            telemetry.addData("Has Sample", state == States.DEPOSITING);
            telemetry.addData("Collected Samples", collectedSamples);
            telemetry.addData("Heading", drive.getPoseEstimate().getHeading());
            telemetry.update();

            // Resetting Scan if no pixel found
            if (state == States.RESETTING) {
                resetScan();
                state = States.SEARCHING;
                totalResets++;
            }

            // Looking for pixels
            else if (state == States.SEARCHING) {
                // Scanning slowly to the left
                scan();
                while (opModeIsActive() && !isStopRequested()) {
                    // Updating localization (Needed, because we aren't using a trajectory. We are using dynamic movement from teleop. (Drive.setWeightedPower())
                    drive.update();

                    // Checking for depth change (Change in depth means claw went over a sample)
                    if (sensors.getDistance() <= 4.5) {
                        drive.setWeightedDrivePower(new Pose2d()); // Sets motor powers to 0 (Brakes)

                        // Waiting for bot to settle in place
                        waitSeconds(0.4);

                        // Grabbing Sample
                        claw.pickup();

                        waitSeconds(0.3);
                        claw.fingerClose();

                        // Waiting for pickup
                        waitSeconds(0.35);
                        claw.crane();
                        link.setCustomPosition(Link.IN + (Link.OUT * 0.3));

                        // Depositing into bucket
                        state = States.DEPOSITING;
                        break;
                    }
                    else if (!drive.isBusy()) {
                        state = States.RESETTING;
                        break;
                    }
                }
            }
            // Bringing held sample to the bucket
            else if (state == States.DEPOSITING) {
                // Running full deposit sequence
                depositSampleIntoBucket();

                collectedSamples++;

                // Stop searching if all 3 samples have been collected
                if (collectedSamples == 3) {
                    break;
                }

                if (collectedSamples == 2) {
                    claw.wristVertical();
                }

                // Setting up proper trajectory
                resetScan();

                state = States.SEARCHING;
            }
        }

        // Parking
        drive.followTrajectorySequence(parkingTrajectory);

        // Updating session storage
        Globals.teleopEntryPose = drive.getPoseEstimate();
    }
}
