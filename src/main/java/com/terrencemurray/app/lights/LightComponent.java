package com.terrencemurray.app.lights;

public interface LightComponent {
    public Boolean turnOn();
    public Boolean turnOff();
    public Boolean adjustBrightness(float level); // between 0 and 1
}