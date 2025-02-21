package org.firstinspires.ftc.teamcode.control.systems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
public class Sensors {
    private final NormalizedColorSensor colorSensor;

    private final TouchSensor backTouch;

    /**
     * Config Variables
     */
    public static float GAIN = 200.0f;

    public Sensors(String colorSensor, String backTouch, HardwareMap hMap) {
        this.colorSensor = hMap.get(NormalizedColorSensor.class, colorSensor);
        this.backTouch = hMap.get(TouchSensor.class, backTouch);

        this.colorSensor.setGain(GAIN);
    }

    // Back touch
    public boolean isTouchingBack() {
        return backTouch.isPressed();
    }

    // Gain Controls
    public void setGain(float newGain) {
        this.colorSensor.setGain(newGain);
    }

    // Getting Colors
    public NormalizedRGBA getColors() {
        return colorSensor.getNormalizedColors();
    }

    // Light Commands
    public void turnLightOn() {
        ((SwitchableLight)colorSensor).enableLight(true);
    }

    public void turnLightOff() {
        ((SwitchableLight) colorSensor).enableLight(false);
    }

    // Getting distance in centimeters
    public double getDistance() {
        return ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM);
    }
}
