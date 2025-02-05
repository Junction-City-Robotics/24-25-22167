package org.firstinspires.ftc.teamcode.control.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.miscellaneous.SessionStorage;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.control.Positions;

@Autonomous(name = "Right Side Auto", group = "autos")
public class RightSideAuto extends BaseAuto {
    private static TrajectorySequence mainTrajectory;

    @Override
    public void beforeStart() {
        // Pre start vs setup
        vs.completeSetup();
        vs.setPower(0.0);

        // Creating Trajectory
        drive.setPoseEstimate(Positions.LEFT_START);
        mainTrajectory = drive.trajectorySequenceBuilder(Positions.LEFT_START)

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
