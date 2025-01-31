//package org.firstinspires.ftc.teamcode.control.opmodes.autos.outdated;
//
//import com.acmerobotics.roadrunner.geometry.Vector2d;
//import com.acmerobotics.roadrunner.trajectory.Trajectory;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import org.firstinspires.ftc.teamcode.control.opmodes.Positions;
//import org.firstinspires.ftc.teamcode.control.opmodes.autos.BaseAuto;
//import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
//
//@Autonomous(name = "T2A Autonomous (Left)", group = "autos")
//public class T2AAutonomous extends BaseAuto {
//    private TrajectorySequence completePlan;
//
//    @Override
//    public void autoContents() {
//        // First Movements (Grab pixel, get wrist out of the way)
//        claw.clawClose();
//        brush.wristToHover();
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
//
//        // Creating Trajectories
//        Trajectory goToPark = drive.trajectoryBuilder(Positions.BUCKETS)
//                .addTemporalMarker(0.001, () -> {
//                    vs.down();
//                    claw.hingeDown();
//                    brush.wristToDeposit();
//                    brush.brushStop();
//                })
//                .splineTo(new Vector2d(24, 24), Math.toRadians(-90))
//                .splineTo(Positions.PARKING.vec(), Math.toRadians(0))
//                .addTemporalMarker(3.0, () -> {
//                    claw.hingeDeposit();
//                    vs.customPosition(0);
//                })
//                .build();
//
//        completePlan = drive.trajectorySequenceBuilder(Positions.LEFT_START)
//                .addTemporalMarker(0.001, () -> {
//                    brush.wristToHover();
//                    link.startPosition();
//                })
//                .addTemporalMarker(0.2, () -> vs.halfway())
//                .splineTo(Positions.BUCKETS.vec(), Positions.BUCKETS.getHeading())
//                .addTemporalMarker(1.0, () -> vs.up())
//                .addTemporalMarker(2.3, () -> claw.hingeDeposit())
//                .addTemporalMarker(3.3, () -> claw.clawOpen())
//                .forward(3.0)
//                .addTemporalMarker(3.8, () -> {
//                    claw.clawClose();
//                    vs.down();
//                    claw.hingeDown();
//                    brush.wristToFloor();
//                })
//                .waitSeconds(3.3)
//                .addTrajectory(goToPark)
//                .addTemporalMarker(4.0, () -> claw.clawOpen())
//                .build();
//    }
//}
