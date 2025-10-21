package com.terrencemurray.app.lights;
// 816038951

import com.terrencemurray.app.utility.SetItem;
import com.terrencemurray.app.utility.observer.Subject;

/**
 * Interface for all light components in the smart home lighting system.
 * 
 * Defines basic operations for individual devices and hubs using the Composite Pattern.
 * Supports observer notifications and collection storage.
 * 
 * @author Terrence Murray
 * @version 1.0
 */
public interface LightComponent extends Subject, SetItem {
    
    /**
     * Turns on this light component.
     * @return true if successful, false otherwise
     */
    public Boolean turnOn();
    
    /**
     * Turns off this light component.
     * @return true if successful, false otherwise
     */
    public Boolean turnOff();
    
    /**
     * Adjusts the brightness level of this light component.
     * @param level brightness level between 0.0 and 1.0
     * @return true if successful, false if level is invalid
     */
    public Boolean adjustBrightness(float level);
}