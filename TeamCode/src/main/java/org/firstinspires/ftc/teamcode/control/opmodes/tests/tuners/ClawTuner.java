package org.firstinspires.ftc.teamcode.control.opmodes.tests.tuners;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.systems.Claw;

@TeleOp(name = "Claw Tuner", group = "tuners")
public class ClawTuner extends LinearOpMode {
    private double fingerPosition = 1.0;
    private double wristPosition = 0.35;
    private double elbowPosition = 0.65;
    private double armPosition = 0.35;

    private boolean isClicked = false;

    @Override
    public void runOpMode() {
        // Link init
        Claw claw = new Claw("finger", "wrist", "elbow", "arm", hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            // Input Handling
            if (gamepad1.dpad_up) {
                if (!isClicked) {
                    fingerPosition += 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.dpad_down) {
                if (!isClicked) {
                    fingerPosition -= 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.y) {
                if (!isClicked) {
                    wristPosition += 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.a) {
                if (!isClicked) {
                    wristPosition -= 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.left_bumper) {
                if (!isClicked) {
                    elbowPosition += 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.right_bumper) {
                if (!isClicked) {
                    elbowPosition -= 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.left_stick_button) {
                if (!isClicked) {
                    armPosition += 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.right_stick_button) {
                if (!isClicked) {
                    armPosition -= 0.05;
                    isClicked = true;
                }
            } else {
                isClicked = false;
            }

            // Updating position
            claw.setCustomFingerPosition(fingerPosition);
            claw.setCustomWristPosition(wristPosition);
            claw.setCustomElbowPosition(elbowPosition);
            claw.setCustomArmPosition(armPosition);

            // Telemetry
            telemetry.addData("Finger", fingerPosition);
            telemetry.addData("Wrist", wristPosition);
            telemetry.addData("Elbow", elbowPosition);
            telemetry.addData("Arm", armPosition);
            telemetry.update();
        }
    }
}
