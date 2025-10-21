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
    protected void doExecute() {
        light.turnOn();
    }

    @Override
    protected void doUndo() {
        // Restore the previous state
        if (from instanceof Boolean) {
            if (((Boolean) from).booleanValue()) {
                light.turnOn();  // Restore to on state
            } else {
                light.turnOff(); // Restore to off state
            }
        } else {
            // If no previous state recorded, reverse the turn on operation
            light.turnOff();
        }
    }

    public String toString() {
        String actor = this.executedBy != null ? this.executedBy.getName() : "None";
        return "[" + this.executedAt.toString() +  "] Turn on light of " + light.getClass().getSimpleName() + " (Actor " + actor + ")";
    }    
}
