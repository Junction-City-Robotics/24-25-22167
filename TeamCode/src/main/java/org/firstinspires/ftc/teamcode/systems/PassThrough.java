package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class PassThrough {
    private final Servo brush;
    private final Servo bucket;
    private final DcMotor wrist;

    // CONSTANTS
    // Brush
    private final static double BRUSH_INTAKE = -1.0;
    private final static double BRUSH_OUTPUT = 1.0;
    private final static double BRUSH_STOP = 0.5;

    // Bucket
    private final static double BUCKET_DOWN = 0.96;
    private final static double BUCKET_UP = 0.48;

    // Wrist
    public final static int WRIST_PICKUP_POSITION = 1090;
    public final static int WRIST_STALL_POSITION = 770;
    public final static int WRIST_DEPOSIT_POSITION = 0;

    public PassThrough(String brush, String bucket, String wrist, HardwareMap hardwareMap) {
        this.brush = hardwareMap.get(Servo.class, brush);
        this.bucket = hardwareMap.get(Servo.class, bucket);
        this.wrist = hardwareMap.get(DcMotor.class, wrist);
    }

    // Bucket Controlling Functions
    public void BucketUp() {
        bucket.setPosition(BUCKET_UP);
    }
    public void BucketDown() {
        bucket.setPosition(BUCKET_DOWN);
    }

    // Wrist / Arm Moving
    public void WristStart() {
        wrist.setPower(0.0);

        wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        wrist.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void WristPickup() {
        wrist.setTargetPosition(WRIST_PICKUP_POSITION);
    }
    public void WristStallPosition() {
        wrist.setTargetPosition(WRIST_STALL_POSITION);
    }
    public void WristDeposit() {
        wrist.setTargetPosition(WRIST_DEPOSIT_POSITION);
    }
    public void WristCustomPosition(int position) {
        wrist.setTargetPosition(position);
    }
    public void WristPower(double power) {
        wrist.setPower(power);
    }
    public DcMotor Wrist() { return wrist; }

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
