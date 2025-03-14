package org.firstinspires.ftc.teamcode.control.systems;

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

    public static double RIGHT_SERVO_OFFSET = 0.0;

    /*
     * Static set positions for servos (Config Variables)
     */
    public static double IN = 0.0;
    public static double OUT = 0.23;

    public Link(String l1Name, String l2Name, HardwareMap hMap) {
        l1 = hMap.get(Servo.class, l1Name);
        l2 = hMap.get(Servo.class, l2Name);

        l1.setDirection(Servo.Direction.REVERSE);
    }

    public void startPosition() {
        l1.setPosition(IN);
        l2.setPosition(IN + RIGHT_SERVO_OFFSET);
    }

    public void extendedPosition() {
        l1.setPosition(OUT);
        l2.setPosition(OUT + RIGHT_SERVO_OFFSET);
    }

    public void setCustomPosition(double position) {
        l1.setPosition(position);
        l2.setPosition(position + RIGHT_SERVO_OFFSET);
    }
}
