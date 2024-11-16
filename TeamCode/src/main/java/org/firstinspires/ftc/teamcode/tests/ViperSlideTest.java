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



        ViperSlide viperSlide = new ViperSlide("viper_1", "viper_2", hardwareMap);
        viperSlide.SetDirections(DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE);
        viperSlide.ResetRunWithEncoders();
        viperSlide.StartRunToPosition();
        viperSlide.ZeroPowerBreakMode();

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                viperSlide.SetPower(0.8);
                viperSlide.Up();
            } else if (gamepad1.dpad_down) {
                viperSlide.SetPower(-0.8);
                viperSlide.Down();
            } else if (gamepad1.a || gamepad1.b || gamepad1.y || gamepad1.x) {
                viperSlide.SetPower(0.0);
            }

            // Show the output for drivers
            telemetry.addData("Current", viperSlide.V1().getCurrentPosition());
            telemetry.addData("Top Position", ViperSlide.TOP_POSITION);
            telemetry.addData("Bottom Position", ViperSlide.BOTTOM_POSITION);
            telemetry.addData("Slide Position", slide_position);
            telemetry.addData("Is Clicking", has_clicked);
            telemetry.addData("Instructions", "The 'Slide Position' Is the set position the viper slide is currently at." +
                    "Pressing The Dpad Up will set it to the usual up position, and the dpad down will set it to the usual down position." +
                    "Pressing Right Bumper will ad 5 to it, right trigger will add 20, and dpad right will add 1." +
                    "Use the left variants will subtract instead of add. It will then go to that position. Record the positions you want to" +
                    " save, so you have the correct positions.");
            telemetry.update();
        }
    }
}
