package org.firstinspires.ftc.teamcode.control.opmodes.teleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Claw;
import org.firstinspires.ftc.teamcode.control.systems.inputOutput.Link;
import org.firstinspires.ftc.teamcode.control.systems.inputOutput.ViperSlide;
import org.firstinspires.ftc.teamcode.miscellaneous.input.ControllerActionManager;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public class BaseTeleop extends LinearOpMode {
    // Player Input
    protected final ControllerActionManager controls = new ControllerActionManager(gamepad1, gamepad2);

    // Systems
    protected ViperSlide vs;
    protected Claw claw;
    protected Link link;

    // RR Drive
    protected SampleMecanumDrive drive;

    // Loop or single run
    private final boolean runOpModeLoop;

    // Constructors
    public BaseTeleop(boolean runOpModeLoop) {
        this.runOpModeLoop = runOpModeLoop;
    }

    public BaseTeleop() {
        this.runOpModeLoop = true;
    }

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
}
