//package org.firstinspires.ftc.teamcode.control.opmodes.autos.outdated;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.control.systems.outdated.BasicDrive;
//
//@Autonomous(name = "Basic Park", group = "autos")
//public class TimeBasedPark extends LinearOpMode {
//    @Override
//    public void runOpMode() {
//        int forward = 0;
//
//        ElapsedTime runtime = new ElapsedTime();
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//        BasicDrive drive = new BasicDrive("fl", "bl", "fr", "br", hardwareMap);
//        drive.runWithEncoders();
//
//        // Wait for the game to start (driver presses PLAY)
//        waitForStart();
//
//        // run until the end of the match (driver presses STOP)
//        while (opModeIsActive()) {
//
//            // Send calculated power to wheels
//            if (runtime.seconds() < 0.7) {
//                drive.forward(0.3);
//                telemetry.addData("Current Task", "Moving out from wall");
//            } else if (runtime.seconds() < 3.4) {
//                if (forward == 0) {
//                    forward = (drive.fl().getCurrentPosition() + drive.br().getTargetPosition() +
//                            drive.fr().getCurrentPosition() + drive.bl().getCurrentPosition()) / 4;
//                }
//                drive.strafeRight(0.3);
//                telemetry.addData("Current Task", "Moving to Park Location");
//            } else if (runtime.seconds() < 3.8) {
//                drive.arcRight(-0.3);
//            } else {
//                drive.stop();
//                telemetry.addData("Current Task", "Process Ended");
//            }
//
//            // Show the elapsed game time and wheel power.
//            telemetry.addData("Forward Motion", forward);
//            telemetry.addData("Status", "Run Time: " + runtime);
//            telemetry.update();
//        }
//    }
//}
