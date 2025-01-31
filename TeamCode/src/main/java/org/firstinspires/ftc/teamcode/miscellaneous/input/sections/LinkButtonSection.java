package org.firstinspires.ftc.teamcode.miscellaneous.input.sections;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.miscellaneous.input.ButtonManager;
import org.firstinspires.ftc.teamcode.miscellaneous.input.TrackedButtonSection;

public class LinkButtonSection extends TrackedButtonSection {
    public LinkButtonSection(String section, String[] buttons) {
        super(section, buttons);
    }

    public void update(Gamepad g1, Gamepad g2) {
        for (ButtonManager button : buttons) {
            switch (button.getName()) {
                case "rightStickUp": {
                    if (g2.right_stick_y < -0.5) {
                        if (!button.isClicked()) {
                            button.resetTimer();
                        }
                        button.setClicked(true);
                    } else {
                        button.setClicked(false);
                    }
                    break;
                }
                case "rightStickDown": {
                    if (g2.right_stick_y > 0.5) {
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
