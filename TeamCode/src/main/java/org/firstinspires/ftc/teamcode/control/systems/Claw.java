package org.firstinspires.ftc.teamcode.control.systems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw {
    private final Servo finger;
    private final Servo wrist;
    private final Servo arm;
    private final Servo elbow;

    /**
     * Config Variables
     */
    // Claw Positions
    public static double CLOSE = 1.0;
    public static double OPEN = 0.38;

    // Wrist Positions
    public static double WRIST_DEPOSIT = 0.35;
    public static double WRIST_VERTICAL = 0.65;
    public static double WRIST_HORIZONTAL = 0.35;

    // Elbow Positions
    public static double ELBOW_DEPOSIT = 0.175;
    public static double ELBOW_UP = 0.35;
    public static double ELBOW_DOWN = 0.8;

    // Arm Positions
    public static double ARM_DEPOSIT = 0.425;
    public static double ARM_PICKUP = 0.15;

    public Claw(String finger, String wrist, String elbow, String arm, HardwareMap hMap) {
        this.finger = hMap.get(Servo.class, finger);
        this.wrist = hMap.get(Servo.class, wrist);
        this.elbow = hMap.get(Servo.class, elbow);
        this.arm = hMap.get(Servo.class, arm);
    }

    // Claw
    public void close() {
        finger.setPosition(CLOSE);
    }

    public void open() {
        finger.setPosition(OPEN);
    }

    // Wrist
    public void wristDeposit() {
        wrist.setPosition(WRIST_DEPOSIT);
    }

    public void wristHorizontal() {
        wrist.setPosition(WRIST_HORIZONTAL);
    }

    public void wristVertical() {
        wrist.setPosition(WRIST_VERTICAL);
    }

    public void wristRotatedPercent(double percent) {
        double position = WRIST_DEPOSIT + ((WRIST_VERTICAL - WRIST_HORIZONTAL) * percent);
        wrist.setPosition(position);
    }

    // Elbow
    public void elbowDeposit() {
        elbow.setPosition(ELBOW_DEPOSIT);
    }

    public void elbowUp() {
        elbow.setPosition(ELBOW_UP);
    }

    public void elbowDown() {
        elbow.setPosition(ELBOW_DOWN);
    }

    public void elbowPositionPercent(double percent) {
        double position = ((ELBOW_DOWN - ELBOW_UP) * percent) + ELBOW_UP;
        elbow.setPosition(position);
    }

    // Arm
    public void armDeposit() {
        arm.setPosition(ARM_DEPOSIT);
    }

    public void armPickup() {
        arm.setPosition(ARM_PICKUP);
    }

    public void armPositionPercent(double percent) {
        double position = ((ARM_DEPOSIT - ARM_PICKUP) * percent) + ARM_PICKUP;
        arm.setPosition(position);
    }

    // Custom Position Functions
    public void setCustomFingerPosition(double position) {
        finger.setPosition(position);
    }

    public void setCustomWristPosition(double position) {
        wrist.setPosition(position);
    }

    public void setCustomArmPosition(double position) {
        arm.setPosition(position);
    }

    public void setCustomElbowPosition(double position) {
        elbow.setPosition(position);
    }

    public void crane() {
        setCustomWristPosition(0.3);
        setCustomElbowPosition(0.85);
        setCustomArmPosition(0.25);
    }

    public void crane(double armOffset) {
        setCustomWristPosition(0.3);
        setCustomElbowPosition(0.85);
        setCustomArmPosition(0.25 - armOffset);
    }
}
