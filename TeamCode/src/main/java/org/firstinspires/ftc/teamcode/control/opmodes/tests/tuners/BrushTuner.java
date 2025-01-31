package org.firstinspires.ftc.teamcode.control.opmodes.tests.tuners;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Brush;

@TeleOp(name = "Brush Tuner", group = "tuners")
public class BrushTuner extends LinearOpMode {
    private double position = 0;

    private boolean isClicked = false;

    @Override
    public void runOpMode() {
        Brush brush = new Brush("wrist", "brush", hardwareMap);

        waitForStart();
        brush.wristToCustomPosition(1000.0);
        while (opModeIsActive()) {
            // Input Handling for wrist position
            if (gamepad1.dpad_up) {
                if (!isClicked) {
                    position += 0.05;
                    isClicked = true;
                    brush.wristToCustomPosition(position);
                }
            } else if (gamepad1.y) {
                if (!isClicked) {
                    position += 0.1;
                    isClicked = true;
                    brush.wristToCustomPosition(position);
                }
            } else if (gamepad1.dpad_down) {
                if (!isClicked) {
                    position -= 0.05;
                    isClicked = true;
                    brush.wristToCustomPosition(position);
                }
            } else if (gamepad1.a) {
                if (!isClicked) {
                    position -= 0.01;
                    isClicked = true;
                    brush.wristToCustomPosition(position);
                }
            } else {
                isClicked = false;
            }

            // Brush I/O
            if (gamepad1.right_trigger > 0.5) {
                brush.brushIntake();
            } else if (gamepad1.left_trigger > 0.5) {
                brush.brushOutput();
            } else {
                brush.brushStop();
            }

            // Output
            telemetry.addData("Wrist Position", position + "");
            telemetry.update();
        }
    }
}
