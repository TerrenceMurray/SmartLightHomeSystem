package com.terrencemurray.app;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.terrencemurray.app.lights.LightDevice;
import com.terrencemurray.app.lights.LightDeviceHub;
import com.terrencemurray.app.lights.devices.Lamp;

public class LightSystemTest {
    // 1. Turning a device on/off updates its state correctly
    @Test
    public void testLightDeviceOnOff() {
        LightDevice lamp = new Lamp();
        
        // Default state is off
        Assertions.assertFalse(lamp.getIsActive());

        // Turn on the lamp
        lamp.turnOn();
        Assertions.assertTrue(lamp.getIsActive());

        // Turn off the lamp
        lamp.turnOff();
        Assertions.assertFalse(lamp.getIsActive());

        LightDeviceHub hub = new LightDeviceHub("Test Hub");
        
        // Add device to hub
        hub.addDevice(lamp);
        Assertions.assertTrue(hub.getDevices().contains(lamp));
        
        // Add device to hub
        LightDevice lamp2 = new Lamp();
        hub.addDevice(lamp2);
        Assertions.assertTrue(hub.getDevices().contains(lamp2));

        // Turn on the hub (which turns on the lamp)
        hub.turnOn();
        Assertions.assertTrue(lamp.getIsActive());
        Assertions.assertTrue(lamp2.getIsActive());
    }
}
