//package org.firstinspires.ftc.teamcode.control.patterns;
//
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.teamcode.control.systems.outdated.outdated.Brush;
//import org.firstinspires.ftc.teamcode.control.systems.outdated.outdated.Claw;
//import org.firstinspires.ftc.teamcode.control.systems.Link;
//
//public class BringSpecimenToClaw extends Pattern {
//    // Timing Variables
//    private final ElapsedTime time = new ElapsedTime();
//
//    protected final static double WRIST_TO_DEPOSIT_TIME = 0.75;
//    protected final static double CLAW_CLOSE_TIME = 1.2;
//    protected final static double FINISH_TIME = 1.5;
//
//    // Hardware variables
//    private final Brush brush;
//    private final Link link;
//    private final Claw claw;
//
//    // For the isDone function
//    private boolean done = false;
//
//    public BringSpecimenToClaw(Brush brush, Link link, Claw claw) {
//        this.brush = brush;
//        this.link = link;
//        this.claw = claw;
//    }
//
//    @Override
//    public void begin() {
//        time.startTime();
//
//        claw.clawOpen();
//        link.startPosition();
//    }
//
//    @Override
//    public void update(Telemetry telemetry) {
//        telemetry.addData("Bring Specimen to Claw", "Claw Open & Link In");
//
//        if (time.seconds() >= WRIST_TO_DEPOSIT_TIME) {
//            brush.wristToDeposit();
//            telemetry.addData("Bring Specimen to Claw", "Wrist Back to Robot");
//        }
//
//        if (time.seconds() >= CLAW_CLOSE_TIME) {
//            claw.clawClose();
//            telemetry.addData("Bring Specimen to Claw", "Claw Closing");
//        }
//
//        if (time.seconds() >= FINISH_TIME) {
//            done = true;
//            telemetry.addData("Bring Specimen to Claw", "Transport Complete");
//        }
//    }
//
//    @Override
//    public boolean isDone() {
//        return done;
//    }
//}
