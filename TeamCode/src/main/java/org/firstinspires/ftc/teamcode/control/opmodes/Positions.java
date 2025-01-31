package org.firstinspires.ftc.teamcode.control.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class Positions {
    // Start Beginning (The back wall
    public static final Pose2d ORIGIN = new Pose2d(0, 0, Math.toRadians(0));

    // Start Positions
    public static final Pose2d LEFT_START = new Pose2d(0, 24, Math.toRadians(0));
    public static final Pose2d RIGHT_START = new Pose2d(0, -24, Math.toRadians(0));

    // Constant Destinations
    public static final Pose2d PARKING = new Pose2d(6, -66, Math.toRadians(90));
    public static final Pose2d BUCKETS = new Pose2d(18, 60, Math.toRadians(-  45));

    // Pixel Locations
    public static final Pose2d RIGHT_COLORED_PIXELS = new Pose2d(44, -60, Math.toRadians(90));
    public static final Pose2d LEFT_GENERIC_PIXELS = new Pose2d(44, 60, Math.toRadians(90));
    public static final Pose2d RIGHT_GENERIC_PIXELS = new Pose2d(102, 60, Math.toRadians(90));

    // Center Feature Locations
    public static final Pose2d FRONT_CENTER = new Pose2d(44, 0, Math.toRadians(90));
    public static final Pose2d BACK_CENTER = new Pose2d(108, 0, Math.toRadians(180));

    public static final Pose2d LEFT_NEAR = new Pose2d(54, 24);
    public static final Pose2d LEFT_FAR = new Pose2d(66, 24);

    public static final Pose2d RIGHT_NEAR = new Pose2d(54, -24);
    public static final Pose2d RIGHT_FAR = new Pose2d(66, -24);
}
