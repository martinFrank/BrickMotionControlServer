package de.martin.frank.games.brick.motioncontrol;

import com.pi4j.io.gpio.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);

        setupGpio();
    }

    private static void setupGpio() {

        // create gpio controller instance
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pins #04 as an output pin and make sure is is set to LOW at startup
        GpioPinDigitalOutput myLed = gpio.provisionDigitalOutputPin(
                RaspiPin.GPIO_04,   // PIN NUMBER
                "My LED",           // PIN FRIENDLY NAME (optional)
                PinState.LOW);      // PIN STARTUP STATE (optional)

        RaspiConfig.getSingleton().setGpioPinDigitalOutput(myLed, RaspiPin.GPIO_04);
    }
}
