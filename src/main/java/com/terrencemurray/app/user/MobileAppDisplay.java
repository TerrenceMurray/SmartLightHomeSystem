package com.terrencemurray.app.user;
// 816038951

import java.util.ArrayList;

import com.terrencemurray.app.notifications.NotificationManager;

public class MobileAppDisplay {
    private NotificationManager notificationManager;
    private String username;

    public ArrayList<String> getNotifications() { return this.notificationManager.getNotifications(); }

    public MobileAppDisplay(String name) {
        this.notificationManager = NotificationManager.Instance();
        this.username = name;
    }

    public void display() {
        System.out.println("Welcome back, " + this.username);
        for (String status : this.notificationManager.getNotifications()) {
            System.out.println("[" + java.time.LocalDateTime.now() + "] " + status);
        }
    }
}
