package org.firstinspires.ftc.teamcode.miscellaneous;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.control.opmodes.Positions;

public class SessionStorage {
    // Position from auto to teleop to maintain global drive over relative
    public static Pose2d teleopEntryPose = Positions.ORIGIN;

    // Viper Slide position so it doesn't overextend when going from auto to relative
    public static int viperslideStartOffset = 0;
}
