package org.firstinspires.ftc.teamcode.control.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.Positions;
import org.firstinspires.ftc.teamcode.miscellaneous.Globals;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous(name = "Left Side Auto", group = "autos")
public class LeftSideAuto extends BaseAuto {
    private static final Pose2d SAMPLE_SCAN_LOCATION = new Pose2d(-32, 47, Math.toRadians(0));

    private TrajectorySequence firstHangTrajectory;
    private TrajectorySequence bucketToSampleScanTrajectory;
    private TrajectorySequence resetToSampleScanTrajectory;
    private TrajectorySequence parkingTrajectory;
    private TrajectorySequence sampleToBucketTrajectory;

    private enum STATES {
        RESETTING,
        SEARCHING,
        DEPOSITING
    }
    private boolean lastSampleBeingCollects = false;

    private int collectedSamples = 0;

    private void telemetryBuildStatus(String status,
                                      boolean initialHangIsDown,
                                      boolean sampleToBucket,
                                      boolean bucketToSampleScan,
                                      boolean parking) {
        telemetry.addData("Building Trajectories", status);
        telemetry.addData("\tInitial Hang", initialHangIsDown ? "COMPLETE" : "INCOMPLETE");
        telemetry.addData("\tSample to Bucket", sampleToBucket ? "COMPLETE" : "INCOMPLETE");
        telemetry.addData("\tBucket to Sample Scan", bucketToSampleScan ? "COMPLETE" : "INCOMPLETE");
        telemetry.addData("\tParking", parking ? "COMPLETE" : "INCOMPLETE");
        telemetry.update();
    }

    private void depositSampleIntoBucket() {
        telemetry.addData("Sample to Bucket Trajectory", "BUILDING");
        telemetry.update();
        sampleToBucketTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                // Navigating to buckets
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading())

                // Bringing VS Up to deposit
                .addTemporalMarker(() -> vs.up())
                .waitSeconds(1.0)

                // Dropping sample in
                .addTemporalMarker(() -> vs.hingeBucket())
                .waitSeconds(0.6)
                .addTemporalMarker(() -> vs.openClaw())
                .waitSeconds(0.8)

                // Retracting back to normal
                .addTemporalMarker(() -> {
                    vs.down();
                    vs.hingePickup();
                })
                .build();

        drive.followTrajectorySequence(sampleToBucketTrajectory);
    }

    private void resetScan() {
        telemetry.addData("Reset To Scan Trajectory", "BUILDING");
        telemetry.update();
        resetToSampleScanTrajectory = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .splineTo(SAMPLE_SCAN_LOCATION.vec(), SAMPLE_SCAN_LOCATION.getHeading())
                .build();

        drive.followTrajectorySequence(resetToSampleScanTrajectory);
    }

    @Override
    public void beforeStart() {
        // Pre start vs setup
        vs.completeSetup();
        vs.setPower(0.0);

        telemetryBuildStatus("WAIT . . .", false, false, false, false);

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
                .splineToConstantHeading(Positions.FRONT_CENTER.vec().plus(new Vector2d(4.5, 8)), Math.toRadians(180)) // Move into position
                .setReversed(false)

                // Slamming down and hanging
                .addTemporalMarker(1.4, () -> vs.hingeForceHang()) // Slam into place
                .waitSeconds(0.9)
                .addTemporalMarker(() -> vs.openClaw()) // Let Go
                .waitSeconds(0.2)
                .addTemporalMarker(() -> { // Retract back to normal
                    vs.down();
                    vs.hingePickup();
                })
                .build();
        telemetryBuildStatus("WAIT . . .", true, false,false, false);

        // Bucket to sample scanner
        bucketToSampleScanTrajectory = drive.trajectorySequenceBuilder(Positions.BUCKETS)
                .splineTo(SAMPLE_SCAN_LOCATION.vec(), SAMPLE_SCAN_LOCATION.getHeading())
                .build();
        telemetryBuildStatus("WAIT . . .", true, true,false, false);

        // Pixel to bucket trajectory
        sampleToBucketTrajectory = drive.trajectorySequenceBuilder(Positions.LEFT_START)
                // Navigating to buckets
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading())

                // Bringing VS Up to deposit
                .addTemporalMarker(() -> vs.up())
                .waitSeconds(2.4)

                // Dropping sample in
                .addTemporalMarker(() -> vs.hingeBucket())
                .waitSeconds(0.6)
                .addTemporalMarker(() -> vs.openClaw())
                .waitSeconds(0.9)

                // Retracting back to normal
                .addTemporalMarker(() -> {
                    vs.down();
                    vs.hingePickup();

                    collectedSamples++;
                })
                .build();
        telemetryBuildStatus("WAIT . . .", true, true,true, false);

        // Creating parking trajectory
        parkingTrajectory = drive.trajectorySequenceBuilder(Positions.LEFT_START)
                // Getting everything set for moving
                .addTemporalMarker(() -> {
                    vs.down();
                    vs.hingePickup();
                })

                // Parking at 1st level ascent
                .splineToLinearHeading(Positions.BUCKETS.plus(new Pose2d(20, 4, Math.toRadians(90 + 45))), Math.toRadians(90))
                .setReversed(true)
                .splineTo(Positions.LEFT_NEAR.vec(), Math.toRadians(-90))
                .setReversed(false)

                // Touching lower bar
                .addTemporalMarker(() -> vs.hingeForceHang())
                .build();
        telemetryBuildStatus("WAIT . . .", true, true,true, true);

        // Creating Trajectory
