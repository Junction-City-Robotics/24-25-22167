package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ViperSlide {
    // Motor Classes
    private final DcMotor v1;
    private final DcMotor v2;

    private DcMotorSimple.Direction v1Direction;
    private DcMotorSimple.Direction v2Direction;

    // Needed Positions
    public final static int TOP_POSITION = 6000;
    public final static int BOTTOM_POSITION = 0;

    public ViperSlide(String viper_1, String viper_2, HardwareMap map) {
        this.v1 = map.get(DcMotor.class, viper_1);
        this.v2 = map.get(DcMotor.class, viper_2);
    }

    public void ResetRunWithEncoders() {
        CustomPosition(0);

        v1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        v1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        v2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    public void StartRunToPosition() {
        v1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        v2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void Up() {
        v1.setTargetPosition(TOP_POSITION);
        v2.setTargetPosition(TOP_POSITION);
    }

    public void Down() {
        v1.setTargetPosition(BOTTOM_POSITION);
        v2.setTargetPosition(BOTTOM_POSITION);
    }

    public void CustomPosition(int position) {
        v1.setTargetPosition(position);
        v2.setTargetPosition(position);
    }

    public void SetPower(double power) {
        v1.setPower(power);
        v2.setPower(power);
    }

    public void Update() {
        if (v1.getCurrentPosition() < v1.getTargetPosition()) {
            SetPower(0.5);
        } else if (v1.getCurrentPosition() > v1.getTargetPosition()) {
            SetPower(-0.5);
        } else {
            SetPower(0.0);
        }
    }

    public void SetDirections(DcMotorSimple.Direction v1Direction, DcMotorSimple.Direction v2Direction) {
        v1.setDirection(v1Direction);
        v2.setDirection(v2Direction);
    }

    public DcMotor V1() {
        return v1;
    }

    public DcMotor V2() {
        return v2;
    }
}
