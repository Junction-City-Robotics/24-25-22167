package org.firstinspires.ftc.teamcode.control.opmodes.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Claw;
import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Link;
import org.firstinspires.ftc.teamcode.control.systems.inputOutput.ViperSlide;
import org.firstinspires.ftc.teamcode.miscellaneous.SessionStorage;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public class BaseAuto extends LinearOpMode {
    // Timer
    protected final ElapsedTime time = new ElapsedTime();

    // Systems
    protected ViperSlide vs;
    protected Claw claw;
    protected Link link;

    // RR Drive
    protected SampleMecanumDrive drive;

    private void initSystems() {
        // External IO Systems
        vs = new ViperSlide("viper_1", "viper_2", "viper_claw", "hinge", hardwareMap);
        claw = new Claw("finger", "wrist", "elbow", "arm", hardwareMap);
        link = new Link("l1", "l2", hardwareMap);

        // RR Drive System
        drive = new SampleMecanumDrive(hardwareMap);
    }

    @Override
    public void runOpMode() {
        initSystems();

        beforeStart();

        waitForStart();

        time.startTime();

        if (!isStopRequested()) {
            autoContents();
        }

        SessionStorage.teleopEntryPose = drive.getPoseEstimate();
    }

    public void beforeStart() {
        telemetry.update();
    }


    public void autoContents() {
        telemetry.update();
    }
}
