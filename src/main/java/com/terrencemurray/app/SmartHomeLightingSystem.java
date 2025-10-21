package com.terrencemurray.app;

import com.terrencemurray.app.commands.*;
import com.terrencemurray.app.lights.LightComponent;
import com.terrencemurray.app.lights.LightDeviceHub;
import com.terrencemurray.app.lights.devices.Bulb;
import com.terrencemurray.app.lights.devices.Lamp;
import com.terrencemurray.app.lights.hubs.Room;
import com.terrencemurray.app.user.User;

public class SmartHomeLightingSystem {
    
    public static void main(String[] args) {
        System.out.println("=== Smart Home Lighting System ===\n");
        
        // Create users
        final User alice = new User("Alice");
        final User bob = new User("Bob");

        // Create individual devices with descriptive labels
        final Bulb bedroomBulb = new Bulb();
        final Lamp bedroomLamp = new Lamp();
        final Bulb livingRoomBulb = new Bulb();
        final Lamp livingRoomLamp = new Lamp();

        // Create groups
        final LightDeviceHub bedroomHub = new Room("Bedroom");
        bedroomHub.addDevice(bedroomBulb);
        bedroomHub.addDevice(bedroomLamp);

        final LightDeviceHub livingRoomHub = new Room("Living Room");
        livingRoomHub.addDevice(livingRoomBulb);
        livingRoomHub.addDevice(livingRoomLamp);

        // Attach all users as observers to all devices
        // (Both users should see all notifications)
        attachAllUsers(bedroomBulb, alice, bob);
        attachAllUsers(bedroomLamp, alice, bob);
        attachAllUsers(bedroomHub, alice, bob);
        attachAllUsers(livingRoomBulb, alice, bob);
        attachAllUsers(livingRoomLamp, alice, bob);
        attachAllUsers(livingRoomHub, alice, bob);

        System.out.println("--- Executing Commands ---\n");

        // Execute commands (5+ commands including undo)
        alice.clickCommandButton(new TurnOnCommand(bedroomBulb));
        
        bob.clickCommandButton(new TurnOnCommand(livingRoomLamp));
        bob.clickUndoButton();  // Undo the lamp turn on
        
        alice.clickCommandButton(new TurnOffCommand(bedroomHub));  // Turn off entire bedroom
        
        bob.clickCommandButton(new SetBrightnessCommand(livingRoomHub, 80.0f));
        
        alice.clickCommandButton(new TurnOnCommand(bedroomLamp));

        // Display results
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMMAND HISTORY");
        System.out.println("=".repeat(60) + "\n");
        
        System.out.println("Alice's Commands:");
        System.out.println(alice.getCommandLog());
        
        System.out.println("\nBob's Commands:");
        System.out.println(bob.getCommandLog());
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("USER NOTIFICATIONS");
        System.out.println("=".repeat(60) + "\n");
        
        alice.getDisplay().display();
        System.out.println();
        bob.getDisplay().display();
    }
    
    /**
     * Helper method to attach multiple users to a light component
     */
    private static void attachAllUsers(LightComponent device, User... users) {
        for (User user : users) {
            device.attach(user);
        }
    }
}