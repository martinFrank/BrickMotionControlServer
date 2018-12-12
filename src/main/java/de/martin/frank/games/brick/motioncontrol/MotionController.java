package de.martin.frank.games.brick.motioncontrol;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
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
    public MotionReply motion(@RequestParam(value = "pwma", defaultValue = "0") String pwma,
                              @RequestParam(value = "pwmb", defaultValue = "0") String pwmb) {

        System.out.printf("pwma / pwmb : " + pwma + " / " + pwmb);
        System.out.println();

        try{
            Integer a = Integer.parseInt(pwma);
            Integer b = Integer.parseInt(pwmb);

            GpioPinDigitalOutput myLed = RaspiConfig.getSingleton().getDigitalOutputPin(RaspiPin.GPIO_04);

            return new MotionReply(counter.incrementAndGet(),
                    String.format(SUCCESS, Integer.toString(a), Integer.toString(b)));
        }catch (IllegalArgumentException e){
            return new MotionReply(counter.incrementAndGet(),
                    String.format(FAIL, e.getMessage()));
        }
    }

}
