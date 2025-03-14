package org.firstinspires.ftc.teamcode.control.systems;

import static java.lang.Thread.sleep;

import androidx.annotation.ColorRes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Config
public class ViperSlide {
    // Motor Classes
    private final DcMotor v1;
    private final DcMotor v2;

    private final Servo claw;
    private final Servo hinge;

    private final TouchSensor touchSensor;
    private final ScheduledExecutorService touchSensorScheduler = Executors.newScheduledThreadPool(1);

    private int offset = 0;
    private boolean resetting = false;

    // Sees if the hinge claw is open
    private boolean isOpen = false;

    /**
     * Config Variables
     */
    // Viperslide positions
    public static int TOP_POSITION = 2300;
    public static int BOUNCED_HANG_POSITION = 200;
    public static int HANG_POSITION = 150;
    public static int BOTTOM_POSITION = -1;

    // Claw Positions
    public static double CLAW_OPEN = 0.15;
    public static double CLAW_CLOSE = 0.0;

    // Hinge Positions
    public static double HINGE_PICKUP = 0.95;
    public static double HINGE_BUCKET = 0.14;
    public static double HINGE_FORCE_HANG = 0.0;

    // Directions
    private final static DcMotorSimple.Direction[] FORWARD_DIRECTIONS = {
        DcMotorSimple.Direction.FORWARD,
        DcMotorSimple.Direction.REVERSE
    };

    public ViperSlide(String viper_1, String viper_2, String claw, String hinge, String touchSensor,
                      HardwareMap hMap) {
        this.v1 = hMap.get(DcMotor.class, viper_1);
        this.v2 = hMap.get(DcMotor.class, viper_2);

        this.claw = hMap.get(Servo.class, claw);
        this.hinge = hMap.get(Servo.class, hinge);

        this.touchSensor = hMap.get(TouchSensor.class, touchSensor);

        completeSetup();
    }

    public ViperSlide(String viper_1, String viper_2, String claw, String hinge, String touchSensor,
                      HardwareMap hMap, boolean setup) {
        this.v1 = hMap.get(DcMotor.class, viper_1);
        this.v2 = hMap.get(DcMotor.class, viper_2);

        this.claw = hMap.get(Servo.class, claw);
        this.hinge = hMap.get(Servo.class, hinge);

        this.touchSensor = hMap.get(TouchSensor.class, touchSensor);

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

    /**
     * Resetting Viper Slide
     */
    public void resetOffset() {
        resetting = true;
    }

    public void updateOffsetReset() {
        if (resetting) {
            if (isTouchingBottom()) {
                resetting = false;
                offset = -1 * getRawPosition();
                down();
            } else {
                customPosition(getRawPosition() - 500);
            }
        }
    }

    public boolean isResettingOffset() {
        return resetting;
    }

    public boolean isTouchingBottom() {
        return touchSensor.isPressed();
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

    }

    public void hang() {
        v1.setTargetPosition(HANG_POSITION - offset);
        v2.setTargetPosition(HANG_POSITION - offset);
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
// TODO: DO THE BOUNCED AWAY POSITION
    public void setOffset(int newOffset) {
        offset = newOffset;
    }

    public int getRawPosition() {
        return (v1.getCurrentPosition() + v2.getCurrentPosition()) / 2;
    }

    public int getPosition() {
        return (v1.getCurrentPosition() - offset + v2.getCurrentPosition() - offset) / 2;
    }

    public boolean atDestination() {
        int targetMean = (v1.getTargetPosition() + v2.getTargetPosition()) / 2;
        int positionMean = getPosition();

        return positionMean > targetMean - 10 && positionMean < targetMean - 10;
    }

    public int getOffset() {
        return offset;
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
        isOpen = true;
        claw.setPosition(CLAW_OPEN);
    }

    public void closeClaw() {
        isOpen = false;
        claw.setPosition(CLAW_CLOSE);
    }

    public boolean clawIsOpen() {
        return isOpen;
    }

    // Hinge Positions
    public void hingePickup() {
        hinge.setPosition(HINGE_PICKUP);
    }

    public void hingeForceHang() {
        hinge.setPosition(HINGE_FORCE_HANG);
    }

    public void hingeBucket() {
        hinge.setPosition(HINGE_BUCKET);
    }

    public void customHingePosition (double position) {
        hinge.setPosition(position);
    }
}
