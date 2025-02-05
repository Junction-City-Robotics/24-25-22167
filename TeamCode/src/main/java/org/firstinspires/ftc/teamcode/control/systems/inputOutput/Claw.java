package org.firstinspires.ftc.teamcode.control.systems.inputOutput;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final Servo finger;
    private final Servo wrist;
    private final Servo arm;
    private final Servo elbow;

    // Claw Positions
    private static final double CLOSE = 1.0; // TODO: Get actual close position
    private static final double OPEN = 0.38; // TODO: Get actual open position

    // Wrist Positions
    private static final double WRIST_DEPOSIT = 1.0;
    private static final double WRIST_VERTICAL = 0.45;
    private static final double WRIST_HORIZONTAL = 0.68;

    // Elbow Positions
    private static final double ELBOW_DEPOSIT = 0.45;
    private static final double ELBOW_UP = 0.35;
    private static final double ELBOW_DOWN = 1.0;

    // Arm Positions
    private static final double ARM_DEPOSIT = 0.35;
    private static final double ARM_PICKUP = 0.18;

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

    // Arm
    public void armDeposit() {
        arm.setPosition(ARM_DEPOSIT);
    }

    public void armPickup() {
        arm.setPosition(ARM_PICKUP);
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
