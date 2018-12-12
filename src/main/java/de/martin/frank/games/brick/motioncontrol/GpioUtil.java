package de.martin.frank.games.brick.motioncontrol;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

import java.util.HashMap;
import java.util.Map;

public class GpioUtil {

    private static final int PWM_RANGE = 100;
    private static Map<Pin, GpioPinDigitalOutput> outputPins = new HashMap<>();
    private static Map<Pin, GpioPinPwmOutput> pwmPins = new HashMap<>();

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

    public static void setupPwm() {
        //Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        Gpio.pwmSetMode(Gpio.PWM_MODE_BAL);
        Gpio.pwmSetRange(PWM_RANGE);
        //Gpio.pwmSetClock(500);
    }

    public static boolean setAsPwmOutput(Pin pin) {
        try {
            GpioPinPwmOutput pwm = GpioFactory.getInstance().provisionPwmOutputPin(pin);
            pwm.setPwmRange(PWM_RANGE);
            pwmPins.put(pin, pwm);
            return true;

            // set the PWM rate to 500
            //pwm.setPwm(500);

        } catch (Exception e) {
            return false;
        }
    }

    public static GpioPinPwmOutput getPwmOutput(Pin pin) {
        return pwmPins.get(pin);
    }
}
