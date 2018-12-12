package de.martin.frank.games.brick.motioncontrol;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;

import java.util.HashMap;
import java.util.Map;

public class RaspiConfig {

    private static final RaspiConfig singleton = new RaspiConfig();

    private final Map<Pin, GpioPinDigitalOutput> gpioPinDigitalOutput = new HashMap<>();

    public static RaspiConfig getSingleton() {
        return singleton;
    }


    public GpioPinDigitalOutput getDigitalOutputPin(Pin name) {
        GpioPinDigitalOutput pin = gpioPinDigitalOutput.get(name);
        if (pin == null) {
            throw new IllegalArgumentException("Pin " + name + " is not set");
        }
        return pin;
    }

    public void setGpioPinDigitalOutput(GpioPinDigitalOutput pin, Pin name) {
        if (pin == null || name == null) {
            throw new IllegalArgumentException("Pin " + pin + "(name='" + name + "') is not set");
        }
        GpioPinDigitalOutput alreadySet = gpioPinDigitalOutput.get(name);
        if (alreadySet != null) {
            throw new IllegalArgumentException("Pin " + name + " is already set");
        }
        gpioPinDigitalOutput.put(name, pin);
    }
}
