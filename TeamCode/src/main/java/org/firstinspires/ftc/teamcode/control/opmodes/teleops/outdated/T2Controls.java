//package org.firstinspires.ftc.teamcode.control.opmodes.teleops.outdated;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.control.systems.outdated.drive.BasicDrive;
//import org.firstinspires.ftc.teamcode.control.systems.outdated.outdated.Brush;
//import org.firstinspires.ftc.teamcode.control.systems.outdated.outdated.Claw;
//import org.firstinspires.ftc.teamcode.control.systems.Link;
//import org.firstinspires.ftc.teamcode.control.systems.ViperSlide;
//import org.firstinspires.ftc.teamcode.miscellaneous.input.ControllerActionManager;
//
//@TeleOp(name = "T2 Controls", group = "teleops")
//public class T2Controls extends LinearOpMode {
//    private final ControllerActionManager controls = new ControllerActionManager(gamepad1, gamepad2);
//
//    @Override
//    public void runOpMode() {
//        // Drive Controls
//        BasicDrive drive = new BasicDrive("fl", "bl", "fr", "br", hardwareMap);
//
//        // Systems
//        ViperSlide vs = new ViperSlide("viper_1", "viper_2", hardwareMap);
//        Claw claw = new Claw("claw", "hinge", hardwareMap);
//        Link link = new Link("l1", "l2", hardwareMap);
//        Brush brush = new Brush("wrist", "brush", hardwareMap);
//
//        // After start is pressed
//        waitForStart();
//
//        drive.runWithEncoders();
//        vs.afterStart(1.0);
//        link.startPosition();
//
//        while (opModeIsActive()) {
//            controls.updateState(gamepad1, gamepad2);
//
//            // Controller 1 Drive
//            String driveButton = controls.getMostRecentButtonInSection("drive");
//            switch (driveButton) {
//                // Slow move precise controls
//                case "dpadUp":
//                    drive.forward(0.4);
//                    telemetry.addData("Drive Action", "Forward | Speed: 0.4");
//                    break;
//                case "dpadDown":
//                    drive.backward(0.4);
//                    telemetry.addData("Drive Action", "Backward | Speed: 0.4");
//                    break;
//                case "dpadLeft":
//                    drive.strafeLeft(0.6);
//                    telemetry.addData("Drive Action", "Strafe Left | Speed: 0.6");
//                    break;
//                case "dpadRight":
//                    drive.strafeRight(0.6);
//                    telemetry.addData("Drive Action", "Strafe Right | Speed: 0.6");
//                    break;
//                // Fast basic 4 directional controls
//                case "leftStick":
//                    if (gamepad1.left_stick_y < -0.5) {
//                        drive.forward(1.0);
//                        telemetry.addData("Drive Action", "Forward | Speed: 1.0");
//                    } else if (gamepad1.left_stick_y > 0.5) {
//                        telemetry.addData("Drive Action", "Backward | Speed: 1.0");
//                        drive.backward(1.0);
//                    } else if (gamepad1.left_stick_x < -0.5) {
//                        telemetry.addData("Drive Action", "Strafe Left | Speed: 1.0");
//                        drive.strafeLeft(1.0);
//                    } else if (gamepad1.left_stick_x > 0.5) {
//                        telemetry.addData("Drive Action", "Strafe Right | Speed: 1.0");
//                        drive.strafeRight(1.0);
//                    }
//                    break;
//                // Complex distribution right stick controls
//                case "rightStick":
//                    telemetry.addData("Drive Action", "Right Stick | Not implemented");
//                    drive.directionalDrive(1.0, gamepad1.right_stick_x, gamepad1.right_stick_y);
//                    drive.stop(); // REMOVE ONCE IMPLEMENTED
//                    break;
//                // Turning Controls
//                case "rightTrigger":
//                    if (gamepad1.y) {
//                        telemetry.addData("Drive Action", "Arc Right | Speed: 1.0");
//                        drive.arcRight(1.0);
//                    } else {
//                        telemetry.addData("Drive Action", "Turn Right | Speed: 1.0");
//                        drive.turnRight(1.0);
//                    }
//                    break;
//                case "leftTrigger":
//                    if (gamepad1.y) {
//                        telemetry.addData("Drive Action", "Arc Left | Speed: 1.0");
//                        drive.arcLeft(1.0);
//                    } else {
//                        telemetry.addData("Drive Action", "Turn Left | Speed: 1.0");
//                        drive.turnLeft(1.0);
//                    }
//                    break;
//                case "leftBumper":
//                    if (gamepad1.y) {
//                        telemetry.addData("Drive Action", "Arc Left | Speed: 0.4");
//                        drive.arcLeft(0.4);
//                    } else {
//                        telemetry.addData("Drive Action", "Turn Left | Speed: 0.4");
//                        drive.turnLeft(0.4);
//                    }
//                    break;
//                case "rightBumper":
//                    if (gamepad1.y) {
//                        telemetry.addData("Drive Action", "Arc Right | Speed: 0.4");
//                        drive.arcRight(0.4);
//                    } else {
//                        telemetry.addData("Drive Action", "Turn Right | Speed: 0.4");
//                        drive.turnRight(0.4);
//                    }
//                    break;
//                // Stopping movement
//                default:
//                    telemetry.addData("Drive Action", "Stop | Speed: 0.0");
//                    drive.stop();
//                    break;
//            }
//
//            // Controller 2 Systems
//            // Claw & Hinge deposit system
//            String clawButton = controls.getMostRecentButtonInSection("claw");
//            switch (clawButton) {
//                // Hinge
//                case "a":
//                    claw.hingeDown();
//                    break;
//                case "y":
//                    claw.hingeDeposit();
//                    break;
//                // Claw
//                case "b":
//                    claw.clawClose();
//                    break;
//                case "x":
//                    claw.clawOpen();
//                    break;
//            }
//
//            // Viper Slide
//            String vsButton = controls.getMostRecentButtonInSection("viperSlide");
//            switch (vsButton) {
//                case "leftStickDown":
//                    vs.down();
//                    break;
//                case "leftStickUp":
//                    vs.up();
//                    break;
//                case "leftStickSide":
//                    vs.halfway();
//                    break;
//            }
//
//            // Link
//            String linkButton = controls.getMostRecentButtonInSection("link");
//            switch (linkButton) {
//                case "rightStickUp":
//                    link.extendedPosition();
//                    break;
//                case "rightStickDown":
//                    link.startPosition();
//                    break;
//            }
//
//            // Brush & Wrist intake controls
//            String brushButton = controls.getMostRecentButtonInSection("brush");
//            switch (brushButton) {
//                // Brush
//                case "rightTrigger":
//                    brush.brushIntake();
//                    break;
//                case "leftTrigger":
//                    brush.brushOutput();
//                    break;
//                // Wrist
//                case "dpadUp":
//                    brush.wristToFloor();
//                    break;
//                case "dpadDown":
//                    brush.wristToDeposit();
//                    break;
//                case "dpadSide":
//                    brush.wristToHover();
//                    break;
//                // Stopping Wrist
//                default:
//                    brush.brushStop();
//                    break;
//            }
//
//            telemetry.update();
//        }
//    }
//}
