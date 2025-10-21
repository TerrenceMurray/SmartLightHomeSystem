package com.terrencemurray.app.utility.observer;

/**
 * Subject interface for the Observer design pattern.
 * Classes implementing this interface can have observers and notify them of changes.
 * 
 * @author Terrence Murray
 * @version 1.0
 */
public interface Subject {
    /**
     * Attaches an observer to this subject.
     * @param o the observer to attach
     */
    public void attach(Observer o);
    
    /**
     * Detaches an observer from this subject.
     * @param o the observer to detach
     */
    public void detach(Observer o);
    
    /**
     * Notifies all attached observers with a message.
     * @param message the notification message to send
     */
    public void notifyObservers(String message);
}
