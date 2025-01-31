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
                case "leftStickUp": {
                    if (g2.left_stick_y < -0.5) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "leftStickDown": {
                    if (g2.left_stick_y > 0.5) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "leftStickSide": {
                    if (Math.abs(g2.left_stick_x) > 0.5) {
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
