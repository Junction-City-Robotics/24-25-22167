package org.firstinspires.ftc.teamcode.miscellaneous.input.sections;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.miscellaneous.input.ButtonManager;
import org.firstinspires.ftc.teamcode.miscellaneous.input.TrackedButtonSection;

public class ClawButtonSection extends TrackedButtonSection {
    public ClawButtonSection(String section, String[] buttons) {
        super(section, buttons);
    }

    public void update(Gamepad g1, Gamepad g2) {
        for (ButtonManager button : buttons) {
            switch (button.getName()) {
                case "a": {
                    if (g2.a) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "b": {
                    if (g2.b) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "y": {
                    if (g2.y) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "x": {
                    if (g2.x) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "dpadUp" : {
                    if (g2.dpad_up) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "dpadDown" : {
                    if (g2.dpad_down) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "dpadLeft": {
                    if (g2.dpad_left) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "dpadRight": {
                    if (g2.dpad_right) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "leftBumper": {
                    if (g2.left_bumper) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "rightBumper": {
                    if (g2.right_bumper) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "leftTrigger": {
                    if (g2.left_trigger > 0.1) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "rightTrigger": {
                    if (g2.right_trigger > 0.1) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "leftStick": {
                    if (Math.abs(g2.left_stick_x) > 0.2) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "rightStick": {
                    if (Math.abs(g2.right_stick_y) > 0.2) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
            }
        }

        super.update(g1, g2);
    }
}
