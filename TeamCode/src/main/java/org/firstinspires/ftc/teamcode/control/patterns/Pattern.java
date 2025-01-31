package org.firstinspires.ftc.teamcode.control.patterns;

import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class Pattern {
    protected PatternEvent[] sequence;
    protected int index = 0;

    protected final ElapsedTime time = new ElapsedTime();

    public Pattern(double[] timings, Runnable[] functions) {
        this.sequence = new PatternEvent[timings.length];
        for (int i = 0; i < timings.length; i++) {
            PatternEvent e = new PatternEvent(timings[i], functions[i]);

            this.sequence[i] = e;
        }
    }

    public Pattern(PatternEvent[] events) {
        this.sequence = events;
    }

    public void runNext() {
        index++;
        if (!isDone()) {
            sequence[index].runEvent();
        }
    }

    public int getCurrentIndex() {
        return index;
    }

    public boolean isDone() {
        return index >= sequence.length;
    }
}
