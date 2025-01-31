package org.firstinspires.ftc.teamcode.control.opmodes.teleops;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.control.opmodes.Positions;

@TeleOp(name = "T2A Controls", group = "teleops")
public class T2AControls extends BaseTeleop {
    @Override
    public void beforeStart() {
        vs.completeSetup();
        vs.setPower(0.0);


        super.beforeStart();
    }

    @Override
    public void afterStart() {
        vs.afterStart(1.0);
        brush.wristToHover();

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
            } case "dpadUp": case "dpadDown": case "dpadLeft": case "dpadRight": {
                drive.setPoseEstimate(Positions.ORIGIN);
            } default: {
                drive.setWeightedDrivePower(
                        new Pose2d(
                                0, 0, 0
                        )
                );
            }
            // Basic Turning
//            case "rightShoulder": {
//                drive.turn(Math.toRadians(-90));
//                break;
//            } case "leftShoulder": {
//                drive.turn(Math.toRadians(90));
//                break;
//            } case "rightBumper": {
//                drive.turn(Math.toRadians(-30));
//                break;
//            } case "leftBumper": {
//                drive.turn(Math.toRadians(30));
//                break;
//            }
            // Simple Dpad Controls
//            case "dpadUp": {
//                drive.setWeightedDrivePower(
//                        new Pose2d(
//                                1.0,
//                                0.0,
//                                poseEstimate.getHeading()
//                        )
//                );
//                break;
//            } case "dpadDown": {
//                drive.setWeightedDrivePower(
//                        new Pose2d(
//                                -1.0,
//                                0.0,
//                                poseEstimate.getHeading()
//                        )
//                );
//                break;
//            } case "dpadLeft": {
//                drive.setWeightedDrivePower(
//                        new Pose2d(
//                                0.0,
//                                1.0,
//                                poseEstimate.getHeading()
//                        )
//                );
//                break;
//            } case "dpadRight": {
//                drive.setWeightedDrivePower(
//                        new Pose2d(
//                                0.0,
//                                -1.0,
//                                poseEstimate.getHeading()
//                        )
//                );
//                break;
//            }
        }
    }

    protected void systemsControls() {
        // Claw & Hinge deposit system
        String clawButton = controls.getMostRecentButtonInSection("claw");
        switch (clawButton) {
            // Hinge
            case "a":
            case "rightBumper":
                claw.hingeDown();
                break;
            case "y":
            case "leftBumper":
                claw.hingeDeposit();
                break;
            // Claw
            case "b":
                claw.clawClose();
                break;
            case "x":
                claw.clawOpen();
                break;
        }

        // Viper Slide
        String vsButton = controls.getMostRecentButtonInSection("viperSlide");
        switch (vsButton) {
            case "leftStickDown":
                vs.down();
                break;
            case "leftStickUp":
                vs.up();
                break;
            case "leftStickSide":
                vs.halfway();
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
                brush.brushIntake();
                break;
            case "leftTrigger":
                brush.brushOutput();
                break;
            // Wrist
            case "dpadUp":
                brush.wristToFloor();
                break;
            case "dpadDown":
                brush.wristToDeposit();
                break;
            case "dpadSide":
                brush.wristToHover();
                break;
            // Stopping Wrist
            default:
                brush.brushStop();
                break;
        }
    }
}
