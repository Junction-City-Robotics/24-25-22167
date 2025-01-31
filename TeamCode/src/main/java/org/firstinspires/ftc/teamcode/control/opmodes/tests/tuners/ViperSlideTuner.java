package org.firstinspires.ftc.teamcode.control.opmodes.tests.tuners;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.systems.inputOutput.ViperSlide;

@TeleOp(name = "Viper Slide Tuner", group = "tuners")
public class ViperSlideTuner extends LinearOpMode {
    private int position = 0;

    private boolean isClicked = false;

    @Override
    public void runOpMode() {
        ViperSlide vs = new ViperSlide("viper_1", "viper_2", hardwareMap, false);

        vs.forwardDirection();
        vs.setPower(0.0);
        vs.down();
        vs.customPosition(0);
        vs.stopAndResetEncoders();
        vs.runWithEncoders();
        vs.zeroPowerBrakeMode();
        vs.runToPosition();

        waitForStart();

        vs.setPower(0.1);
        vs.down();

        while (opModeIsActive()) {
            if (vs.atDestination()) {
                vs.setPower(0.0);
            }

            if (gamepad1.left_stick_y > 0.3) {
                vs.down();
            } else if (gamepad1.left_stick_y < -0.3) {
                vs.up();
            }

            // Input Handling
            if (gamepad1.dpad_up) {
                if (!isClicked) {
                    position += 500;
                    isClicked = true;
                    vs.setPower(0.1);
                    vs.runToPosition();
                    vs.customPosition(position);
                }
            } else if (gamepad1.y) {
                if (!isClicked) {
                    position += 1000;
                    isClicked = true;
                    vs.setPower(0.1);
                    vs.runToPosition();
                    vs.customPosition(position);
                }
            } else if (gamepad1.dpad_down) {
                if (!isClicked) {
                    position -= 500;
                    isClicked = true;
                    vs.customPosition(position);
                }
            } else if (gamepad1.a) {
                if (!isClicked) {
                    position -= 1000;
                    isClicked = true;
                    vs.customPosition(position);
                }
            } else if (gamepad1.left_stick_button) {
                if (!isClicked) {
                    isClicked = true;

                    vs.setPower(0.0);
                }
            } else {
                isClicked = false;
            }

            // Output
            telemetry.addData("Target Position", vs.viperOne().getTargetPosition());
            telemetry.addData("Current Position", vs.viperOne().getCurrentPosition());
            telemetry.addData("Power", vs.viperTwo().getPower());
            telemetry.addData("Target", vs.atDestination() + "");
            telemetry.addData("Position", position + "");
            telemetry.update();
        }
    }
}
