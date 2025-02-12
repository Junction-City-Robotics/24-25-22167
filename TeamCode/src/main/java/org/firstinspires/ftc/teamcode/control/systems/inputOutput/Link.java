package org.firstinspires.ftc.teamcode.control.systems.inputOutput;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Link {
    /*
     * Left and right link servos
     */
    private final Servo l1; // Left
    private final Servo l2; // Right

    /*
     * Static set positions for servos
     */
    public static final double START_POSITION = 0.1;
    public static final double FULLY_EXTENDED_POSITION = 0.365;

    public Link(String l1Name, String l2Name, HardwareMap hMap) {
        l1 = hMap.get(Servo.class, l1Name);
        l2 = hMap.get(Servo.class, l2Name);

        l1.setDirection(Servo.Direction.REVERSE);
    }

    public void startPosition() {
        l1.setPosition(START_POSITION);
        l2.setPosition(START_POSITION);
    }

    public void extendedPosition() {
        l1.setPosition(FULLY_EXTENDED_POSITION);
        l2.setPosition(FULLY_EXTENDED_POSITION);
    }

    public void setCustomPosition(double position) {
        l1.setPosition(position);
        l2.setPosition(position);
    }
}
