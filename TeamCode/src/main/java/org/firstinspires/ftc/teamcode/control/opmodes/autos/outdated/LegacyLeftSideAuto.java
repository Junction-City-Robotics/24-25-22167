package org.firstinspires.ftc.teamcode.control.opmodes.autos.outdated;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.control.opmodes.autos.BaseAuto;
import org.firstinspires.ftc.teamcode.miscellaneous.Globals;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.control.Positions;

@Autonomous(name = "Left Side Auto (Legacy)", group = "autos")
@Disabled
public class LegacyLeftSideAuto extends BaseAuto {
    private TrajectorySequence mainTrajectory;

    @Override
    public void beforeStart() {
        // Pre start vs setup
        vs.completeSetup();
        vs.setPower(0.0);

        telemetry.addData("Building Trajectory", "WAIT . . .");
        telemetry.update();

        updatePositions();

        // Creating Trajectory
        drive.setPoseEstimate(Positions.LEFT_START);
        mainTrajectory = drive.trajectorySequenceBuilder(Positions.LEFT_START)
                // TODO: Hang preloaded specimen
                .addTemporalMarker(0.001, () -> { // Initial Setup
                    vs.hang();

                    link.startPosition();
                })
                .setReversed(true)
                .splineToConstantHeading(Positions.FRONT_CENTER.vec().plus(new Vector2d(4.5, 8)), Math.toRadians(180)) // Move into position
                .setReversed(false)
                .addTemporalMarker(() -> {
                    claw.setCustomWristPosition(0.3);
                    claw.setCustomElbowPosition(0.85);
                    claw.setCustomArmPosition(0.25);
                })
                .addTemporalMarker(() -> vs.hingeForceHang()) // Slam into place
                .waitSeconds(0.9)
                .addTemporalMarker(() -> vs.openClaw()) // Let Go
                .waitSeconds(0.2)
                .addTemporalMarker(() -> { // Retract back to normal
                    vs.down();
                    vs.hingePickup();
                })

                // Pixel 1
                .splineTo(new Pose2d(-32, 47).vec(), Math.toRadians(0))// TODO: Pick up pixel one
                .addTemporalMarker(() -> { // Gets claw into position
                    claw.fingerOpen();
                    claw.wristCenter();
                    claw.elbowPickup();
                    claw.armPickup();
                })
                .waitSeconds(0.75)
                .addTemporalMarker(() -> claw.fingerClose()) // Grabs it
                .waitSeconds(0.5)
                .addTemporalMarker(() -> { // Brings pixel to viperslide claw
                    claw.wristDeposit();
                    claw.elbowDeposit();
                    claw.armDeposit();
                })
                .waitSeconds(0.75)
                .addTemporalMarker(() -> vs.closeClaw())
                .waitSeconds(0.4)
                .addTemporalMarker(() -> claw.fingerOpen())
                .waitSeconds(0.3)
                .addTemporalMarker(() -> { // Cranes the claw
                    claw.setCustomElbowPosition(0.85);
                    claw.setCustomArmPosition(0.3);
                    // claw.wristVertical(); // NOTE: Changed because we approach it from the side
                })
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading()) // TODO: Deposit pixel one
                .addTemporalMarker(() -> vs.up())
                .waitSeconds(2.4)
                .addTemporalMarker(() -> vs.hingeBucket())
                .waitSeconds(0.6)
                .addTemporalMarker(() -> vs.openClaw())
                .waitSeconds(0.9)
                .addTemporalMarker(() -> {
                    vs.down();
                    vs.hingePickup();
                })

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

                // Pixel 3
                .splineToLinearHeading(new Pose2d(-25.5, 62, Math.toRadians(90)), Math.toRadians(90)) // TODO: Gra pixel 3
                .addTemporalMarker(() -> { // Gets into position
                    vs.hingePickup();
                    claw.fingerOpen();
                    claw.elbowPickup();
                    claw.armPickup();
                })
                .waitSeconds(0.75)
                .addTemporalMarker(() -> claw.fingerClose()) // Grabs it
                .waitSeconds(0.9)
                .back(7)
                .addTemporalMarker(() -> { // Brings pixel to viperslide claw
                    claw.wristDeposit();
                    claw.elbowDeposit();
                    claw.armDeposit();
                })
                .waitSeconds(0.75)
                .addTemporalMarker(() -> vs.closeClaw())
                .waitSeconds(0.9)
                .addTemporalMarker(() -> claw.fingerOpen())
                .waitSeconds(0.5)
                .addTemporalMarker(() -> { // Cranes the claw
                    claw.setCustomWristPosition(0.3);
                    claw.setCustomElbowPosition(0.85);
                    claw.setCustomArmPosition(0.25);
                    updatePositions();
                })
                .setReversed(true)
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading() * -1)
                .setReversed(false) // TODO: Put down pixel three
                .addTemporalMarker(() -> vs.up())
                .waitSeconds(2.75)
                .addTemporalMarker(() -> vs.hingeBucket())
                .waitSeconds(0.6)
                .addTemporalMarker(() -> vs.openClaw())
//                .waitSeconds(0.9)
//                .addTemporalMarker(() -> {
//                    vs.down();
//                    vs.hingePickup();
//                    updatePositions();
//                })
//
//                // Parking at 1st level ascent
//                .splineToLinearHeading(Positions.BUCKETS.plus(new Pose2d(20, 4, Math.toRadians(90 + 45))), Math.toRadians(90))
//                .setReversed(true)
//                .splineTo(Positions.LEFT_NEAR.vec(), Math.toRadians(-90))
//                .addTemporalMarker(this::updatePositions)
//                .setReversed(false)
//                // TODO: Touch lower bar
//                .addTemporalMarker(() -> vs.hingeForceHang())

                // End
                .build();

        telemetry.addData("Building Trajectory", "READY - RUN IT");
        telemetry.update();
    }

    @Override
    public void autoContents() {
        vs.closeClaw();
        vs.setPower(1.0);
        claw.fingerOpen();

        drive.followTrajectorySequence(mainTrajectory);

        // Updating session storage
        Globals.teleopEntryPose = drive.getPoseEstimate();
    }

    public void updatePositions() {
        Globals.teleopEntryPose = drive.getPoseEstimate();
    }
}
