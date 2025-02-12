//package org.firstinspires.ftc.teamcode.control.systems.outdated.outdated;
//
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//
//public class Claw {
//    private boolean isOpen = true;
//
//    private final Servo claw;
//    private final Servo hinge;
//
//    // Claw Positions
//    private final static double CLOSE_CLAW = 0.2;
//    private final static double OPEN_CLAW = 0.05;
//
//    // Hinge Positions
//    private final static double HINGE_PICKUP = 0.09;
//    private final static double HINGE_HALFWAY = 0.5;
//    private final static double HINGE_DEPOSIT = 0.9;
//
//    public Claw(String clawName, String hingeName, HardwareMap hMap) {
//        claw = hMap.get(Servo.class, clawName);
//        hinge = hMap.get(Servo.class, hingeName);
//    }
//
//    /*
//     * Hinge Positions
//     */
//    public void hingeDown() {
//        hinge.setPosition(HINGE_PICKUP);
//    }
//
//    public void hingeUp() {
//        hinge.setPosition(HINGE_HALFWAY);
//    }
//
//    public void hingeDeposit() {
//        hinge.setPosition(HINGE_DEPOSIT);
//    }
//
//    /*
//     * Claw Positions
//     */
//    public void toggleClaw() {
//        if (isOpen) {
//            isOpen = false;
//            clawClose();
//        } else {
//            isOpen = true;
//            clawOpen();
//        }
//    }
//    public void clawClose() {
//        claw.setPosition(CLOSE_CLAW);
//    }
//
//    public void clawOpen() {
//        claw.setPosition(OPEN_CLAW);
//    }
//
//    /*
//     * Custom positions for claw & hinge
//     */
//    public void customClawPosition(double position) {
//        claw.setPosition(position);
//    }
//
//    public void customHingePosition(double position) {
//        hinge.setPosition(position);
//    }
//}
