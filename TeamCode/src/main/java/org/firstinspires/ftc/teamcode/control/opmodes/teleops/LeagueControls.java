package org.firstinspires.ftc.teamcode.control.opmodes.teleops;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.control.opmodes.Positions;

@TeleOp(name = "League Controls", group = "teleops")
public class LeagueControls extends BaseTeleop {
    private boolean isPressingB = false;

    private boolean isHanging = false;

    @Override
    public void beforeStart() {
        vs.completeSetup();
        vs.setPower(0.0);

        super.beforeStart();
    }

    @Override
    public void afterStart() {
        vs.afterStart(1.0);

        claw.fingerOpen();
        vs.hingePickup();

        super.afterStart();
    }

    @Override
    public void teleopContents() {
        // Updating variables that need to be monitored
        controls.updateState(gamepad1, gamepad2);
        drive.update();
        vs.updateOffsetReset();

        driveControls();
        systemsControls();

        // Updating auto hang
        if (sensors.isTouchingBack() && !isHanging && !vs.clawIsOpen() && !vs.isResettingOffset()) {
            isHanging = true;

            // Actually slamming it down
            vs.hang();

            waitSeconds(0.5);
            vs.hingeForceHang();

            // Returning back to normal position
            waitSeconds(0.7);
            vs.openClaw();

            waitSeconds(0.2);
            vs.hingePickup();
            vs.down();
        }
        // Resetting Hanging Status
        else if (isHanging) {
            if (!sensors.isTouchingBack()) {
                isHanging = false;
            }
        }

        // Updates
        telemetry.addData("Current Position", drive.getPoseEstimate());
        telemetry.addData("Viperslide Position (Offset Included)", vs.getPosition());

        telemetry.addData("Red Seen", sensors.getColors().red);
        telemetry.addData("Alpha Seen", sensors.getColors().alpha);
        telemetry.addData("Distance", sensors.getDistance());
        telemetry.addData("Main Claw Closed", claw.isClosed());

        super.teleopContents();
    }

    // First driver controls
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
            case "leftStickButton": {
                drive.setPoseEstimate(new Pose2d(drive.getPoseEstimate().getX(), drive.getPoseEstimate().getY(), Math.toRadians(0)));
            }
            default: {
                drive.setWeightedDrivePower(new Pose2d(0, 0, 0));
                break;
            }
        }

        // Link
        String linkButton = controls.getMostRecentButtonInSection("link");
        switch (linkButton) {
            case "rightTrigger":
                link.extendedPosition();
                break;
            case "leftTrigger":
                link.startPosition();
                break;
        }

        // Viper Slide
        String vsButton = controls.getMostRecentButtonInSection("viperSlide");
        switch (vsButton) {
            case "a":
                if (!vs.isResettingOffset()) {
                    vs.down();
                }
                break;
            case "b":
                if (!vs.isResettingOffset()) {
                    vs.hang();
                }
                break;
            case "y":
                if (!vs.isResettingOffset()) {
                    vs.up();
                }
                break;
            case "x":
                if (!vs.isResettingOffset()) {
                    vs.halfway();
                }
                break;
            case "rightStickButton":
                vs.resetOffset();
                break;

            case "dpadUp": // Hanging specimen macro
                if (!vs.isResettingOffset()) {
                    vs.hang();
                }

                timeoutRunnable(1.0, () -> vs.hingeForceHang());
                timeoutRunnable(1.75, () -> vs.openClaw());
                timeoutRunnable(2.15, () -> vs.hingePickup());
                timeoutRunnable(2.55, () -> vs.down());
                break;
        }
    }

    // Second driver controls
    private void systemsControls() {
        // Claw & Hinge deposit system
        String clawButton = controls.getMostRecentButtonInSection("claw");
        switch (clawButton) {
            // Viperslide Claw
            case "dpadUp":
                vs.openClaw();
                break;
            case "dpadDown":
                vs.closeClaw();
                break;
            // Front Claw
            case "a":
                claw.fingerClose();
                break;
            case "y":
                claw.fingerOpen();
                break;

            case "b":
                // Checking to see if already pressing B
                if (!isPressingB) {
                    // If claw is open, close it. If it's closed, open it.
                    if (claw.isClosed()) {
                        claw.fingerOpen();
                    } else {
                        claw.fingerClose();
                    }
                }
                isPressingB = true;
                break;

            // Hinge
            case "dpadLeft":
                vs.hingeBucket();
                break;
            case "dpadRight":
                vs.hingePickup();
                break;

            case "x":
                vs.hingeBucket();

                timeoutRunnable(1.5, () -> vs.openClaw());

                timeoutRunnable(2.2, () -> vs.hingePickup());
                break;

            // Presets
            // Crane / Hang (Setting everything to half so it can get out the way)
            case "rightBumper":
                claw.crane();
                break;

            // Grabbing Pixel into the viperslide claw
            case "leftBumper":
                vs.closeClaw();

                timeoutRunnable(0.85, () -> claw.fingerOpen());

                timeoutRunnable(1.0, () -> {
                    claw.crane();
                });

                break;

            // Dropdown to pickup
            case "rightTrigger":
                claw.pickup();

                break;

            // Full deposit 
            case "leftTrigger":
                // Setting arm into deposit position
                claw.deposit();

                // Bringing link in
                link.startPosition();

                // Getting viperslide claw to pickup
                vs.hingePickup();
//                timeoutRunnable(1.2, () -> vs.hingePickup());
                vs.openClaw();
                break;

            // Adjusting Wrist
            case "leftStick":
//                claw.wristRotatedPercent(gamepad2.left_stick_x);
                break;
            case "rightStick":
//                claw.elbowPositionPercent(1.0 - Math.abs(gamepad2.right_stick_y));
                break;
            default:
                claw.wristDeposit();
                isPressingB = false;
                break;
        }
    }
}
