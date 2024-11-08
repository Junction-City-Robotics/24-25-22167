package org.firstinspires.ftc.teamcode.systems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.Servo;

public class PassThrough {
    private final Servo brush;
    private final Servo bucket;
    private final Servo wrist;

    // CONSTANTS
    private final static double BRUSH_INTAKE = 1.0;
    private final static double BRUSH_OUTPUT = -1.0;
    private final static double BRUSH_STOP = 0.5;

    private final static double BUCKET_DOWN = 0.9;
    private final static double BUCKET_UP = 0.48;

    private final static double WRIST_PICKUP_POSITION = 0.187;
    private final static double WRIST_DEPOSIT_POSITION = 0.74;

    public PassThrough(String brush, String bucket, String wrist) {
        this.brush = hardwareMap.get(Servo.class, brush);
        this.bucket = hardwareMap.get(Servo.class, bucket);
        this.wrist = hardwareMap.get(Servo.class, wrist);
    }

    // Bucket Controlling Functions
    public void BucketUp() {
        bucket.setPosition(BUCKET_UP);
    }
    public void BucketDown() {
        bucket.setPosition(BUCKET_DOWN);
    }

    // Wrist / Arm Moving
    public void WristPickup() {
        wrist.setPosition(WRIST_PICKUP_POSITION);
    }
    public void WristDeposit() {
        wrist.setPosition(WRIST_DEPOSIT_POSITION);
    }

    // Brush Controls
    public void BrushIntake() {
        brush.setPosition(BRUSH_INTAKE);
    }
    public void BrushOutput() {
        brush.setPosition(BRUSH_OUTPUT);
    }
    public void BrushStop() {
        brush.setPosition(BRUSH_STOP);
    }
}
