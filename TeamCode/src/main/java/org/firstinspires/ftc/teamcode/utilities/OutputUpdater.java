package org.firstinspires.ftc.teamcode.utilities;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OutputUpdater {
    private HashMap<String, String> consoleMessages = new HashMap<String, String>();
    private List<String> spokenMessages = new ArrayList<>();

    private final ElapsedTime runtime = new ElapsedTime();

    private Telemetry telemetry;

    public OutputUpdater(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    // Adders
    public void AddConsoleOutput(String type, String message) {
        consoleMessages.put(type, message);
    }
    public void AddSpeakOutput(String message) {
        spokenMessages.add(message);
    }

    // Function to fully update everything
    public void Update() {
        for (String type : consoleMessages.keySet()) {
            telemetry.addData(type, consoleMessages.get(type));
        }
        consoleMessages.clear();

        for (String message : spokenMessages) {
            telemetry.speak(message);
        }
        spokenMessages.clear();

        telemetry.update();
    }
}
