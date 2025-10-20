package com.terrencemurray.app.commands;

import com.terrencemurray.app.lights.LightComponent;

public class TurnOffCommand extends Command {

    public TurnOffCommand(LightComponent light) {
        super(light);
    }
    public TurnOffCommand(LightComponent light, Boolean from) {
        super(light);
        this.from = from;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        // If it's a hub, just reverse the operation on the entire hub
        if (from instanceof Boolean && ((Boolean) from).booleanValue()) {
            light.turnOn();
        } else {
            light.turnOff();
        }
    }

    public String toString() {
        return "Turn off light of " + light.getClass().getSimpleName();
    }
}
