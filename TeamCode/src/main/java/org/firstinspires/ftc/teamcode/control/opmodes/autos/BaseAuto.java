package org.firstinspires.ftc.teamcode.control.opmodes.autos;

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Claw;
import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Link;
import org.firstinspires.ftc.teamcode.control.systems.inputOutput.ViperSlide;
import org.firstinspires.ftc.teamcode.miscellaneous.SessionStorage;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BaseAuto extends LinearOpMode {
    // Timer
    protected final ElapsedTime time = new ElapsedTime();

    // Systems
    protected ViperSlide vs;
    protected Claw claw;
    protected Link link;

    // RR Drive
    protected SampleMecanumDrive drive;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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

    protected void timeoutRunnable(double seconds, Runnable task) {
        scheduler.schedule(task, (long) seconds, TimeUnit.SECONDS);
    }

    protected void updateSystems(ViperSlide viperSlide, MecanumDrive mecanumDrive) {
        while (!isStopRequested()) {
            timeoutRunnable(0.5, () -> {
                SessionStorage.viperslideStartOffset = viperSlide.getPosition();
                SessionStorage.teleopEntryPose = mecanumDrive.getPoseEstimate();
            });
        }
    }
}
