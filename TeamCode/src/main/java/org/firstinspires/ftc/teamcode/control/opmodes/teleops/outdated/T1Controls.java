//package org.firstinspires.ftc.teamcode.teleops.outdated;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.systems.drive.BasicDrive;
//import org.firstinspires.ftc.teamcode.systems.PassThrough;
//import org.firstinspires.ftc.teamcode.systems.inputOutput.ViperSlide;
//
//import java.util.AbstractMap.SimpleEntry;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//
//@TeleOp(name = "T1 Controls", group = "teleop")
//public class T1Controls extends LinearOpMode {
//
//    private final ElapsedTime runtime = new ElapsedTime();
//
//    // Controller Snap Tap and other tracked buttons
//    List<String> tracked_controls = Arrays.asList("A", "Y", "X", "B", "LeftShoulder", "RightShoulder", "LeftStick", "RightStick", "DpadUp", "DpadDown");
//    HashMap<String, ElapsedTime> snap_tap = new HashMap<String, ElapsedTime>();
//    HashMap<String, Boolean> is_clicked = new HashMap<String, Boolean>();
//
//    @Override
//    public void runOpMode() {
//        telemetry.addData("Status", "Initialized");
//
//        // System's Controls
//        BasicDrive drive = new BasicDrive("fl", "bl", "fr", "br", hardwareMap);
//        drive.RunWithEncoders();
//
//        ViperSlide viperSlide = new ViperSlide("viper_1", "viper_2", hardwareMap);
//        viperSlide.SetDirections(DcMotorSimple.Direction.FORWARD, DcMotorSimple.Direction.REVERSE);
//        viperSlide.SetPower(0.0);
//        viperSlide.Down();
//        viperSlide.ResetRunWithEncoders();
//        viperSlide.ZeroPowerBreakMode();
//        viperSlide.StartRunToPosition();
//
//        PassThrough passThrough = new PassThrough("brush", "bucket", "wrist", hardwareMap);
//        passThrough.WristStart();
//        passThrough.WristPower(0.0);
//
//        telemetry.addData("Systems", "Initialized");
//
//        // Setting up Snap tap and tracking
//        for (String button : tracked_controls) {
//            snap_tap.put(button, new ElapsedTime());
//            is_clicked.put(button, false);
//        }
//        telemetry.addData("Controls", "Initialized");
//
//        telemetry.update();
//
//        // Wait for the game to start (driver presses PLAY)
//        waitForStart();
//        runtime.reset();
//
//        // Setting to Start
//        viperSlide.SetPower(0.8);
//        viperSlide.Down();
//
//        passThrough.WristDeposit();
//        passThrough.BucketDown();
//
//        // run until the end of the match (driver presses STOP)
//        while (opModeIsActive()) {
//            List<SimpleEntry<String, Double>> time_ordered_controls = UpdateSnapTap();
//
//            // Moving According to Snap-Tap
//            String chosen_button = "Stop";
//            while (time_ordered_controls.size() != 0) {
//                SimpleEntry<String, Double> move_pair = time_ordered_controls.get(0);
//                if (Boolean.TRUE.equals(is_clicked.get(move_pair.getKey()))) {
//                    chosen_button = move_pair.getKey();
//                    break;
//                }
//                time_ordered_controls.remove(0);
//            }
//
//            // Moving The robot with chosen action
//            if (Objects.equals(chosen_button, "Stop")) {
//                telemetry.addData("Speed", "0.0 (Stopped)");
//                drive.Stop();
//            } else if (Objects.equals(chosen_button, "LeftShoulder")) {
//                double speed = gamepad1.left_trigger;// Math.sin((gamepad1.left_trigger * Math.PI) / 2);
//                telemetry.addData("Speed", speed);
//                drive.TurnLeft(speed);
//
//            } else if (Objects.equals(chosen_button, "DpadUp")) {
//                double speed = 0.25;
//                if (gamepad1.left_bumper && !gamepad1.right_bumper) {
//                    drive.ArcLeft(speed);
//                } else if (gamepad1.right_bumper && !gamepad1.left_bumper) {
//                    drive.ArcRight(speed);
//                } else if (gamepad1.right_bumper && gamepad1.left_bumper) {
//                    drive.Forward(speed * 1.2);
//                } else {
//                    drive.ArcLeft(0.0);
//                }
//                telemetry.addData("Speed", speed);
//
//            } else if (Objects.equals(chosen_button, "DpadDown")) {
//                double speed = -0.25;
//                if (gamepad1.left_bumper && !gamepad1.right_bumper) {
//                    drive.ArcLeft(speed);
//                } else if (gamepad1.right_bumper && !gamepad1.left_bumper) {
//                    drive.ArcRight(speed);
//                } else if (gamepad1.right_bumper && gamepad1.left_bumper) {
//                    drive.Backward(speed * -1.2);
//                } else {
//                    drive.Forward(0.0);
//                }
//                telemetry.addData("Speed", speed);
//
//            } else if (Objects.equals(chosen_button, "RightShoulder")) {
//                double speed = gamepad1.right_trigger;// Math.sin((gamepad1.right_trigger * Math.PI) / 2);
//                telemetry.addData("Speed", speed);
//                drive.TurnRight(speed);
//
//            } else if (Objects.equals(chosen_button, "LeftStick")) {
//                double direction_r = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);
//                double direction_d = Math.toDegrees(direction_r);
//                double magnitude = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2));
//                double speed = Math.min(1.0, magnitude);
//
//                telemetry.addData("Speed", speed);
//                telemetry.addData("Stick Direction (Deg)", direction_d);
//                telemetry.addData("Stick Direction (Rad)", direction_r);
//                telemetry.addData("Stick Magnitude", magnitude);
//
//                drive.DirectionalDrive(direction_d, speed);
//
//            } else if (Objects.equals(chosen_button, "RightStick")) {
//                double total_x = Math.abs(gamepad1.right_stick_x);
//                double total_y = Math.abs(gamepad1.right_stick_y);
//
//                if (total_x >= total_y) {
//                    double speed = Math.sin((total_x * Math.PI) / 2);
//                    telemetry.addData("Speed", speed);
//                    if (gamepad1.right_stick_x > 0.0) {
//                        drive.StrafeRight(speed);
//                        telemetry.addData("Direction", "Right");
//                    } else {
//                        drive.StrafeLeft(speed);
//                        telemetry.addData("Direction", "Left");
//                    }
//                } else {
//                    double speed = Math.sin((total_y * Math.PI) / 2);
//                    telemetry.addData("Speed", speed);
//                    if (gamepad1.right_stick_y > 0.0) {
//                        drive.Backward(speed);
//                        telemetry.addData("Direction", "Backward");
//                    } else {
//                        drive.Forward(speed);
//                        telemetry.addData("Direction", "Forward");
//                    }
//                }
//
//            } else if (Objects.equals(chosen_button, "A")) {
//                drive.Backward(0.2);
//                telemetry.addData("Speed", "0.2");
//            } else if (Objects.equals(chosen_button, "B")) {
//                drive.StrafeRight(0.2);
//                telemetry.addData("Speed", "0.2");
//            } else if (Objects.equals(chosen_button, "X")) {
//                drive.StrafeLeft(0.2);
//                telemetry.addData("Speed", "0.2");
//            } else if (Objects.equals(chosen_button, "Y")) {
//                drive.Forward(0.2);
//                telemetry.addData("Speed", "0.2");
//            } else {
//                telemetry.addData("WARNING", "NO POSSIbLE ACTION FOUND IN MOVING CHAIN: STOPPING");
//                telemetry.addData("Speed", "N/A | ERROR");
//                drive.Stop();
//            }
//
//            // Gamepad 2 Controls | Viper Slide
//            if (Math.abs(gamepad2.right_stick_x) > 0.8) {
//                viperSlide.SetPower(1.0);
//                viperSlide.StartRunToPosition();
//                viperSlide.Halfway();
//            } else if (gamepad2.right_stick_y > 0) {
//                viperSlide.SetPower(1.0);
//                viperSlide.StartRunToPosition();
//                viperSlide.Down();
//            } else if (gamepad2.right_stick_y < 0) {
//                viperSlide.SetPower(1.0);
//                viperSlide.StartRunToPosition();
//                viperSlide.Up();
//            } else if (gamepad2.right_stick_button) {
//                viperSlide.SetPower(0.0);
//            } else if (gamepad2.left_stick_y > 0.0) {
//                viperSlide.UseMotors();
//                viperSlide.SetPower(-1.0);
//            } else if (gamepad2.left_stick_y < 0.0) {
//                viperSlide.SetPower(1.0);
//            } else if (gamepad2.left_stick_button) {
//                viperSlide.SetPower(0.0);
//            }
//
//            // Pass through system
//            if (gamepad2.right_trigger > 0.0) {
//                passThrough.BrushIntake();
//            } else if (gamepad2.left_trigger > 0.0) {
//                passThrough.BrushOutput();
//            } else {
//                passThrough.BrushStop();
//            }
//
//            // Wrist
//            if (gamepad2.dpad_down) {
//                passThrough.WristPower(0.9);
//                passThrough.WristDeposit();
//            } else if (gamepad2.dpad_up) {
//                passThrough.WristPower(0.9);
//                passThrough.WristPickup();
//            } else if (gamepad2.dpad_left || gamepad2.dpad_right) {
//                passThrough.WristPower(0.9);
//                passThrough.WristStallPosition();
//            }
//
//            // Bucket
//            if (gamepad2.right_bumper) {
//                passThrough.BucketDown();
//            } else if (gamepad2.left_bumper) {
//                passThrough.BucketUp();
//            }
//
//            // Show the output for drivers
//            telemetry.addData("Button Active", chosen_button);
//            telemetry.addData("Run Time", runtime.toString());
//            telemetry.update();
//        }
//    }
//
//    private List<SimpleEntry<String, Double>> UpdateSnapTap() {
//        // Button/Stick Updating
//        if (gamepad1.a && !Boolean.TRUE.equals(is_clicked.get("A"))) {
//            snap_tap.put("A", new ElapsedTime());
//            is_clicked.put("A", true);
//        } else if (!gamepad1.a) {
//            is_clicked.put("A", false);
//        }
//        if (gamepad1.b && !Boolean.TRUE.equals(is_clicked.get("B"))) {
//            snap_tap.put("B", new ElapsedTime());
//            is_clicked.put("B", true);
//        } else if (!gamepad1.b) {
//            is_clicked.put("B", false);
//        }
//        if (gamepad1.y && !Boolean.TRUE.equals(is_clicked.get("Y"))) {
//            snap_tap.put("Y", new ElapsedTime());
//            is_clicked.put("Y", true);
//        } else if (!gamepad1.y) {
//            is_clicked.put("Y", false);
//        }
//        if (gamepad1.x && !Boolean.TRUE.equals(is_clicked.get("X"))) {
//            snap_tap.put("X", new ElapsedTime());
//            is_clicked.put("X", true);
//        } else if (!gamepad1.x) {
//            is_clicked.put("X", false);
//        }
//
//        if (gamepad1.left_trigger != 0 && !Boolean.TRUE.equals(is_clicked.get("LeftShoulder"))) {
//            snap_tap.put("LeftShoulder", new ElapsedTime());
//            is_clicked.put("LeftShoulder", true);
//        } else if (gamepad1.left_trigger == 0) {
//            is_clicked.put("LeftShoulder", false);
//        }
//        if (gamepad1.right_trigger != 0 && !Boolean.TRUE.equals(is_clicked.get("RightShoulder"))) {
//            snap_tap.put("RightShoulder", new ElapsedTime());
//            is_clicked.put("RightShoulder", true);
//        } else if (gamepad1.right_trigger == 0) {
//            is_clicked.put("RightShoulder", false);
//        }
//
//        if (gamepad1.dpad_up && !Boolean.TRUE.equals(is_clicked.get("DpadUp"))) {
//            snap_tap.put("DpadUp", new ElapsedTime());
//            is_clicked.put("DpadUp", true);
//        } else if (!gamepad1.dpad_up) {
//            is_clicked.put("DpadUp", false);
//        }
//        if (gamepad1.dpad_down && !Boolean.TRUE.equals(is_clicked.get("DpadDown"))) {
//            snap_tap.put("DpadDown", new ElapsedTime());
//            is_clicked.put("DpadDown", true);
//        } else if (!gamepad1.dpad_down) {
//            is_clicked.put("DpadDown", false);
//        }
//
//        if ((gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) && !Boolean.TRUE.equals(is_clicked.get("LeftStick"))) {
//            snap_tap.put("LeftStick", new ElapsedTime());
//            is_clicked.put("LeftStick", true);
//        } else if ((gamepad1.left_stick_x == 0 & gamepad1.left_stick_y == 0)) {
//            is_clicked.put("LeftStick", false);
//        }
//        if ((gamepad1.right_stick_x != 0 || gamepad1.right_stick_y != 0) && !Boolean.TRUE.equals(is_clicked.get("RightStick"))) {
//            snap_tap.put("RightStick", new ElapsedTime());
//            is_clicked.put("RightStick", true);
//        } else if ((gamepad1.right_stick_x == 0 & gamepad1.right_stick_y == 0)) {
//            is_clicked.put("RightStick", false);
//        }
//
//        // Snap tap priortazation list
//        List<String> iterable_controls = new ArrayList<>(tracked_controls);
//
//        List<SimpleEntry<String, Double>> ordered_list = new ArrayList<>();
//        do {
//            double fastest_time = -1.0;
//            String fastest_control = "";
//            for (String control : iterable_controls) {
//                if (Objects.requireNonNull(snap_tap.get(control)).seconds() > fastest_time) {
//                    fastest_time = Objects.requireNonNull(snap_tap.get(control)).seconds();
//                    fastest_control = control;
//                }
//            }
//            iterable_controls.remove(fastest_control);
//            ordered_list.add(new SimpleEntry<>(fastest_control, fastest_time));
//
//        } while (iterable_controls.size() != 0);
//
//        Collections.reverse(ordered_list);
//        return ordered_list;
//    }
//}
