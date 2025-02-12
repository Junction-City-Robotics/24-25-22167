//package org.firstinspires.ftc.teamcode.control.systems.outdated.drive;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//public class EncoderMeasuredDrive {
//    public final static double TICKS_PER_MM = 1.275;
//    public final static double TICKS_PER_FEET = 388.7;
//
//    private final DcMotor fl;
//    private final DcMotor bl;
//    private final DcMotor fr;
//    private final DcMotor br;
//
//    private int step = 0;
//
//    public EncoderMeasuredDrive(String fl, String bl, String fr, String br, HardwareMap hardwareMap) {
//        this.fl = hardwareMap.get(DcMotor.class, fl);
//        this.bl = hardwareMap.get(DcMotor.class, bl);;
//        this.fr = hardwareMap.get(DcMotor.class, fr);;
//        this.br = hardwareMap.get(DcMotor.class, br);;
//    }
//
//    public void StartDrive() {
//        fr.setDirection(DcMotor.Direction.FORWARD);
//        br.setDirection(DcMotor.Direction.FORWARD);
//        fl.setDirection(DcMotor.Direction.REVERSE);
//        bl.setDirection(DcMotor.Direction.REVERSE);
//
//        SetAllPositions(0);
//        SetAllPower(0.0);
//
//        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        SetAllPower(0.0);
//    }
//
//    public boolean AtTarget() {
//        if (fr.getCurrentPosition() != fr.getTargetPosition()) {
//            return false;
//        } else if (br.getCurrentPosition() != br.getTargetPosition()) {
//            return false;
//        } else if (bl.getCurrentPosition() != bl.getTargetPosition()) {
//            return false;
//        } else return fl.getCurrentPosition() == fl.getTargetPosition();
//    }
//
//    public void SetAllPower(double power) {
//        fl.setPower(power);
//        bl.setPower(power);
//        fr.setPower(power);
//        br.setPower(power);
//    }
//
//    public void Straight(double feet) {
//        int convertedAmount = (int) (feet * TICKS_PER_FEET);
//
//        fl.setTargetPosition(fl.getTargetPosition() + convertedAmount);
//        bl.setTargetPosition(bl.getTargetPosition() + convertedAmount);
//        br.setTargetPosition(br.getTargetPosition() + convertedAmount);
//        fr.setTargetPosition(fr.getTargetPosition() + convertedAmount);
//    }
//    public void Strafe(double feet) {
//        int convertedAmount = (int) (feet * TICKS_PER_FEET);
//
//        fl.setTargetPosition(fl.getTargetPosition() + convertedAmount);
//        bl.setTargetPosition(bl.getTargetPosition() - convertedAmount);
//        br.setTargetPosition(br.getTargetPosition() + convertedAmount);
//        fr.setTargetPosition(fr.getTargetPosition() - convertedAmount);
//    }
//
//    public boolean RoughAtTarget() {
//        if (fr.getCurrentPosition() > fr.getTargetPosition() - 10 &&
//        fr.getCurrentPosition() < fr.getCurrentPosition() + 10) {
//            return bl.getCurrentPosition() > bl.getTargetPosition() - 10 &&
//                    bl.getCurrentPosition() < bl.getTargetPosition() + 10;
//        }
//        return false;
//    }
//
//    public void SetAllPositions(int position) {
//        fl.setTargetPosition(position);
//        fr.setTargetPosition(position);
//        br.setTargetPosition(position);
//        bl.setTargetPosition(position);
//    }
//
//    public void CountStep() {
//        step += 1;
//    }
//    public void ReverseStep() {
//        step -= 1;
//    }
//    public void ResetStep() {
//        step = 0;
//    }
//    public boolean StepIs(int checkedStep) {
//        return step == checkedStep;
//    }
//    public int Step() {
//        return step;
//    }
//
//    public DcMotor FL() {
//        return fl;
//    }
//
//    public DcMotor FR() {
//        return fr;
//    }
//
//    public DcMotor BL() {
//        return bl;
//    }
//
//    public DcMotor BR() {
//        return br;
//    }
//}
