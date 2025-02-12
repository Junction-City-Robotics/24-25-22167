package org.firstinspires.ftc.teamcode.control.systems.inputOutput;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final Servo finger;
    private final Servo wrist;
    private final Servo arm;
    private final Servo elbow;

    // Claw Positions
    private static final double CLOSE = 1.0;
    private static final double OPEN = 0.38;

    // Wrist Positions
    private static final double WRIST_DEPOSIT = 0.35;
    private static final double WRIST_VERTICAL = 0.65;
    private static final double WRIST_HORIZONTAL = 0.35;

    // Elbow Positions
    private static final double ELBOW_DEPOSIT = 0.175;
    private static final double ELBOW_UP = 0.35;
    private static final double ELBOW_DOWN = 0.8;

    // Arm Positions
    private static final double ARM_DEPOSIT = 0.425;
    private static final double ARM_PICKUP = 0.15;

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
}
