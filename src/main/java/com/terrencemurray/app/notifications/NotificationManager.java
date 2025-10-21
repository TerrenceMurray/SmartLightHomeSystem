package com.terrencemurray.app.notifications;
// 816038951

import java.util.ArrayList;

/**
 * Manages system-wide notifications using the Singleton Pattern.
 * Stores and provides access to all notification messages.
 * 
 * @author Terrence Murray
 * @version 1.0
 */
public class NotificationManager {
    /** Singleton instance */
    private static NotificationManager instance;
    
    /** List of all notifications */
    private ArrayList<String> notifications;

    /** @return all stored notifications */
    public ArrayList<String> getNotifications() { return this.notifications; }

    /**
     * Private constructor for Singleton pattern.
     */
    private NotificationManager () {
        notifications = new ArrayList<>();
    }

    /**
     * Gets the singleton instance of NotificationManager.
     * @return the single NotificationManager instance
     */
    public static NotificationManager Instance () {
        if (instance == null)
            instance = new NotificationManager();
        
        return instance;
    }

    /**
     * Adds a new notification message.
     * @param message the notification message to add
     */
    public void pushNotification(String message) {
        this.notifications.add(message);
    }
}
