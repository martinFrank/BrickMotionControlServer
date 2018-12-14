package de.martin.frank.games.brick.motioncontrol;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static de.martin.frank.games.brick.motioncontrol.GpioUtil.*;

@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        setupGpio();
        SpringApplication.run(App.class, args);
    }

    private static void setupGpio() {
        setupPwm();
        setAsDigitalOutput(RaspiPin.GPIO_02, PinState.LOW);
        setAsDigitalOutput(RaspiPin.GPIO_03, PinState.LOW);
        setAsPwmOutput(RaspiPin.GPIO_23);

        setAsDigitalOutput(RaspiPin.GPIO_04, PinState.LOW);
        setAsDigitalOutput(RaspiPin.GPIO_05, PinState.LOW);
        setAsPwmOutput(RaspiPin.GPIO_26);

    }


}
