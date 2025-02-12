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
                case "leftStickButton": {
                    if (g1.left_stick_button) {
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
