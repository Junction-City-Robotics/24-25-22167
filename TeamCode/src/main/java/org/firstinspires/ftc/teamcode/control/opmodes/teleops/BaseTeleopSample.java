package org.firstinspires.ftc.teamcode.control.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.miscellaneous.input.ControllerActionManager;

@TeleOp(name = "Base Teleop Sample", group = "teleop")

public class BaseTeleopSample extends BaseTeleop {
    @Override
    public void teleopContents() {
        // Sample code
        controls.updateState(gamepad1, gamepad2);

        telemetry.addData("Drive button pressed", controls.getMostRecentButtonInSection("drive"));

        super.teleopContents();
    }

    @Override
    public void beforeStart() {

        // Before the main loop and before the start button

        super.beforeStart();
    }

    @Override
    public void afterStart() {

        // Before main loop, after start button

        super.afterStart();
    }
}
