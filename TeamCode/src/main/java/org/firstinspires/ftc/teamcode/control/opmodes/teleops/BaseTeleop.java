package org.firstinspires.ftc.teamcode.control.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.control.systems.Claw;
import org.firstinspires.ftc.teamcode.control.systems.Link;
import org.firstinspires.ftc.teamcode.control.systems.Sensors;
import org.firstinspires.ftc.teamcode.control.systems.ViperSlide;
import org.firstinspires.ftc.teamcode.miscellaneous.input.ControllerActionManager;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BaseTeleop extends LinearOpMode {
    // Player Input
    protected final ControllerActionManager controls = new ControllerActionManager(gamepad1, gamepad2);

    // Systems
    protected ViperSlide vs;
    protected Claw claw;
    protected Link link;
    protected Sensors sensors;

    // RR Drive
    protected SampleMecanumDrive drive;

    // Loop or single run
    private final boolean runOpModeLoop;

    // Scheduled Tasks Object
     private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // Constructors
    public BaseTeleop(boolean runOpModeLoop) {
        this.runOpModeLoop = runOpModeLoop;
    }

    public BaseTeleop() {
        this.runOpModeLoop = true;
    }

    private void initSystems() {
        // External IO Systems
        vs = new ViperSlide("viper_1", "viper_2", "viper_claw", "hinge", "viper_touch", hardwareMap);
        claw = new Claw("finger", "wrist", "elbow", "arm", hardwareMap);
        link = new Link("l1", "l2", hardwareMap);
        sensors = new Sensors("color", "back_touch", hardwareMap);

        // RR Drive System
        drive = new SampleMecanumDrive(hardwareMap);
    }

    @Override
    public void runOpMode() {
        initSystems();

        beforeStart();

        waitForStart();

        afterStart();

        if (runOpModeLoop) {
            while (opModeIsActive() && !isStopRequested()) {
                teleopContents();
            }
        } else {
            teleopContents();
        }
    }

    public void beforeStart() {
        telemetry.update();
    }

    public void afterStart() {
        telemetry.update();
    }

    public void teleopContents() {
        telemetry.update();
    }

    protected void timeoutRunnable(double seconds, Runnable task) {
        scheduler.schedule(task, (long) seconds, TimeUnit.SECONDS);
    }
}
