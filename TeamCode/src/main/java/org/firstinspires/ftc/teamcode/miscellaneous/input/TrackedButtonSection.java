package org.firstinspires.ftc.teamcode.miscellaneous.input;

import com.qualcomm.robotcore.hardware.Gamepad;

public class TrackedButtonSection {
    protected static final String NO_BUTTON = "NO BUTTON";

    private final String section;
    protected final ButtonManager[] buttons;

    public TrackedButtonSection(String section, String[] buttonNames) {
        this.section = section;

        buttons = new ButtonManager[buttonNames.length];
        for (int i = 0; i < buttonNames.length; i++) {
            buttons[i] = new ButtonManager(buttonNames[i], true);
        }
    }

    public void update(Gamepad g1, Gamepad g2) {
        for (ButtonManager button : buttons) {
            if (!button.isClicked()) {
                button.resetTimer();
            }
        }
    }

    public String mostRecentValidClick() {
        ButtonManager candidate = new ButtonManager(NO_BUTTON, false);
        for (ButtonManager button : buttons) {
            if (button.isClicked()) {
                if (candidate.getName().equals(NO_BUTTON)) {
                    candidate = button;
                } else if (button.elapsedSecondsTime() < candidate.elapsedSecondsTime()) {
                    candidate = button;
                }
            }
        }
        return candidate.getName();
    }
}
