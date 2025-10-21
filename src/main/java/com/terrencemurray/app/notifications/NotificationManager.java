package com.terrencemurray.app.notifications;
// 816038951

import java.util.ArrayList;

public class NotificationManager {
    private static NotificationManager instance;
    private ArrayList<String> notifications;

    public ArrayList<String> getNotifications() { return this.notifications; }

    private NotificationManager () {
        notifications = new ArrayList<>();
    }

    public static NotificationManager Instance () {
        if (instance == null)
            instance = new NotificationManager();
        
        return instance;
    }

    public void pushNotification(String message) {
        this.notifications.add(message);
    }
}
