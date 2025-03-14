package org.firstinspires.ftc.teamcode.control;

import com.acmerobotics.roadrunner.geometry.Pose2d;

/*
* WARNING
* When moving the positions from the motion planner to the control,
* SWITCH THE X AND Y
* AND INVERT THE NEW Y
* ROTATE THE HEADING BY -90
* */

/*
* Moving guide:
* ADDING to the Y makes the bot go FORWARD
* SUBTRACTING to the Y makes the bot go BACKWARDS
* ADDING TO THE X makes the bot go LEFT
* SUBTRACTING to the X makes the bot go RIGHT
* */

public class Positions {
    // Start Beginning (The back wall
    public static final Pose2d ORIGIN = new Pose2d(0, 0, Math.toRadians(0));

    // Start Positions
    public static final Pose2d LEFT_START = new Pose2d(-61.5, 15.5, Math.toRadians(180));
    public static final Pose2d RIGHT_START = new Pose2d(-61.5, -15.5, Math.toRadians(180));

    // Constant Destinations
    public static final Pose2d PARKING = new Pose2d(-60, -38, Math.toRadians(0));
    public static final Pose2d BUCKETS = new Pose2d(-48.5, 54, Math.toRadians(-45));

    // Center Feature Locations
    public static final Pose2d FRONT_CENTER = new Pose2d(-36, -2, Math.toRadians(90));
    public static final Pose2d BACK_CENTER = new Pose2d(0, -108, Math.toRadians(90)); // TODO: Deal with warning and translate to correct Pose

    public static final Pose2d LEFT_NEAR = new Pose2d(-9, 20, Math.toRadians(90));
    public static final Pose2d LEFT_FAR = new Pose2d(9, 25, Math.toRadians(90));

    public static final Pose2d RIGHT_NEAR = new Pose2d(-9, -25, Math.toRadians(-90));
    public static final Pose2d RIGHT_FAR = new Pose2d(9, -25, Math.toRadians(-90));
}
