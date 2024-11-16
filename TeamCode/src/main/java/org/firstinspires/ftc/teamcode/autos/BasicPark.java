package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.systems.BasicDrive;

@Autonomous(name = "Basic Park", group = "autos")
public class BasicPark extends LinearOpMode {
    @Override
    public void runOpMode() {
        int forward = 0;

        ElapsedTime runtime = new ElapsedTime();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        BasicDrive drive = new BasicDrive("fl", "bl", "fr", "br", hardwareMap);
        drive.RunWithEncoders();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Send calculated power to wheels
            if (runtime.seconds() < 0.7) {
                drive.Forward(1.0);
                telemetry.addData("Current Task", "Moving out from wall");
            } else if (runtime.seconds() < 3.4) {
                if (forward == 0) {
                    forward = (drive.FL().getCurrentPosition() + drive.BR().getTargetPosition()) / 2;
                }
                drive.StrafeRight(0.3);
                telemetry.addData("Current Task", "Moving to Park Location");
            } else if (runtime.seconds() < 3.8) {
                drive.ArcRight(-0.3);
            } else {
                drive.Stop();
                telemetry.addData("Current Task", "Proccess Ended");
            }

            // Show the elapsed game time and wheel power.
            telemetry.addData("Forward Motion", forward);
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}
