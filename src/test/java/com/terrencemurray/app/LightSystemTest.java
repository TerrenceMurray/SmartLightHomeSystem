package com.terrencemurray.app;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.terrencemurray.app.commands.*;
import com.terrencemurray.app.lights.LightDevice;
import com.terrencemurray.app.lights.LightDeviceHub;
import com.terrencemurray.app.lights.devices.Bulb;
import com.terrencemurray.app.lights.devices.Lamp;
import com.terrencemurray.app.user.User;

public class LightSystemTest {
    private LightDevice lamp;
    private LightDevice bulb;
    private LightDeviceHub hub;

    @BeforeEach
    public void setUp() {
        lamp = new Lamp();
        bulb = new Bulb();
        hub = new LightDeviceHub("Test Hub");
    }

    // 1. Turning a device on/off updates its state correctly
    @Test
    public void testLightDeviceOnOff() {
        // Default state is off
        Assertions.assertFalse(lamp.getIsActive());

        // Turn on the lamp
        lamp.turnOn();
        Assertions.assertTrue(lamp.getIsActive());

        // Turn off the lamp
        lamp.turnOff();
        Assertions.assertFalse(lamp.getIsActive());
        
        // Add devices to hub
        hub.addDevice(lamp);
        Assertions.assertTrue(hub.getDevices().contains(lamp));
        
        hub.addDevice(bulb);
        Assertions.assertTrue(hub.getDevices().contains(bulb));

        // Turn on the hub (which turns on all devices)
        hub.turnOn();
        Assertions.assertTrue(lamp.getIsActive());
        Assertions.assertTrue(bulb.getIsActive());
    }

    // 2. Setting brightness updates the brightness level.
    @Test
    public void testAdjustBrightness() {
        // Test default brightness
        Assertions.assertEquals(0.5f, lamp.getBrightness());   

        // Update brightness 
        lamp.adjustBrightness(0.8f);
        Assertions.assertEquals(0.8f, lamp.getBrightness());

        // Test invalid brightness levels
        boolean resultLow = lamp.adjustBrightness(-0.1f);
        Assertions.assertFalse(resultLow);
        Assertions.assertEquals(0.8f, lamp.getBrightness()); // Should remain unchanged

        // Test hub brightness adjustment
        hub.addDevice(lamp);
        hub.addDevice(bulb);

        hub.adjustBrightness(0.6f);
        Assertions.assertEquals(0.6f, lamp.getBrightness());
        Assertions.assertEquals(0.6f, bulb.getBrightness());
    }

    // 3. Undoing a command restores the previous state.
    @Test
    public void testCommandUndo() {
        lamp.turnOff(); // Ensure lamp is off initially

        // Create a command to turn the lamp on
        Command turnOnCommand = new TurnOnCommand(lamp, lamp.getIsActive());
        turnOnCommand.execute();

        // Verify the lamp is on
        Assertions.assertTrue(lamp.getIsActive());

        // Undo the command
        turnOnCommand.undo();

        // Verify the lamp is off
        Assertions.assertFalse(lamp.getIsActive());

        // Test hub command undo
        hub.addDevice(lamp);
        hub.addDevice(bulb);    

        // Create a command to turn the hub on
        Command turnOnHubCommand = new TurnOnCommand(hub);
        turnOnHubCommand.execute();
        // Verify both devices are on
        Assertions.assertTrue(lamp.getIsActive());
        Assertions.assertTrue(bulb.getIsActive());
        // Undo the hub command
        turnOnHubCommand.undo();
        // Verify both devices are off
        Assertions.assertFalse(lamp.getIsActive());
        Assertions.assertFalse(bulb.getIsActive());
    }

    // 4. Observer notifications are triggered when a change occurs.
    @Test
    public void testObserverNotifications() {
        final String expectedString = "The brightness of " + this.bulb.getSerialNumber() + " was adjusted to " + (0.6f * 100) + "%";
        
        // Create an observer
        final User testUser = new User("Test User");
        
        // Attach a subject to the observer
        this.bulb.attach(testUser);

        // Trigger a notification
        this.bulb.adjustBrightness(0.6f);

        // Verify that the notifications
        Assertions.assertEquals(expectedString, testUser.getDisplay().getStatusCache().get(0));
    }
}
