package com.terrencemurray.app.utility.observer;

/**
 * Observer interface for the Observer design pattern.
 * Classes implementing this interface can receive notifications from Subject objects.
 * 
 * @author Terrence Murray
 * @version 1.0
 */
public interface Observer {
    /**
     * Called when the observed subject's state changes.
     * @param message notification message about the change
     */
    public void update(String message);
}
