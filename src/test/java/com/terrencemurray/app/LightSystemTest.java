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
        System.out.println("=== Test 1: Light Device On/Off State ===");
        
        // Default state is off
        System.out.println("Initial lamp state: " + lamp.getIsActive());
        Assertions.assertFalse(lamp.getIsActive());

        // Turn on the lamp
        lamp.turnOn();
        System.out.println("Lamp after turnOn(): " + lamp.getIsActive());
        Assertions.assertTrue(lamp.getIsActive());

        // Turn off the lamp
        lamp.turnOff();
        System.out.println("Lamp after turnOff(): " + lamp.getIsActive());
        Assertions.assertFalse(lamp.getIsActive());
        
        // Add devices to hub
        hub.addDevice(lamp);
        System.out.println("Lamp added to hub: " + hub.getDevices().contains(lamp));
        Assertions.assertTrue(hub.getDevices().contains(lamp));
        
        hub.addDevice(bulb);
        System.out.println("Bulb added to hub: " + hub.getDevices().contains(bulb));
        Assertions.assertTrue(hub.getDevices().contains(bulb));

        // Turn on the hub (which turns on all devices)
        hub.turnOn();
        System.out.println("After hub.turnOn() - Lamp: " + lamp.getIsActive() + ", Bulb: " + bulb.getIsActive());
        Assertions.assertTrue(lamp.getIsActive());
        Assertions.assertTrue(bulb.getIsActive());
        
        System.out.println("✅ Test 1 PASSED: Device on/off state updates correctly\n");
    }

    // 2. Setting brightness updates the brightness level.
    @Test
    public void testAdjustBrightness() {
        System.out.println("=== Test 2: Brightness Level Adjustment ===");
        
        // Test default brightness
        System.out.println("Default lamp brightness: " + lamp.getBrightness());
        Assertions.assertEquals(0.5f, lamp.getBrightness());   

        // Update brightness 
        lamp.adjustBrightness(0.8f);
        System.out.println("Lamp brightness after setting to 0.8: " + lamp.getBrightness());
        Assertions.assertEquals(0.8f, lamp.getBrightness());

        // Test invalid brightness levels
        boolean resultLow = lamp.adjustBrightness(-0.1f);
        System.out.println("Setting invalid brightness (-0.1) result: " + resultLow);
        System.out.println("Lamp brightness after invalid setting: " + lamp.getBrightness());
        Assertions.assertFalse(resultLow);
        Assertions.assertEquals(0.8f, lamp.getBrightness()); // Should remain unchanged

        // Test hub brightness adjustment
        hub.addDevice(lamp);
        hub.addDevice(bulb);

        hub.adjustBrightness(0.6f);
        System.out.println("After hub brightness adjustment to 0.6:");
        System.out.println("  Lamp brightness: " + lamp.getBrightness());
        System.out.println("  Bulb brightness: " + bulb.getBrightness());
        Assertions.assertEquals(0.6f, lamp.getBrightness());
        Assertions.assertEquals(0.6f, bulb.getBrightness());
        
        System.out.println("✅ Test 2 PASSED: Brightness adjustment works correctly\n");
    }

    // 3. Undoing a command restores the previous state.
    @Test
    public void testCommandUndo() {
        System.out.println("=== Test 3: Command Undo Functionality ===");
        
        lamp.turnOff(); // Ensure lamp is off initially
        System.out.println("Initial lamp state: " + lamp.getIsActive());

        // Create a command to turn the lamp on
        Command turnOnCommand = new TurnOnCommand(lamp, lamp.getIsActive());
        turnOnCommand.execute();
        System.out.println("After executing turnOn command: " + lamp.getIsActive());

        // Verify the lamp is on
        Assertions.assertTrue(lamp.getIsActive());

        // Undo the command
        turnOnCommand.undo();
        System.out.println("After undoing turnOn command: " + lamp.getIsActive());

        // Verify the lamp is off
        Assertions.assertFalse(lamp.getIsActive());

        // Test hub command undo
        hub.addDevice(lamp);
        hub.addDevice(bulb);    
        System.out.println("Added devices to hub");

        // Create a command to turn the hub on
        Command turnOnHubCommand = new TurnOnCommand(hub);
        turnOnHubCommand.execute();
        System.out.println("After executing hub turnOn - Lamp: " + lamp.getIsActive() + ", Bulb: " + bulb.getIsActive());
        // Verify both devices are on
        Assertions.assertTrue(lamp.getIsActive());
        Assertions.assertTrue(bulb.getIsActive());
        
        // Undo the hub command
        turnOnHubCommand.undo();
        System.out.println("After undoing hub turnOn - Lamp: " + lamp.getIsActive() + ", Bulb: " + bulb.getIsActive());
        // Verify both devices are off
        Assertions.assertFalse(lamp.getIsActive());
        Assertions.assertFalse(bulb.getIsActive());
        
        System.out.println("✅ Test 3 PASSED: Command undo restores previous state correctly\n");
    }

    // 4. Observer notifications are triggered when a change occurs.
    @Test
    public void testObserverNotifications() {
        System.out.println("=== Test 4: Observer Notification System ===");
        
        final String expectedString = "The brightness of " + this.bulb.getSerialNumber() + " was adjusted to " + (0.6f * 100) + "%";
        System.out.println("Expected notification message: " + expectedString);
        
        // Create an observer
        final User testUser = new User("Test User");
        System.out.println("Created observer user: " + testUser.getName());
        
        // Attach a subject to the observer
        this.bulb.attach(testUser);
        System.out.println("Attached bulb (" + bulb.getSerialNumber() + ") to user");

        // Trigger a notification
        this.bulb.adjustBrightness(0.6f);
        System.out.println("Triggered brightness adjustment on bulb");

        // Verify that the notifications
        String actualNotification = testUser.getDisplay().getStatusCache().get(0);
        System.out.println("Actual notification received: " + actualNotification);
        
        Assertions.assertEquals(expectedString, actualNotification);
        
        // Test multiple notifications
        lamp.attach(testUser);
        lamp.turnOn();
        System.out.println("Additional notification from lamp: " + testUser.getDisplay().getStatusCache().get(1));
        
        // Verify observer received both notifications
        Assertions.assertEquals(2, testUser.getDisplay().getStatusCache().size());
        
        System.out.println("✅ Test 4 PASSED: Observer notifications work correctly\n");
    }
}
