package org.firstinspires.ftc.teamcode.miscellaneous.input.sections;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.miscellaneous.input.TrackedButtonSection;

public class DriveButtonSection extends TrackedButtonSection {
    public DriveButtonSection(String section, String[] buttons) {
        super(section, buttons);
    }

    public void update(Gamepad g1, Gamepad g2) {
        for (int i = 0; i < buttons.length; i++) {
            
            switch (buttons[i].getName()) {
                case "leftStick": {
                    if (Math.abs(g1.left_stick_x) > 0.1 || Math.abs(g1.left_stick_y) > 0.1) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
                case "rightStick": {
                    if (Math.abs(g1.right_stick_x) > 0.1 || Math.abs(g1.right_stick_y) > 0.1) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
                case "dpadUp": {
                    if (g1.dpad_up) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
                case "dpadDown": {
                    if (g1.dpad_down) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
                case "dpadLeft": {
                    if (g1.dpad_left) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
                 case "dpadRight": {
                    if (g1.dpad_right) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
                case "rightTrigger": {
                    if (g1.right_trigger > 0.1) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
                case "leftTrigger": {
                    if (g1.left_trigger > 0.1) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
                case "rightBumper": {
                    if (g1.right_bumper) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
                case "leftBumper": {
                    if (g1.left_bumper) {
                        if (!buttons[i].isClicked()) {
                            buttons[i].resetTimer();
                        }
                        buttons[i].setClicked(true);
                    } else {
                        buttons[i].setClicked(false);
                    }
                    break;
                }
            }
        }

        super.update(g1, g2);
    }
}
