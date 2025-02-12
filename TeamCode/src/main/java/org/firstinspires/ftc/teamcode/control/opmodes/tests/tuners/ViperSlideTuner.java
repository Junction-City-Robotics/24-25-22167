package org.firstinspires.ftc.teamcode.control.opmodes.tests.tuners;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.systems.ViperSlide;

@TeleOp(name = "Viper Slide Tuner", group = "tuners")
public class ViperSlideTuner extends LinearOpMode {
    private int slidePosition = 0;

    private double clawPosition = 0.0;

    private double hingePosition = 0.0;

    private boolean isClicked = false;

    @Override
    public void runOpMode() {
        ViperSlide vs = new ViperSlide("viper_1", "viper_2", "viper_claw", "hinge", hardwareMap, false);

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
                    slidePosition += 500;
                    isClicked = true;
                    vs.setPower(0.1);
                    vs.runToPosition();
                    vs.customPosition(slidePosition);
                }
            } else if (gamepad1.y) {
                if (!isClicked) {
                    slidePosition += 1000;
                    isClicked = true;
                    vs.setPower(0.1);
                    vs.runToPosition();
                    vs.customPosition(slidePosition);
                }
            } else if (gamepad1.dpad_down) {
                if (!isClicked) {
                    slidePosition -= 500;
                    isClicked = true;
                    vs.customPosition(slidePosition);
                }
            } else if (gamepad1.a) {
                if (!isClicked) {
                    slidePosition -= 1000;
                    isClicked = true;
                    vs.customPosition(slidePosition);
                }
            }  else if (gamepad1.left_stick_button) {
                if (!isClicked) {
                    clawPosition += 0.05;
                    isClicked = true;
                    vs.customClawPosition(clawPosition);
                }
            } else if (gamepad1.right_stick_button) {
                if (!isClicked) {
                    clawPosition -= 0.05;
                    isClicked = true;
                    vs.customClawPosition(clawPosition);
                }
            } else if (gamepad1.right_bumper) {
                if (!isClicked) {
                    hingePosition += 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.left_bumper) {
                if (!isClicked) {
                    hingePosition -= 0.05;
                    isClicked = true;
                }
            } else {
                isClicked = false;
            }

            vs.customHingePosition(hingePosition);

            // Output
            telemetry.addData("Claw Position", clawPosition);
            telemetry.addData("Hinge Position", hingePosition);
            telemetry.addData("Target Position", vs.viperOne().getTargetPosition());
            telemetry.addData("Current Position", vs.viperOne().getCurrentPosition());
            telemetry.addData("Power", vs.viperTwo().getPower());
            telemetry.addData("Target", vs.atDestination() + "");
            telemetry.addData("Position", slidePosition + "");
            telemetry.update();
        }
    }
}
