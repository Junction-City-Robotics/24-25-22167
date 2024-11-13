package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.systems.ViperSlide;

@TeleOp(name = "Viper Slide Tests", group = "tests")
public class ViperSlideTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        int slide_position = ViperSlide.BOTTOM_POSITION;
        boolean has_clicked = false;

        ViperSlide viperSlide = new ViperSlide("v1", "v2");
        viperSlide.SetDirections(DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE);
        viperSlide.ResetRunWithEncoders();
        viperSlide.ZeroPowerBreakMode();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                viperSlide.SetPower(0.5);
                viperSlide.CustomPosition(slide_position);
            } else if (gamepad1.dpad_down) {
                viperSlide.SetPower(0.5);
                viperSlide.CustomPosition(slide_position);
            } else if (gamepad1.a || gamepad1.b || gamepad1.y || gamepad1.x) {
                viperSlide.SetPower(0.0);
            }

            // Adding To Positions
            if (gamepad1.right_bumper && !has_clicked) {
                slide_position += 5;
                has_clicked = true;
            } else if (gamepad1.right_trigger > 0 && !has_clicked) {
                slide_position += 20;
                has_clicked = true;
            } else if (gamepad1.dpad_right && !has_clicked) {
                slide_position += 1;
                has_clicked = true;
            }

            // Subtracting To Positions
            if (gamepad1.left_bumper && !has_clicked) {
                slide_position += 5;
                has_clicked = true;
            } else if (gamepad1.left_trigger > 0 && !has_clicked) {
                slide_position += 20;
                has_clicked = true;
            } else if (gamepad1.dpad_left && !has_clicked) {
                slide_position += 1;
                has_clicked = true;
            }

            // Resetting Has Clicked
            if (!gamepad1.dpad_left && !gamepad1.dpad_right &&
                gamepad1.right_trigger == 0 && gamepad1.left_trigger == 0 &&
                !gamepad1.left_bumper && !gamepad1.right_bumper) {
                has_clicked = false;
            }

            // Show the output for drivers
            telemetry.addData("Top Position", ViperSlide.TOP_POSITION);
            telemetry.addData("Bottom Position", ViperSlide.BOTTOM_POSITION);
            telemetry.addData("Slide Position", slide_position);
            telemetry.addData("Is Clicking", has_clicked);
            telemetry.addData("Instructions", "");
            telemetry.update();
        }
    }
}
