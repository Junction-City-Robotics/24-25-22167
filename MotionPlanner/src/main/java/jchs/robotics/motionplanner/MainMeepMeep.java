package jchs.robotics.motionplanner;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import org.rowlandhall.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

public class MainMeepMeep {
    // Overall needed components
    private static final MeepMeep mm;

    private static final DefaultBotBuilder botBuilder;

    // Robot Data
    private static final double WIDTH = 16.0;
    private static final double HEIGHT = 15.5;

    private static final double TRACK_WIDTH = 12.7;

    private final static double VELOCITY = 46.81651638;
    private final static double ACCELERATION = 46.81651638;

    static {
        mm = new MeepMeep(600)
                .setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_BLACK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .setShowFPS(true)
                .setTheme(new ColorSchemeRedDark());

        botBuilder = new DefaultBotBuilder(mm)
                .setConstraints(VELOCITY, ACCELERATION,
                        Math.toRadians(180), Math.toRadians(180),  TRACK_WIDTH)
                .setDimensions(WIDTH, HEIGHT);
    }

    public static void main(String[] args) {
        /*
          May this code be graced...
                Blessed down to the soul...
                    By the wholly...
                        The lovely...
                            The majestic...

                                    JACOB
         */
        Pose2d startPose = Positions.RIGHT_START;

        RoadRunnerBotEntity bot = botBuilder.followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(startPose)
                // Drop off first pixel
                // Initial Moving to bucket to drop off the first pixel
                .setReversed(true)
                .splineToConstantHeading(Positions.FRONT_CENTER.vec().plus(new Vector2d(0, -10)), Math.toRadians(180))
                .setReversed(false)
                .waitSeconds(2.5)

                // Pixel 1
                .splineTo(new Pose2d(-33, 48).vec(), Math.toRadians(0))// TODO: Pick up pixel one

                .waitSeconds(0.75)
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading()) // TODO: Deposit pixel one

                // Pixel 2
                .splineToLinearHeading(new Pose2d(-38, 58, Math.toRadians(0)), Math.toRadians(0))
                .waitSeconds(0.5)
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading()) // TODO: Deposit pixel two

                // Pixel 3
                .splineToLinearHeading(new Pose2d(-28, 62, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(0.5)
                .setReversed(true)
                .splineToLinearHeading(Positions.BUCKETS, Positions.BUCKETS.getHeading() * -1)
                .setReversed(false) // TODO: Put down pixel three

                // Parking at 1st level ascent
                .splineToLinearHeading(Positions.BUCKETS.plus(new Pose2d(20, 4, Math.toRadians(180 + 45))), Math.toRadians(90))
                .setReversed(true)
                .splineTo(Positions.LEFT_NEAR.vec(), Math.toRadians(-90))
                .setReversed(false)
                // TODO: Touch lower bar

                // End
                .waitSeconds(0.75)
                .build());

        // Actually running it
        mm.addEntity(bot).start();
    }
}