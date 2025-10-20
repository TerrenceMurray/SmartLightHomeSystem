package com.terrencemurray.app.lights;

public interface LightComponent {
    public boolean turnOn();
    public boolean turnOff();
    public boolean adjustBrightness(float level); // between 0 and 1
}