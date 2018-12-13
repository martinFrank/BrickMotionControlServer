package de.martin.frank.games.brick.motioncontrol;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

import java.util.HashMap;
import java.util.Map;

public class GpioUtil {

    public static final int PWM_RANGE_PERCENT_FACTOR = 1000;
    private static final int PWM_RANGE = 1000;
    private static Map<Pin, GpioPinDigitalOutput> outputPins = new HashMap<>();
    private static Map<Pin, GpioPinPwmOutput> pwmPins = new HashMap<>();

    public static void setupPwm() {
        Gpio.pwmSetMode(Gpio.PWM_MODE_BAL);//Gpio.PWM_MODE_MS;
        Gpio.pwmSetRange(PWM_RANGE);
        Gpio.pwmSetClock(2000);
    }

    public static boolean setAsDigitalOutput(Pin pin, PinState state) {
        try {
            GpioPinDigitalOutput output = GpioFactory.getInstance().provisionDigitalOutputPin(pin, state);
            outputPins.put(pin, output);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static GpioPinDigitalOutput getDigitalOutput(Pin pin) {
        return outputPins.get(pin);
    }

    public static boolean setAsPwmOutput(Pin pin) {
        try {
            GpioPinPwmOutput pwm = GpioFactory.getInstance().provisionPwmOutputPin(pin);
            pwm.setPwmRange(PWM_RANGE);
            pwmPins.put(pin, pwm);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static GpioPinPwmOutput getPwmOutput(Pin pin) {
        return pwmPins.get(pin);
    }
}
