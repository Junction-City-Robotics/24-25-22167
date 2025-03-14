package org.firstinspires.ftc.teamcode.control.opmodes.autos;

import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.control.systems.Claw;
import org.firstinspires.ftc.teamcode.control.systems.Link;
import org.firstinspires.ftc.teamcode.control.systems.Sensors;
import org.firstinspires.ftc.teamcode.control.systems.ViperSlide;
import org.firstinspires.ftc.teamcode.miscellaneous.Globals;
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
    protected Sensors sensors;

    // RR Drive
    protected SampleMecanumDrive drive;

    // Here to schedule the tasks to run. See `protected function timeoutRunnable()`
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * <p>
     *     Function to initialize all of the systems, after opmode has been initialized. Not in
     *     constructor because Opmode tools (HardwareMap) Doesn't work.
     * </p>
     */
    private void initSystems() {
        // External IO Systems
        vs = new ViperSlide("viper_1", "viper_2", "viper_claw", "hinge", "viper_touch", hardwareMap);
        claw = new Claw("finger", "wrist", "elbow", "arm", hardwareMap);
        link = new Link("l1", "l2", hardwareMap);
        sensors = new Sensors("color", "back_touch", hardwareMap);

        // RR Drive System
        drive = new SampleMecanumDrive(hardwareMap);
    }

    /**
     * <p>
     *     WARNING: This should not be run in the code. Only by the REV Driver Hub.
     *     Main contents of the auto. This assembles the overridden functions into a complete
     *     opmode / program.
     * </p>
     */
    @Override
    public void runOpMode() {
        initSystems();

        beforeStart();

        waitForStart();

        time.startTime();

        if (!isStopRequested()) {
            autoContents();
        }

        Globals.teleopEntryPose = drive.getPoseEstimate();
    }

    /**
     * <p>
     *     The code ran before the actual play button has been hit.
     *     After init button has been pressed.
     * </p>
     */
    public void beforeStart() {
        telemetry.update();
    }

    /**
     * <p>
     *     The code that runs after the start button has pressed. Also after init button has been
     *     pressed.
     * </p>
     */
    public void autoContents() {
        telemetry.update();
    }

    /**
     * <p>
     *     Schedules a task to be done after the set amount of time.
     *     NOTE: This runs on a separate thread.
     * </p>
     * @param seconds Amount of seconds to wait
     * @param task Lambda / Runnable scheduled to run
     */
    protected void timeoutRunnable(double seconds, Runnable task) {
        scheduler.schedule(task, (long) seconds, TimeUnit.SECONDS);
    }

    /**
     * <p>
     *     Custom function to wait a certain amount of seconds, because normal wait() function
     *     doesn't work. This will wait the allotted amount of time. This function also checks
     *     to see if a stop is requested in the Opmode, and if the Opmode is still running.
     * </p>
     * @param seconds Amount of seconds to wait
     */
    protected void waitSeconds(double seconds) {
        // Gets current time, and starts tracking
        ElapsedTime c = new ElapsedTime();
        c.startTime();

        // Wait till tracked time exceeds / reaches requested time. Also checks for opmode stops.
        while (c.seconds() <= seconds && !isStopRequested() && opModeIsActive()) {
            continue;
        }
    }
}
