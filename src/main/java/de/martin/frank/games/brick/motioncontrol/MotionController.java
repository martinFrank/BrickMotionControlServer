package de.martin.frank.games.brick.motioncontrol;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MotionController {

    private static final String SUCCESS = "set motion control pwma=%s pwmb=%s succeeded";
    private static final String FAIL = "set motion control failed: %s";
    private final AtomicLong counter = new AtomicLong();

    //http://localhost:8080/motion?pwma=33&pwmb=23

    @RequestMapping("/motion")
    public MotionResponse motion(@RequestParam(value = "pwma", defaultValue = "0") String pwma,
                                 @RequestParam(value = "pwmb", defaultValue = "0") String pwmb) {

        System.out.printf("pwma / pwmb : " + pwma + " / " + pwmb);
        System.out.println();

        try{
            Integer a = Integer.parseInt(pwma);
            Integer b = Integer.parseInt(pwmb);
            setPwm(a, RaspiPin.GPIO_01, RaspiPin.GPIO_02, RaspiPin.GPIO_26);

            return new MotionResponse(counter.incrementAndGet(),
                    String.format(SUCCESS, Integer.toString(a), Integer.toString(b)));
        }catch (IllegalArgumentException e){
            return new MotionResponse(counter.incrementAndGet(),
                    String.format(FAIL, e.getMessage()));
        }
    }

    private void setPwm(Integer value, Pin leftPin, Pin rightPin, Pin pwmPin) {
        GpioPinDigitalOutput pin1 = GpioUtil.getDigitalOutput(leftPin);
        GpioPinDigitalOutput pin2 = GpioUtil.getDigitalOutput(rightPin);
        if (value > 0) {
            pin1.high();
            pin2.low();
        } else if (value == 0) {
            pin1.low();
            pin2.low();
        } else if (value < 0) {
            pin1.low();
            pin2.high();
        }
        GpioPinPwmOutput pin26 = GpioUtil.getPwmOutput(pwmPin);
        int aAbs = Math.abs(value);
        pin26.setPwmRange(aAbs);
    }

}
