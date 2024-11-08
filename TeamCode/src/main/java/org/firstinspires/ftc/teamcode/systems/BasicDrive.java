package org.firstinspires.ftc.teamcode.systems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;

public class BasicDrive {
    private final DcMotor fl;
    private final DcMotor bl;

    private final DcMotor fr;
    private final DcMotor br;

    public BasicDrive(String fl, String bl, String fr, String br) {
        this.fl = hardwareMap.get(DcMotor.class, fl);
        this.bl = hardwareMap.get(DcMotor.class, bl);;
        this.fr = hardwareMap.get(DcMotor.class, fr);;
        this.br = hardwareMap.get(DcMotor.class, br);;
    }

    public DcMotor FL() {
        return fl;
    }

    public DcMotor FR() {
        return fr;
    }

    public DcMotor BL() {
        return bl;
    }

    public DcMotor BR() {
        return br;
    }

    public void RunWithEncoders() {
        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void RunWithoutEncoders() {
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void DirectionalDrive(double angle, double speed) {

    }

    public void Stop() {
        fl.setPower(0.0);
        bl.setPower(0.0);
        fr.setPower(0.0);
        br.setPower(0.0);
    }

    public void Forward(double speed) {
        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

        fl.setPower(speed);
        bl.setPower(speed);
        fr.setPower(speed);
        br.setPower(speed);
    }

    public void Backward(double speed) {
        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);
        fl.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.FORWARD);

        fl.setPower(speed);
        bl.setPower(speed);
        fr.setPower(speed);
        br.setPower(speed);
    }

    public void StrafeLeft(double speed) {
        fl.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);

        bl.setPower(speed);
        br.setPower(speed);
        fl.setPower(speed);
        fr.setPower(speed);
    }

    public void StrafeRight(double speed) {
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.FORWARD);

        bl.setPower(speed);
        br.setPower(speed);
        fl.setPower(speed);
        fr.setPower(speed);
    }

    public void TurnLeft(double speed) {
        fl.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);

        bl.setPower(speed);
        br.setPower(speed);
        fl.setPower(speed);
        fr.setPower(speed);
    }

    public void ArcRight(double speed) {
        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

        fl.setPower(Math.min(1.0, speed * 3.0));
        bl.setPower(Math.min(1.0, speed * 3.0));
        fr.setPower(speed);
        br.setPower(speed);
    }

    public void ArcLeft(double speed) {
        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

        fl.setPower(speed);
        bl.setPower(speed);
        fr.setPower(Math.min(1.0, speed * 3.0));
        br.setPower(Math.min(1.0, speed * 3.0));
    }

    public void TurnRight(double speed) {
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);

        bl.setPower(speed);
        br.setPower(speed);
        fl.setPower(speed);
        fr.setPower(speed);
    }
}