//        drive.setPoseEstimate(Positions.LEFT_START);
//        mainTrajectory = drive.trajectorySequenceBuilder(Positions.LEFT_START)
//                // Pixel 1
//                .splineTo(new Pose2d(-32, 47).vec(), Math.toRadians(0))// TODO: Pick up pixel one
//                .addTemporalMarker(() -> { // Gets claw into position
//                    claw.open();
//                    claw.wristDeposit();
//                    claw.elbowDown();
//                    claw.armPickup();
//                })
//                .waitSeconds(0.75)
//                .addTemporalMarker(() -> claw.close()) // Grabs it
//                .waitSeconds(0.5)
//                .addTemporalMarker(() -> { // Brings pixel to viperslide claw
//                    claw.wristDeposit();
//                    claw.elbowDeposit();
//                    claw.armDeposit();
//                })
//                .waitSeconds(0.75)
//                .addTemporalMarker(() -> vs.closeClaw())
//                .waitSeconds(0.4)
//                .addTemporalMarker(() -> claw.open())
//                .waitSeconds(0.3)
//                .addTemporalMarker(() -> { // Cranes the claw
//                    claw.setCustomElbowPosition(0.85);
//                    claw.setCustomArmPosition(0.3);
//                    claw.wristVertical(); // NOTE: Changed because we approach it from the side
//                })
//                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading()) // TODO: Deposit pixel one
//                .addTemporalMarker(() -> vs.up())
//                .waitSeconds(2.4)
//                .addTemporalMarker(() -> vs.hingeBucket())
//                .waitSeconds(0.6)
//                .addTemporalMarker(() -> vs.openClaw())
//                .waitSeconds(0.9)
//                .addTemporalMarker(() -> {
//                    vs.down();
//                    vs.hingePickup();
//                })
// TODO: X
//                // Pixel 2
//                .splineToLinearHeading(new Pose2d(-33, 58.5, Math.toRadians(0)), Math.toRadians(0)) // TODO: Collect Pixel two
//                .addTemporalMarker(() -> { // Gets into position
//                    vs.hingePickup();
//                    claw.open();
//                    claw.wristDeposit();
//                    claw.elbowDown();
//                    claw.armPickup();
//                })
//                .waitSeconds(0.25)
//                .addTemporalMarker(() -> claw.close()) // Grabs it
//                .waitSeconds(0.45)
//                .addTemporalMarker(() -> { // Brings pixel to viperslide claw
//                    claw.wristDeposit();
//                    claw.elbowDeposit();
//                    claw.armDeposit();
//                })
//                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading()) // TODO: Deposit pixel two
//                .addTemporalMarker(() -> vs.closeClaw())
//                .waitSeconds(0.4)
//                .addTemporalMarker(() -> claw.open())
//                .waitSeconds(0.3)
//                .addTemporalMarker(() -> { // Cranes the claw
//                    claw.setCustomWristPosition(0.3);
//                    claw.setCustomElbowPosition(0.85);
//                    claw.setCustomArmPosition(0.25);
//                    vs.up();
//                })
//                .waitSeconds(2.75)
//                .addTemporalMarker(() -> vs.hingeBucket())
//                .waitSeconds(0.6) l
//                .addTemporalMarker(() -> vs.openClaw())
//                .waitSeconds(0.9)
//                .addTemporalMarker(() -> {
//                    vs.down();
//                    vs.hingePickup();
//                })
// TODO: X
//                // Pixel 3
//                .splineToLinearHeading(new Pose2d(-25.5, 62, Math.toRadians(90)), Math.toRadians(90)) // TODO: Gra pixel 3
//                .addTemporalMarker(() -> { // Gets into position
//                    vs.hingePickup();
//                    claw.open();
//                    claw.elbowDown();
//                    claw.armPickup();
//                })
//                .waitSeconds(0.75)
//                .addTemporalMarker(() -> claw.close()) // Grabs it
//                .waitSeconds(0.9)
//                .back(7)
//                .addTemporalMarker(() -> { // Brings pixel to viperslide claw
//                    claw.wristDeposit();
//                    claw.elbowDeposit();
//                    claw.armDeposit();
//                })
//                .waitSeconds(0.75)
//                .addTemporalMarker(() -> vs.closeClaw())
//                .waitSeconds(0.9)
//                .addTemporalMarker(() -> claw.open())
//                .waitSeconds(0.5)
//                .addTemporalMarker(() -> { // Cranes the claw
//                    claw.setCustomWristPosition(0.3);
//                    claw.setCustomElbowPosition(0.85);
//                    claw.setCustomArmPosition(0.25);
//                    updatePositions();
//                })
//                .setReversed(true)
//                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading() * -1)
//                .setReversed(false) // TODO: Put down pixel three
//                .addTemporalMarker(() -> vs.up())
//                .waitSeconds(2.75)
//                .addTemporalMarker(() -> vs.hingeBucket())
//                .waitSeconds(0.6)
//                .addTemporalMarker(() -> vs.openClaw())
//
//                // End
//                .build();

        telemetryBuildStatus("BUILT - READY TO RUN", true, true,true, true);
    }

    @Override
    public void autoContents() {
        vs.closeClaw();
        vs.setPower(1.0);
        claw.open();

        // Initial hanging
        drive.followTrajectorySequence(firstHangTrajectory);

        // Collecting and hanging pixels
        STATES state = STATES.SEARCHING;
        while (time.startTime() < 20) {
            if (state.equals(STATES.RESETTING)) {
                resetScan();
                state = STATES.SEARCHING;
            } else if (state.equals(STATES.SEARCHING)) {
                claw.crane(0.05);
                drive.setWeightedDrivePower(
                        new Pose2d(
                                0,
                                0.2,
                                0
                        )
                );

                if (sensors.getDistance() <= 4.5) {
                    drive.setWeightedDrivePower(
                            new Pose2d(
                                    0,
                                    0,
                                    0
                            )
                    );

                    claw.close();

                    state = STATES.DEPOSITING;
                }

            } else if (state.equals(STATES.DEPOSITING)) {
                // Depositing last one into bucket
                depositSampleIntoBucket();

                if (collectedSamples == 2) {
                    lastSampleBeingCollects = true;
                } else if (collectedSamples == 3) {
                    break;
                }

                drive.followTrajectorySequence(bucketToSampleScanTrajectory);
                state = STATES.SEARCHING;
            }
        }

        // Parking
        drive.followTrajectorySequence(parkingTrajectory);

        // Updating session storage
        Globals.teleopEntryPose = drive.getPoseEstimate();
    }
}
