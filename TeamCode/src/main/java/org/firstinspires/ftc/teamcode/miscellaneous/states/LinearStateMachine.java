package org.firstinspires.ftc.teamcode.miscellaneous.states;

public class LinearStateMachine {
    private String currentState;
    private String[] states;

    private int currentIndex = 0;

    public LinearStateMachine(String[] states) {
        this.currentState = states[currentIndex];
        this.states = states;
    }

    public LinearStateMachine(String[] states, int startIndex) {
        this.currentIndex = startIndex;
        this.states = states;
        this.currentState = states[this.currentIndex];
    }

    public boolean nextState() {
        if (currentIndex + 1 < states.length) {
            currentIndex++;
            currentState = states[currentIndex];
            return true;
        }
        return false;
    }

    public boolean previousState() {
        if (currentIndex - 1 >= 0) {
            currentIndex--;
            currentState = states[currentIndex];
            return true;
        }
        return false;
    }

    public void resetState() {
        currentIndex = 0;
        currentState = states[currentIndex];
    }

    public boolean atEnd() {
        return currentIndex == states.length - 1;
    }
}
