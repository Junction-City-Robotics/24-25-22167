package org.firstinspires.ftc.teamcode.control.systems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

@Config
public class Sensors {
    NormalizedColorSensor colorSensor;

    /**
     * Config Variables
     */
    public static int GAIN = 2;

    public Sensors(String colorSensor, HardwareMap hMap) {
        this.colorSensor = hMap.get(NormalizedColorSensor.class, colorSensor);
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
        ((SwitchableLight)colorSensor).enableLight(false);
    }
}
