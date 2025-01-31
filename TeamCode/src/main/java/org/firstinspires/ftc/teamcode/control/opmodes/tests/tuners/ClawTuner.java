package org.firstinspires.ftc.teamcode.control.opmodes.tests.tuners;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Claw;

@TeleOp(name = "Claw Tuner", group = "tuners")
public class ClawTuner extends LinearOpMode {
    // Positions
    private double hingePosition = 0;
    private double clawPosition = 0;

    // Input handling button repetitive canceling
    private boolean dpadIsClicked = false;
    private boolean elseIsClicked = false;

    @Override
    public void runOpMode() {
        Claw claw = new Claw("claw", "hinge", hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            // Hinge input
            if (gamepad1.dpad_up) {
                if (!dpadIsClicked) {
                    hingePosition += 0.1;
                    dpadIsClicked = true;
                }
            } else if (gamepad1.dpad_down) {
                if (!dpadIsClicked) {
                    hingePosition -= 0.1;
                    dpadIsClicked = true;
                }
            } else {
                dpadIsClicked = false;
            }

            // Claw
            if (gamepad1.y) {
                if (!elseIsClicked) {
                    clawPosition += 0.1;
                    elseIsClicked = true;
                }
            } else if (gamepad1.a) {
                if (!elseIsClicked) {
                    clawPosition -= 0.1;
                    elseIsClicked = true;
                }
            } else {
                elseIsClicked = false;
            }

            // Setting positions
            claw.customClawPosition(clawPosition);
            claw.customHingePosition(hingePosition);

            // Output
            telemetry.addData("Hinge Position", hingePosition + "");
            telemetry.addData("Claw Position", clawPosition + "");
            telemetry.update();
        }
    }
}
