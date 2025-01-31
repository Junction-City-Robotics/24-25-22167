package org.firstinspires.ftc.teamcode.control.opmodes.tests.patternTesters;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.opmodes.teleops.BaseTeleop;

@TeleOp(name = "Bring Specimen To Claw Test", group = "patternTests")
public class BringSpecimenToClawTest extends BaseTeleop {
    @Override
    public void teleopContents() {
        if (gamepad1.a) {

        } else if (gamepad1.b) {

        }

        super.teleopContents();
    }

    @Override
    public void beforeStart() {
        super.beforeStart();
    }

    @Override
    public void afterStart() {
        // Stopping other systems
        vs.setPower(0.0);

        // Prepping other systems
        link.extendedPosition();
        brush.brushIntake();
        claw.clawClose();

        super.afterStart();
    }
}
