package org.firstinspires.ftc.teamcode.miscellaneous.input;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.miscellaneous.input.sections.ClawButtonSection;
import org.firstinspires.ftc.teamcode.miscellaneous.input.sections.DriveButtonSection;
import org.firstinspires.ftc.teamcode.miscellaneous.input.sections.LinkButtonSection;
import org.firstinspires.ftc.teamcode.miscellaneous.input.sections.ViperSlideButtonSection;

import java.util.HashMap;

public class ControllerActionManager {
    // base controllers
    private Gamepad g1;
    private Gamepad g2;

    // Buttons
    private static final String[] DRIVE_TRACKED_BUTTONS = new String[] {
            "leftStick", // Move
            "rightStick", // Turn
            
            "leftStickButton"
    };

    private static final String[] VIPER_SLIDE_BUTTONS = new String[] {
            "a",
            "b",
            "y",
            "x",

            "dpadUp",

            "rightStickButton"
    };


    private static final String[] CLAW_TRACKED_BUTTONS = new String[] {
            "y",
            "a",

            "dpadUp",
            "dpadDown",

            "dpadLeft",
            "dpadRight",

            "x",
            "b",

            "leftBumper",
            "rightBumper",

            "rightTrigger",
            "leftTrigger",

            "leftStick",
            "rightStick",
    };

    private static final String[] LINK_TRACKED_BUTTONS = new String[] {
            "rightTrigger", // Link Out
            "leftTrigger", // Link In
    };

    // Sections
    private final HashMap<String, TrackedButtonSection> sections = new HashMap<>();

    public ControllerActionManager(Gamepad g1, Gamepad g2) {
        this.g1 = g1;
        this.g2 = g2;

        createSections();
    }

    private void createSections() {
        // Controller 1 (Driving)
        sections.put("drive", new DriveButtonSection("drive", DRIVE_TRACKED_BUTTONS));

        // Controller 2 (Systems)
        sections.put("claw", new ClawButtonSection("drive", CLAW_TRACKED_BUTTONS));
        sections.put("link", new LinkButtonSection("drive", LINK_TRACKED_BUTTONS));
        sections.put("viperSlide", new ViperSlideButtonSection("drive", VIPER_SLIDE_BUTTONS));
    }

    public void updateState(Gamepad newG1, Gamepad newG2) {
        this.g1 = newG1;
        this.g2 = newG2;

        sections.forEach((sectionName, section) -> {
            assert section != null;
            section.update(g1, g2);
        });
    }

    public String getMostRecentButtonInSection(String sectionName) {
        TrackedButtonSection section = sections.get(sectionName);

        assert section != null;

        return section.mostRecentValidClick();
    }
}
