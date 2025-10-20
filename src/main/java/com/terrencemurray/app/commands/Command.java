package com.terrencemurray.app.commands;

import com.terrencemurray.app.lights.LightComponent;

public abstract class Command {
    protected LightComponent light;
    protected Object from;
    protected Object to;

    public Command(LightComponent light) {
        this.light = light;
    }

    public abstract void execute();
    public abstract void undo();
}