//package org.firstinspires.ftc.teamcode.control.opmodes.autos;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.acmerobotics.roadrunner.geometry.Vector2d;
//import com.acmerobotics.roadrunner.trajectory.Trajectory;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import org.firstinspires.ftc.teamcode.control.opmodes.Positions;
//import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
//
//@Autonomous(name = "Sample to Bucket", group = "autos")
//public class SampleToBucket extends BaseAuto {
//    private TrajectorySequence completePlan;
//
//    @Override
//    public void autoContents() {
//        // First Movements (Grab pixel, get wrist out of the way)
//        vs.closeClaw();
//        claw.setCustomWristPosition(0.3);
//        claw.setCustomElbowPosition(0.85);
//        claw.setCustomArmPosition(0.35);
//        claw.open();
//        vs.setPower(1.0);
//
//        // Go to buckets and let vs up halfway
//        drive.followTrajectorySequence(completePlan);
//    }
//
//    @Override
//    public void beforeStart() {
//        // Completing the init of the systems
//        vs.completeSetup();
//        vs.setPower(0.0);
//
//        drive.setPoseEstimate(Positions.LEFT_START);
//        completePlan = drive.trajectorySequenceBuilder(Positions.LEFT_START)
//                .addTemporalMarker(0.001, () -> {
//                    // Hovering
//                    claw.setCustomWristPosition(0.3);
//                    claw.setCustomElbowPosition(0.85);
//                    claw.setCustomArmPosition(0.35);
//
//                    link.startPosition();
//                })
//                .addTemporalMarker(0.2, () -> vs.halfway())
//                .splineTo(Positions.BUCKETS.vec(), Positions.BUCKETS.getHeading())
//                .addTemporalMarker(1.0, () -> vs.up())
//                .addTemporalMarker(2.3, () -> vs.hingeBucket())
//                .addTemporalMarker(3.5, () -> vs.openClaw())
//                .back(11.0)
//                .addTemporalMarker(3.8, () -> {
//                    vs.closeClaw();
//                    vs.down();
//                    vs.hingePickup();
//
//                    claw.setCustomWristPosition(0.3);
//                    claw.setCustomElbowPosition(0.85);
//                    claw.setCustomArmPosition(0.35);
//                })
//                .waitSeconds(3.3)
//
//                // Pixel 1
//                .splineTo(new Pose2d(-38, 48).vec(), Math.toRadians(0))// TODO: Pick up pixel one
//                .waitSeconds(0.5)
//                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading()) // TODO: Deposit pixel one
//
//                .build();
//    }
//}
