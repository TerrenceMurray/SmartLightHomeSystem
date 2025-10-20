package com.terrencemurray.app.commands;

import com.terrencemurray.app.lights.LightComponent;

public class TurnOnCommand extends Command {

    public TurnOnCommand(LightComponent light) {
        super(light);
    }
    public TurnOnCommand(LightComponent light, Boolean from) {
        super(light);
        this.from = from;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        // If it's a hub, just reverse the operation on the entire hub
        if (from instanceof Boolean && ((Boolean) from).booleanValue()) {
            light.turnOff();
        } else {
            light.turnOn();
        }
    }

    public String toString() {
        return "Turn on light of " + light.getClass().getSimpleName();
    }    
}
