package org.firstinspires.ftc.teamcode.control.patterns;

import com.qualcomm.robotcore.util.ElapsedTime;

public class PatternEvent {
    private final Runnable event;
    private final double time;

    private boolean ranYet = false;

    public PatternEvent(double time, Runnable event) {
        this.event = event;
        this.time = time;
    }

    public boolean pastTime(ElapsedTime elapsedTime) {
        return elapsedTime.seconds() >= time;
    }

    public boolean hasRun() {
        return ranYet;
    }

    public void runEvent() {
        ranYet = true;
        event.run();
    }

    public double getTime() {
        return time;
    }

    public Runnable getEvent() {
        return event;
    }
}