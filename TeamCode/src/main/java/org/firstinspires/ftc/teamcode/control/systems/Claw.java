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
    public static double FINGER_CLOSE = 0.4;
    public static double FINGER_OPEN = 0.9;

    private boolean closed = true;

    // Wrist Positions
    public static double WRIST_CENTER = 0.52;
    public static double WRIST_VERTICAL = 0.85;
    public static double WRIST_MAX = 0.8;
    public static double WRIST_MIN = 0.2;

    // Elbow Positions
    public static double ELBOW_DEPOSIT = 0.2;
    public static double ELBOW_PICKUP = 0.7;
    public static double ELBOW_CRANE = 0.75;
    public static double ELBOW_VERTICAL_STALL = 0.45;
    public static double ELBOW_BAR_STALL = 0.9;

    // Arm Positions
    public static double ARM_DEPOSIT = 0.45;
    public static double ARM_PICKUP = 0.05;
    public static double ARM_CRANE = 0.15;
    public static double ARM_VERTICAL_STALL = 0.5;
    public static double ARM_BAR_STALL = 0.3;


    public Claw(String finger, String wrist, String elbow, String arm, HardwareMap hMap) {
        this.finger = hMap.get(Servo.class, finger);
        this.wrist = hMap.get(Servo.class, wrist);
        this.elbow = hMap.get(Servo.class, elbow);
        this.arm = hMap.get(Servo.class, arm);
    }

    // Claw
    public void fingerClose() {
        finger.setPosition(FINGER_CLOSE);
        closed = true;
    }

    public void fingerOpen() {
        finger.setPosition(FINGER_OPEN);
        closed = false;
    }

    public boolean isClosed() {
        return closed;
    }

    // Wrist
    public void wristDeposit() {
        wrist.setPosition(WRIST_CENTER);
    }

    public void wristCenter() {
        wrist.setPosition(WRIST_CENTER);
    }
    public void wristRightAngle() {wrist.setPosition(WRIST_MAX);}
    public void wristVertical() {wrist.setPosition(WRIST_VERTICAL);}


    // Elbow
    public void elbowDeposit() {
        elbow.setPosition(ELBOW_DEPOSIT);
    }

    public void elbowPickup() {
        elbow.setPosition(ELBOW_PICKUP);
    }

    public void elbowVerticalStall() {
        elbow.setPosition(ELBOW_VERTICAL_STALL);
    }
    public void elbowBarStall() {
        elbow.setPosition(ELBOW_BAR_STALL);
    }

    // Arm
    public void armDeposit() {
        arm.setPosition(ARM_DEPOSIT);
    }
    public void armPickup() {
        arm.setPosition(ARM_PICKUP);
    }
    public void armCrane() {
        arm.setPosition(ARM_CRANE);
    }
    public void armVerticalStall() {
        arm.setPosition(ARM_VERTICAL_STALL);
    }
    public void armBarStall() {
        arm.setPosition(ARM_BAR_STALL);
    }

    // Presets
    public void crane() {
        setCustomElbowPosition(ELBOW_CRANE);
        setCustomArmPosition(ARM_CRANE);
    }

    public void crane(double armOffset) {
        setCustomElbowPosition(ELBOW_CRANE);
        setCustomArmPosition(ARM_CRANE - armOffset);
    }

    public void pickup() {
        fingerOpen();
        elbowPickup();
        armPickup();
    }

    public void deposit() {
        wristDeposit();
        elbowDeposit();
        armDeposit();
        fingerClose();
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
