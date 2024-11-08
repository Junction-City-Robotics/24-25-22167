package org.firstinspires.ftc.teamcode.systems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;

public class ViperSlide {

    private final DcMotor v1;
    private final DcMotor v2;

    public ViperSlide(String v1, String v2) {
        this.v1 = hardwareMap.get(DcMotor.class, v1);
        this.v2 = hardwareMap.get(DcMotor.class, v2);
    }

    public void RunWithEncoders() {
        v1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        v2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void RunWithoutEncoders() {
        v1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        v2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void ZeroPowerBreakMode() {
        v1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        v2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void Up(double speed) {
        v2.setDirection(DcMotor.Direction.REVERSE);
        v1.setDirection(DcMotor.Direction.FORWARD);

        v1.setPower(speed);
        v2.setPower(speed);
    }

    public void Down(double speed) {
        v2.setDirection(DcMotor.Direction.FORWARD);
        v1.setDirection(DcMotor.Direction.REVERSE);

        v1.setPower(speed);
        v2.setPower(speed);
    }

    public void Stop() {
        Up(0.0);
    }

    public DcMotor V1() {
        return v1;
    }

    public DcMotor V2() {
        return v2;
    }
}
