//package org.firstinspires.ftc.teamcode.control.opmodes.autos.outdated;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.control.systems.drive.EncoderMeasuredDrive;
//
//@Autonomous(name = "Measured Park", group = "autos")
//public class EncoderMeasuredPark extends LinearOpMode {
//    @Override
//    public void runOpMode() {
//        EncoderMeasuredDrive drive = new EncoderMeasuredDrive("fl", "bl", "fr", "br", hardwareMap);
//        drive.StartDrive();
//
////        PassThrough passThrough = new PassThrough("brush", "bucket", "wrist", hardwareMap);
////        passThrough.WristStart();
////        passThrough.WristPower(0.0);
//
//        // Wait for the game to start (driver presses PLAY)
//        waitForStart();
//
//        drive.ResetStep();
//        drive.Straight(1.0);
//        drive.SetAllPower(0.5);
//        // run until the end of the match (driver presses STOP)
//        while (opModeIsActive()) {
//            telemetry.addData("Count", drive.Step());
//
//            // Initial Forward
//            if (drive.RoughAtTarget() && drive.StepIs(0)) {
//                drive.Strafe(3.5);
//                drive.CountStep();
//                telemetry.addData("Count", drive.Step());
//            }
//            // Strafe Right
//            else if (drive.RoughAtTarget() && drive.StepIs(1)) {
//                drive.Straight(-1.0);
//                drive.CountStep();
//                telemetry.addData("Count", drive.Step());
//
////                passThrough.WristPower(0.5);
////                passThrough.WristDeposit();
//            }
//            // Back Up Back in
//            else if (drive.RoughAtTarget() && drive.StepIs(2)) {
//                // drive.SetAllPower(0.0);
//                telemetry.addData("Count", drive.Step());
//            }
//        }
//    }
//}
