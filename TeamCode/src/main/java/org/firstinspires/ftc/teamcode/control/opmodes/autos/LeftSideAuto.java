package org.firstinspires.ftc.teamcode.control.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.miscellaneous.SessionStorage;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.control.Positions;

@Autonomous(name = "Left Side Auto", group = "autos")
public class LeftSideAuto extends BaseAuto {
    private static TrajectorySequence mainTrajectory;

    @Override
    public void beforeStart() {
        // Pre start vs setup
        vs.completeSetup();
        vs.setPower(0.0);

        // Creating Trajectory
        drive.setPoseEstimate(Positions.LEFT_START);
        mainTrajectory = drive.trajectorySequenceBuilder(Positions.LEFT_START)
                // Drop off first pixel
                // Initial Moving to bucket to drop off the first pixel
                .setReversed(true)
                .splineToConstantHeading(Positions.FRONT_CENTER.vec().plus(new Vector2d(0, 14)), Math.toRadians(180))
                .setReversed(false)
                .waitSeconds(1.0) // TODO: Hang loaded pixel

                // Pixel 1
                .splineTo(new Pose2d(-38, 48).vec(), Math.toRadians(0))// TODO: Pick up pixel three
                .waitSeconds(0.5)
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading()) // TODO: Deposit pixel three

                // Pixel 2
                .splineToLinearHeading(new Pose2d(-38, 58, Math.toRadians(0)), Math.toRadians(0))
                .waitSeconds(0.5)
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading()) // TODO: Deposit pixel two

                // Pixel 3
                .splineToLinearHeading(new Pose2d(-28, 62, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(0.5)
                .setReversed(true)
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading() * -1) // TODO: Deposit pixel two
                .setReversed(false)

                // Parking at 1st level ascent
                .splineToLinearHeading(Positions.BUCKETS.plus(new Pose2d(20, 4, Math.toRadians(90 + 45))), Math.toRadians(90))
                .setReversed(true)
                .splineTo(Positions.LEFT_NEAR.vec(), Math.toRadians(-90))
                .setReversed(false)
                // TODO: Touch lower bar

                // End
                .waitSeconds(0.75)
                .build();
    }

    @Override
    public void autoContents() {
        claw.close();

        vs.setPower(1.0);

        drive.followTrajectorySequence(mainTrajectory);

        SessionStorage.teleopEntryPose = drive.getPoseEstimate();
        SessionStorage.viperslideStartOffset = vs.getPosition();
    }
}
