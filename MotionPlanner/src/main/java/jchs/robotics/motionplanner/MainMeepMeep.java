package jchs.robotics.motionplanner;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

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
                // Dropping off pre loaded pixel
                .setReversed(true)
                .splineToConstantHeading(Positions.FRONT_CENTER.vec().plus(new Vector2d(10, 0)), Math.toRadians(180))
                .setReversed(false)
                .waitSeconds(0.5) // TODO: Hang loaded pixel

                // Getting 1st pixel to the human player area
                .splineTo(Positions.PARKING.vec().plus(new Vector2d(10, 24)), Math.toRadians(90))
                .back(15)
                .waitSeconds(0.5)

                // Getting 2nd pixel to the human player area and picking up 1st specimen
                .splineToLinearHeading(Positions.PARKING.plus(new Pose2d(20, 24, 0)), Math.toRadians(90))
                .waitSeconds(0.5)

                // Getting 3rd pixel to the human player area and picking up 2nd specimen

                // Getting 3rd specimen hung

                // Parking

                // End
                .waitSeconds(0.75)
                .build());

        // Actually running it
        mm.addEntity(bot).start();
    }
}