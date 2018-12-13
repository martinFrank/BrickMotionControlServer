package de.martin.frank.games.brick.motioncontrol;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

import static de.martin.frank.games.brick.motioncontrol.GpioUtil.PWM_RANGE_PERCENT_FACTOR;

@RestController
public class MotionController {

    private static final String SUCCESS_MESSAGE = "set motion control pwma=%s pwmb=%s succeeded";
    private static final String FAIL_MESSAGE = "set motion control failed: %s";
    private static final String NOT_IN_RANGE_MESSAGE = "%s:%d is not in range [%d,%d]";
    private static final int PERCENTAGE_MIN = -100;
    private static final int PERCENTAGE_MAX = 100;
    private static final String PWMA = "pwma";
    private static final String PWMB = "pwmb";
    private final AtomicLong counter = new AtomicLong();

    //http://localhost:8080/motion?pwma=33&pwmb=23

    @RequestMapping("/motion")
    public MotionResponse motion(@RequestParam(value = PWMA, defaultValue = "0") String pwma,
                                 @RequestParam(value = PWMB, defaultValue = "0") String pwmb) {
        System.out.printf("pwma / pwmb : %s/%s%n", pwma, pwmb);
        try {
            int a = validateInput(pwma, PWMA);
            int b = validateInput(pwmb, PWMB);
            setPwm(a, RaspiPin.GPIO_04, RaspiPin.GPIO_05, RaspiPin.GPIO_26);
            setPwm(b, RaspiPin.GPIO_02, RaspiPin.GPIO_03, RaspiPin.GPIO_23);
            return new MotionResponse(counter.incrementAndGet(),
                    String.format(SUCCESS_MESSAGE, Integer.toString(a), Integer.toString(b)));
        } catch (Exception e) {
            return new MotionResponse(counter.incrementAndGet(),
                    String.format(FAIL_MESSAGE, e.getMessage()));
        }
    }

    private int validateInput(String pwma, String name) throws IllegalArgumentException {
        int validInput = Integer.parseInt(pwma);
        if (!isInBounds(PERCENTAGE_MIN, validInput, PERCENTAGE_MAX)) {
            throw new IllegalArgumentException(String.format(NOT_IN_RANGE_MESSAGE,
                    name,
                    validInput,
                    PERCENTAGE_MIN,
                    PERCENTAGE_MAX));
        }
        return validInput;

    }

    private boolean isInBounds(int min, int value, int max) {
        return value >= min && value <= max;
    }

    private void setPwm(Integer value, Pin leftPin, Pin rightPin, Pin pwmPin) {
        GpioPinDigitalOutput left = GpioUtil.getDigitalOutput(leftPin);
        GpioPinDigitalOutput right = GpioUtil.getDigitalOutput(rightPin);
        switch ((int) Math.signum(value)) {
            case 1:
                left.high();
                right.low();
                break;
            case 0:
                left.low();
                right.low();
                break;
            case -1:
                left.low();
                right.high();
                break;
        }
        GpioPinPwmOutput pwm = GpioUtil.getPwmOutput(pwmPin);
        int aAbs = PWM_RANGE_PERCENT_FACTOR * Math.abs(value);
        pwm.setPwm(aAbs);
    }

}
