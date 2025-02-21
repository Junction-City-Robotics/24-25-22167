package org.firstinspires.ftc.teamcode.control.opmodes.tests.tuners;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.systems.Link;

@TeleOp(name = "Link Tuner", group = "tuners")
@Config
public class LinkTuner extends LinearOpMode {
    public static double position = 0;

    public static boolean isClicked = false;

    @Override
    public void runOpMode() {
        // Link init
        Link link = new Link("l1", "l2", hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            // Input Handling
            if (gamepad1.dpad_up) {
                if (!isClicked) {
                    position += 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.y) {
                if (!isClicked) {
                    position += 0.1;
                    isClicked = true;
                }
            } else if (gamepad1.dpad_down) {
                if (!isClicked) {
                    position -= 0.05;
                    isClicked = true;
                }
            } else if (gamepad1.a) {
                if (!isClicked) {
                    position -= 0.1;
                    isClicked = true;
                }
            } else {
                isClicked = false;
            }

            link.setCustomPosition(position);

            // Telemetry
            telemetry.addData("Instructions", "Dpad up and y increase the position, " +
                    "and dpad down and a decrease it. This will change the link servo positions.");
            telemetry.addData("Position", position + "");
            telemetry.update();
        }
    }
}
