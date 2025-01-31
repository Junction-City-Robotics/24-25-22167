//package org.firstinspires.ftc.teamcode.control.opmodes.teleops.outdated;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.miscellaneous.input.ControllerActionManager;
//import org.firstinspires.ftc.teamcode.control.systems.drive.BasicDrive;
//import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Brush;
//import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Claw;
//import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Link;
//import org.firstinspires.ftc.teamcode.control.systems.inputOutput.ViperSlide;
//
//@TeleOp(name = "Scrimmage Controls", group = "teleops")
//@Disabled
//public class ScrimmageControls extends LinearOpMode {
//    // Controller
//    ControllerActionManager controller = new ControllerActionManager(gamepad1, gamepad2);
//
//    @Override
//    public void runOpMode() {
//        // Systems
//        BasicDrive drive = new BasicDrive("fl", "bl", "fr", "br", hardwareMap);
//        ViperSlide vs    = new ViperSlide("viper_1", "viper_2", hardwareMap);
//        Brush brush      = new Brush("wrist", "brush", hardwareMap);
//        Claw claw        = new Claw("claw", "hinge", hardwareMap);
//        Link link        = new Link("l1", "l2", hardwareMap);
//
//        telemetry.addData("Systems", "Systems are initialized. Ready to go.");
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            controller.updateState(gamepad1, gamepad2);
//
//            // Drive Section
//            String driveButton = controller.getDominantButton("drive");
//
//            // VS section
//
//            // Brush section
//
//            // Claw section
//
//            // Link section
//            String linkButton = controller.getDominantButton("link");
//            if ("rightJoystick".equals(linkButton)) {
//                if (gamepad2.right_stick_y < -0.3) {
//                    link.extendedPosition();
//                } else if (gamepad2.right_stick_y > 0.3) {
//                    link.startPosition();
//                }
//            }
//
//            // End of loop updates
//            telemetry.update();
//        }
//    }
//}
