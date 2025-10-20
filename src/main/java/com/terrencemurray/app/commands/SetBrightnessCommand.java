package com.terrencemurray.app.commands;

import com.terrencemurray.app.lights.LightComponent;

public final class SetBrightnessCommand extends Command {

    public SetBrightnessCommand(LightComponent light, Float to) {
        super(light);
        this.to = to;
    }

    public SetBrightnessCommand(LightComponent light, Float from, Float to) {
        super(light);
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute() {
        light.adjustBrightness(((Float) to).floatValue());
    }

    @Override
    public void undo() {
        light.adjustBrightness(((Float) from).floatValue());
    }

    public String toString() {
        return "Set brightness to " + (((Float) to) * 100) + "% on " + light.getClass().getSimpleName();
    }
}
