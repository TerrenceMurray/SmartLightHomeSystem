package com.terrencemurray.app.lights;

import com.terrencemurray.app.utility.SetItem;
import com.terrencemurray.app.utility.observer.Subject;

public interface LightComponent extends Subject, SetItem {
    public Boolean turnOn();
    public Boolean turnOff();
    public Boolean adjustBrightness(float level); // between 0 and 1
}