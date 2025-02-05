package org.firstinspires.ftc.teamcode.control.systems.inputOutput;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ViperSlide {
    // Motor Classes
    private final DcMotor v1;
    private final DcMotor v2;

    private final Servo claw;
    private final Servo hinge;

    private int offset = 0;

    // Needed Positions
    public final static int TOP_POSITION = 5500;
    public final static int HALF_POSITION = 3000;
    public final static int BOTTOM_POSITION = 10;

    // Claw Positions
    public final static double CLAW_OPEN = 0.15;
    public final static double CLAW_CLOSE = 0.0;

    // Hinge Positions
    private final static double HINGE_PICKUP = 0.09;
    private final static double HINGE_BAR = 0.5;
    private final static double HINGE_BUCKET = 0.9;

    // Directions
    private final static DcMotorSimple.Direction[] FORWARD_DIRECTIONS = {
        DcMotorSimple.Direction.FORWARD,
        DcMotorSimple.Direction.REVERSE
    };

    public ViperSlide(String viper_1, String viper_2, String claw, String hinge, HardwareMap hMap) {
        this.v1 = hMap.get(DcMotor.class, viper_1);
        this.v2 = hMap.get(DcMotor.class, viper_2);

        this.claw = hMap.get(Servo.class, claw);
        this.hinge = hMap.get(Servo.class, hinge);

        completeSetup();
    }

    public ViperSlide(String viper_1, String viper_2, String claw, String hinge, HardwareMap hMap, boolean setup) {
        this.v1 = hMap.get(DcMotor.class, viper_1);
        this.v2 = hMap.get(DcMotor.class, viper_2);

        this.claw = hMap.get(Servo.class, claw);
        this.hinge = hMap.get(Servo.class, hinge);

        if (setup) {
            completeSetup();
        }
    }

    /*
     * Overall method for running VS
     */
    public void completeSetup() {
        offset = 0;

        // Stops power
        setPower(0.0);

        // Sets up basic forward rotations for the servos
        forwardDirection();

        // Sets start position so run to position doesn't freak out
        customPosition(0);

        // Sets brake mode so it doesn't move
        zeroPowerBrakeMode();

        // Why not
        runWithEncoders();

        // Resets encoders
        stopAndResetEncoders();

        // Runs encoders
        runWithEncoders();

        // Sets up run to position
        runToPosition();
    }

    public void completeSetup(int offset) {
        this.offset = offset;

        // Stops power
        setPower(0.0);

        // Sets up basic forward rotations for the servos
        forwardDirection();

        // Sets start position so run to position doesn't freak out
        customPosition(0);

        // Sets brake mode so it doesn't move
        zeroPowerBrakeMode();

        // Why not
        runWithEncoders();

        // Resets encoders
        stopAndResetEncoders();

        // Runs encoders
        runWithEncoders();

        // Sets up run to position
        runToPosition();
    }

    public void afterStart(double power) {
        setPower(power);
        down();
    }

    /*
    * Individual components for running the VS motors with run to position
    */
    public void runWithEncoders() {
        v1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        v2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stopAndResetEncoders() {
        v1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        v2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void zeroPowerBrakeMode() {
        v1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        v2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void runToPosition() {
        v1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        v2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /*
     * Setting Motor directions
     */
    public void forwardDirection() {
        v1.setDirection(FORWARD_DIRECTIONS[0]);
        v2.setDirection(FORWARD_DIRECTIONS[1]);
    }

    /*
     * VS positions to go to
     */
    public void up() {
        v1.setTargetPosition(TOP_POSITION - offset);
        v2.setTargetPosition(TOP_POSITION - offset);
    }

    public void down() {
        v1.setTargetPosition(BOTTOM_POSITION - offset);
        v2.setTargetPosition(BOTTOM_POSITION - offset);
    }

    public void halfway() {
        v1.setTargetPosition(HALF_POSITION - offset);
        v2.setTargetPosition(HALF_POSITION - offset);
    }

    public void customPosition(int position) {
        v1.setTargetPosition(position - offset);
        v2.setTargetPosition(position - offset);
    }

    /*
     * Extra misc function
     */
    public void setPower(double power) {
        v1.setPower(power);
        v2.setPower(power);
    }

    public void setOffset(int newOffset) {
        offset = newOffset;
    }

    public int getPosition() {
        return (v1.getCurrentPosition() + v2.getCurrentPosition()) / 2;
    }

    public boolean atDestination() {
        int targetMean = (v1.getTargetPosition() + v2.getTargetPosition()) / 2;
        int positionMean = getPosition();

        return positionMean > targetMean - 10 && positionMean < targetMean - 10;
    }

    /*
     * Getters for the motor classes
     */
    public DcMotor viperOne() {
        return v1;
    }

    public DcMotor viperTwo() {
        return v2;
    }

    /*
    * Claw Functions
    * */
    public void customClawPosition(double position) {
        claw.setPosition(position);
    }

    public void openClaw() {
        claw.setPosition(CLAW_OPEN);
    }

    public void closeClaw() {
        claw.setPosition(CLAW_CLOSE);
    }

    // Hinge Positions
    public void hingePickup() {
        hinge.setPosition(HINGE_PICKUP);
    }

    public void hingeBar() {
        hinge.setPosition(HINGE_BAR);
    }

    public void hingeBucket() {
        hinge.setPosition(HINGE_BUCKET);
    }
}
