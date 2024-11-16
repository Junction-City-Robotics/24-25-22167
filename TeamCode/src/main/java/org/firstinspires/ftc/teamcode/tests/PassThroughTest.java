package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.systems.PassThrough;
import org.firstinspires.ftc.teamcode.utilities.OutputUpdater;

@TeleOp(name = "Pass Through System Tests", group = "tests")
public class PassThroughTest extends LinearOpMode {
    private final OutputUpdater outputUpdater = new OutputUpdater(telemetry);

    @Override
    public void runOpMode() {
        boolean has_clicked = false;
        int wrist_position = PassThrough.WRIST_DEPOSIT_POSITION;

        PassThrough passThrough = new PassThrough("brush", "bucket", "wrist");
        passThrough.WristStart();
        passThrough.WristPower(0.2);

        waitForStart();
        while (opModeIsActive()) {
            // Brush Rotation
            if (gamepad1.right_trigger > 0) {
                passThrough.BrushIntake();
            } else if (gamepad1.left_trigger > 0) {
                passThrough.BrushOutput();
            } else {
                passThrough.BrushStop();
            }

            // Wrist Movements
            if (gamepad1.left_stick_y > 0.4) {
                wrist_position = PassThrough.WRIST_DEPOSIT_POSITION;
                passThrough.WristCustomPosition(wrist_position);
            } else if (gamepad1.left_stick_y < -0.4) {
                wrist_position = PassThrough.WRIST_PICKUP_POSITION;
                passThrough.WristCustomPosition(wrist_position);
            } else if (gamepad1.left_stick_button) {
                wrist_position = PassThrough.WRIST_STALL_POSITION;
                passThrough.WristCustomPosition(wrist_position);
            }

            // Position Incrementing
            if (gamepad1.dpad_left) {
                if (!has_clicked) {
                    has_clicked = true;
                    wrist_position -= 5;
                }
            } else if (gamepad1.dpad_right) {
                if (!has_clicked) {
                    has_clicked = true;
                    wrist_position += 5;
                }
            } else if (gamepad1.dpad_up) {
                if (!has_clicked) {
                    has_clicked = true;
                    wrist_position -= 20;
                }
            } else if (gamepad1.dpad_down) {
                if (!has_clicked) {
                    wrist_position -= 20;
                    has_clicked = true;
                }
            } else {
                has_clicked = false;
            }

            // Bucket Movement
            if (gamepad1.right_bumper) {
                passThrough.BucketDown();
            } else if (gamepad1.left_bumper) {
                passThrough.BucketUp();
            }

            // Show the output for drivers
            telemetry.addData("Wrist Position", wrist_position);
            telemetry.addData("Is Clicking", has_clicked);
            telemetry.addData("Instructions", "This is the Pass Through System Test." +
                    "This is to test the intake brush, bucket, and wrist. The brush and bucket" +
                    "You can only change the brush & bucket in the code, and test here. You can" +
                    " change the wrist position. You can change the value using the Dpad. Left and " +
                    "Down subtract, right and up add.");
            outputUpdater.Update();
        }
    }
}
