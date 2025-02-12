//package org.firstinspires.ftc.teamcode.control.systems.outdated.drive;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//public class BasicDrive {
//    private final DcMotor fl;
//    private final DcMotor bl;
//
//    private final DcMotor fr;
//    private final DcMotor br;
//
//    public BasicDrive(String fl, String bl, String fr, String br, HardwareMap hardwareMap) {
//        this.fl = hardwareMap.get(DcMotor.class, fl);
//        this.bl = hardwareMap.get(DcMotor.class, bl);
//        this.fr = hardwareMap.get(DcMotor.class, fr);
//        this.br = hardwareMap.get(DcMotor.class, br);
//    }
//
//    public DcMotor fl() {
//        return fl;
//    }
//
//    public DcMotor fr() {
//        return fr;
//    }
//
//    public DcMotor bl() {
//        return bl;
//    }
//
//    public DcMotor br() {
//        return br;
//    }
//
//    public void runWithEncoders() {
//        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//    }
//
//    public void directionalDrive(double speed, double x, double y) {
//
//    }
//
//    public void stop() {
//        fl.setPower(0.0);
//        bl.setPower(0.0);
//        fr.setPower(0.0);
//        br.setPower(0.0);
//    }
//
//    public void forward(double speed) {
//        fr.setDirection(DcMotor.Direction.FORWARD);
//        br.setDirection(DcMotor.Direction.FORWARD);
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        bl.setDirection(DcMotor.Direction.REVERSE);
//
//        fl.setPower(speed);
//        bl.setPower(speed);
//        fr.setPower(speed);
//        br.setPower(speed);
//    }
//
//    public void backward(double speed) {
//        fr.setDirection(DcMotor.Direction.REVERSE);
//        br.setDirection(DcMotor.Direction.REVERSE);
//        fl.setDirection(DcMotor.Direction.FORWARD);
//        bl.setDirection(DcMotor.Direction.FORWARD);
//
//        fl.setPower(speed);
//        bl.setPower(speed);
//        fr.setPower(speed);
//        br.setPower(speed);
//    }
//
//    public void strafeLeft(double speed) {
//        fl.setDirection(DcMotor.Direction.FORWARD);
//        bl.setDirection(DcMotor.Direction.REVERSE);
//        fr.setDirection(DcMotor.Direction.FORWARD);
//        br.setDirection(DcMotor.Direction.REVERSE);
//
//        bl.setPower(speed);
//        br.setPower(speed);
//        fl.setPower(speed);
//        fr.setPower(speed);
//    }
//
//    public void strafeRight(double speed) {
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        bl.setDirection(DcMotor.Direction.FORWARD);
//        fr.setDirection(DcMotor.Direction.REVERSE);
//        br.setDirection(DcMotor.Direction.FORWARD);
//
//        bl.setPower(speed);
//        br.setPower(speed);
//        fl.setPower(speed);
//        fr.setPower(speed);
//    }
//
//    public void arcRight(double speed) {
//        fr.setDirection(DcMotor.Direction.FORWARD);
//        br.setDirection(DcMotor.Direction.FORWARD);
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        bl.setDirection(DcMotor.Direction.REVERSE);
//
//        fl.setPower(speed);
//        bl.setPower(speed);
//        fr.setPower(speed * .5);
//        br.setPower(speed * .5);
//    }
//
//    public void arcLeft(double speed) {
//        fr.setDirection(DcMotor.Direction.FORWARD);
//        br.setDirection(DcMotor.Direction.FORWARD);
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        bl.setDirection(DcMotor.Direction.REVERSE);
//
//        fl.setPower(speed * .5);
//        bl.setPower(speed * .5);
//        fr.setPower(speed);
//        br.setPower(speed);
//    }
//
//    public void turnRight(double speed) {
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        bl.setDirection(DcMotor.Direction.REVERSE);
//        fr.setDirection(DcMotor.Direction.REVERSE);
//        br.setDirection(DcMotor.Direction.REVERSE);
//
//        bl.setPower(speed);
//        br.setPower(speed);
//        fl.setPower(speed);
//        fr.setPower(speed);
//    }
//
//    public void turnLeft(double speed) {
//        fl.setDirection(DcMotor.Direction.FORWARD);
//        bl.setDirection(DcMotor.Direction.FORWARD);
//        fr.setDirection(DcMotor.Direction.FORWARD);
//        br.setDirection(DcMotor.Direction.FORWARD);
//
//        bl.setPower(speed);
//        br.setPower(speed);
//        fl.setPower(speed);
//        fr.setPower(speed);
//    }
//
//    public void zeroPowerBrakeMode() {
//        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//    }
//}
