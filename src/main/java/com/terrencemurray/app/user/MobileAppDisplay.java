package com.terrencemurray.app.user;
// 816038951

import java.util.ArrayList;

import com.terrencemurray.app.notifications.NotificationManager;

/**
 * Mobile app display for showing notifications to users.
 * Connects to the NotificationManager to display system messages.
 * 
 * @author Terrence Murray
 * @version 1.0
 */
public class MobileAppDisplay {
    /** Reference to the notification manager */
    private NotificationManager notificationManager;
    
    /** Username for this display */
    private String username;

    /** @return all notifications from the system */
    public ArrayList<String> getNotifications() { return this.notificationManager.getNotifications(); }

    /**
     * Creates a mobile display for the specified user.
     * @param name the username for this display
     */
    public MobileAppDisplay(String name) {
        this.notificationManager = NotificationManager.Instance();
        this.username = name;
    }

    /**
     * Displays welcome message and all notifications to the console.
     */
    public void display() {
        System.out.println("Welcome back, " + this.username);
        for (String status : this.notificationManager.getNotifications()) {
            System.out.println("[" + java.time.LocalDateTime.now() + "] " + status);
        }
    }
}
