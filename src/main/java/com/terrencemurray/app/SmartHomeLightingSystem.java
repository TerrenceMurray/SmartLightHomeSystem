package com.terrencemurray.app;

import com.terrencemurray.app.commands.*;
import com.terrencemurray.app.lights.LightDeviceHub;
import com.terrencemurray.app.lights.devices.Bulb;
import com.terrencemurray.app.lights.devices.Lamp;
import com.terrencemurray.app.lights.hubs.Room;
import com.terrencemurray.app.user.User;

public class SmartHomeLightingSystem {
    
    public static void main (String[] args) {
        // 2. Create two or more users, each with:
        // - Their own SmartRemoteControl
        // - Their own MobileAppDisplay
        final User alice = new User("Alice");
        final User bob = new User("Bob");

        // 1. Create at least three individual light devices and two groups.
        final Bulb bedroomBulb = new Bulb();
        final Lamp bedroomLamp = new Lamp();
        final LightDeviceHub bedroomHub = new Room("Bedroom Hub");
        bedroomHub.addDevice(bedroomBulb);
        bedroomHub.addDevice(bedroomLamp);

        final Bulb livingRoomBulb = new Bulb();
        final Lamp livingRoomLamp = new Lamp();
        final LightDeviceHub livingRoomHub = new Room("Living Room Hub");
        livingRoomHub.addDevice(livingRoomBulb);
        livingRoomHub.addDevice(livingRoomLamp);

        // 2. Add users as observers to the light devices and hubs
        bedroomBulb.attach(alice);
        bedroomLamp.attach(alice);
        bedroomHub.attach(alice);

        livingRoomBulb.attach(bob);
        livingRoomLamp.attach(bob);
        livingRoomHub.attach(bob);

        // 3. Execute at least five commands, including at least one undo().
        alice.execute(new TurnOnCommand(bedroomBulb, bedroomBulb.getIsActive()));

        bob.execute(new TurnOnCommand(livingRoomLamp, livingRoomLamp.getIsActive()));
        bob.undo();   // Undo: Turn off living room lamp

        alice.execute(new TurnOffCommand(bedroomHub)); // Turn off bedroom hub (both devices)

        bob.execute(new SetBrightnessCommand(livingRoomHub, 0.8f));
        
        alice.execute(new TurnOnCommand(bedroomLamp, bedroomLamp.getIsActive()));

        // 4. Print formatted log output for all users showing:
        
        // - Command history per user
        System.out.println("Alice's Command Log:");
        System.out.println(alice.getCommandLog());
        
        System.out.println("\nBob's Command Log:");
        System.out.println(bob.getCommandLog());
        
        // - Notifications received by all displays
        System.out.println("\nAlice's Display Status:");
        alice.getDisplay().displayStatus();

        System.out.println("\nBob's Display Status:");
        bob.getDisplay().displayStatus();
    }
}
