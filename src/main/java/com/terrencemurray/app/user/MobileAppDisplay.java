package com.terrencemurray.app.user;

import java.util.ArrayList;

public class MobileAppDisplay {
    private ArrayList<String> statusCache;

    public MobileAppDisplay() {
        this.statusCache = new ArrayList<>();
    }

    public void updateStatus(String status) {
        this.statusCache.add(status);
    }

    public void displayStatus() {
        for (String status : statusCache) {
            System.out.println("[" + java.time.LocalDateTime.now() + "] " + status);
        }
    }
}
