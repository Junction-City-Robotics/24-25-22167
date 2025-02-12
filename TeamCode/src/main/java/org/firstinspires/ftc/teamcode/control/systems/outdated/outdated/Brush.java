//package org.firstinspires.ftc.teamcode.control.systems.outdated.outdated;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//
//@Config
//public class Brush {
//    public final Servo wrist;
//    private final Servo brush;
//
//    // Brush constants
//    public final static double BRUSH_INPUT = 0.0;
//    public final static double BRUSH_OUTPUT = 1.0;
//    public final static double BRUSH_STOP = 0.5;
//
//    // Wrist variables
//    public final static double WRIST_FLOOR = 0.17;
//    public final static double WRIST_HOVER = 0.39;
//    public final static double WRIST_DEPOSIT = 0.67;
//
//    public Brush(String wristName, String brushName, HardwareMap hMap) {
//        wrist = hMap.get(Servo.class, wristName);
//        brush = hMap.get(Servo.class, brushName);
//    }
//
//    /*
//     * Wrist controls (Pick up position, halfway hover, and deposit)
//     */
//    public void wristToFloor() {
//        wrist.setPosition(WRIST_FLOOR);
//    }
//
//    public void wristToHover() {
//        wrist.setPosition(WRIST_HOVER);
//    }
//
//    public void wristToDeposit() {
//        wrist.setPosition(WRIST_DEPOSIT);
//    }
//
//    public void wristToCustomPosition(double position) {
//        wrist.setPosition(position);
//    }
//
//    /*
//     * Brush controls (I/O, Stop)
//     */
//    public void brushIntake() {
//        brush.setPosition(BRUSH_INPUT);
//    }
//
//    public void brushOutput() {
//        brush.setPosition(BRUSH_OUTPUT);
//    }
//
//    public void brushStop() {
//        brush.setPosition(BRUSH_STOP);
//    }
//
//    /**
//     * <p>Gets the servo brush object.</p>
//     * @return Servo object for the brush
//     */
//    public Servo getBrush() {
//        return brush;
//    }
//
//    /**
//     * <p>Gets the servo wrist object.</p>
//     * @return Servo object for te wrist
//     */
//    public Servo getWrist() {
//        return wrist;
//    }
//}
