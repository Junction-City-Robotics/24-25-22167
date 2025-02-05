package org.firstinspires.ftc.teamcode.control.opmodes.teleops;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.opmodes.Positions;

@TeleOp(name = "T3 Controls", group = "teleops")
public class T3Controls extends BaseTeleop {
    @Override
    public void beforeStart() {
        vs.completeSetup();
        vs.setPower(0.0);


        super.beforeStart();
    }

    @Override
    public void afterStart() {
        vs.afterStart(1.0);

        super.afterStart();
    }

    @Override
    public void teleopContents() {
        // Updating variables that need to be monitored
        controls.updateState(gamepad1, gamepad2);
        drive.update();

        driveControls();
        systemsControls();

        telemetry.addData("Current Position", drive.getPoseEstimate());

        super.teleopContents();
    }

    private void driveControls() {
        String driveButton = controls.getMostRecentButtonInSection("drive");
        Pose2d poseEstimate = drive.getPoseEstimate();
        switch (driveButton) {
            // Full speed global drive, and turning
            case "rightStick":
            case "leftStick": {
                Vector2d input = new Vector2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x
                ).rotated(-poseEstimate.getHeading());

                drive.setWeightedDrivePower(
                        new Pose2d(
                                input.getX(),
                                input.getY(),
                                -gamepad1.right_stick_x
                        )
                );
                break;
            }
            // Resetting Position if needed
            case "rightTrigger": case "leftTrigger": {
                drive.setPoseEstimate(Positions.ORIGIN);
            } default: {
                drive.setWeightedDrivePower(
                        new Pose2d(
                                0, 0, 0
                        )
                );
            }
        }

        // Viper Slide
        String vsButton = controls.getMostRecentButtonInSection("viperSlide");
        switch (vsButton) {
            case "dpadDown":
                vs.down();
                break;
            case "dpadUp":
                vs.up();
                break;
            case "dpadSide":
                vs.halfway();
                break;
        }
    }

    protected void systemsControls() {
        // Claw & Hinge deposit system
        String clawButton = controls.getMostRecentButtonInSection("claw");
        switch (clawButton) {
            // Hinge
            case "a":
            case "rightBumper":
                vs.hingePickup();
                break;
            case "y": case "leftBumper":
                vs.hingeBucket();
                break;
            // Claw
            case "b":
                claw.close();
                break;
            case "x":
                claw.open();
                break;
        }

        // Link
        String linkButton = controls.getMostRecentButtonInSection("link");
        switch (linkButton) {
            case "rightStickUp":
                link.extendedPosition();
                break;
            case "rightStickDown":
                link.startPosition();
                break;
        }

        // Brush & Wrist intake controls
        String brushButton = controls.getMostRecentButtonInSection("brush");
        switch (brushButton) {
            // Brush
            case "rightTrigger":
                // TODO: USED TO BE BRUSH STUFF
                break;
            case "leftTrigger":
                // TODO: USED TO BE BRUSH STUFF
                break;
            // Wrist
            case "dpadUp":
                // TODO: USED TO BE BRUSH STUFF
                break;
            case "dpadDown":
                // TODO: USED TO BE BRUSH STUFF
                break;
            case "dpadSide":
                // TODO: USED TO BE BRUSH STUFF
                break;
            // Stopping Wrist
            default:
                // TODO: USED TO BE BRUSH STUFF
                break;
        }
    }
}
