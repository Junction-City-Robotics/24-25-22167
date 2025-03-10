package org.firstinspires.ftc.teamcode.miscellaneous.input.sections;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.miscellaneous.input.ButtonManager;
import org.firstinspires.ftc.teamcode.miscellaneous.input.TrackedButtonSection;

public class ViperSlideButtonSection extends TrackedButtonSection {
    public ViperSlideButtonSection(String section, String[] buttons) {
        super(section, buttons);
    }

    public void update(Gamepad g1, Gamepad g2) {
        for (ButtonManager button : buttons) {
            switch (button.getName()) {
                case "y": {
                    if (g1.y) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "a": {
                    if (g1.a) {
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
                    if (g1.x) {
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
                    if (g1.b) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "rightStickButton": {
                    if (g1.right_stick_button) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "dpadUp": {
                    if (g1.dpad_up) {
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
