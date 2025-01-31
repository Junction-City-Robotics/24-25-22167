package org.firstinspires.ftc.teamcode.miscellaneous.input;

import com.qualcomm.robotcore.util.ElapsedTime;

public class ButtonManager {
    private final String name;
    private boolean clicked = false;
    private final ElapsedTime time = new ElapsedTime();

    public ButtonManager(String name, boolean start) {
        this.name = name;

        if (start) {
            startTimer();
        }
    }

    // Basic name getter
    public String getName() {
        return name;
    }

    // Is clicked functions
    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    // Timer functions
    public void startTimer() {
        time.startTime();
    }

    public void resetTimer() {
        time.reset();
    }

    public double elapsedSecondsTime() {
        return time.seconds();
    }
}
