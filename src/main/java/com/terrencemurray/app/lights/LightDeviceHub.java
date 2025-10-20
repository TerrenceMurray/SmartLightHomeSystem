package com.terrencemurray.app.lights;

import java.util.HashSet;

import com.terrencemurray.app.utility.observer.Observer;

import java.util.ArrayList;

public class LightDeviceHub implements LightComponent {
    private HashSet<LightComponent> devices;
    private String label;
    private ArrayList<Observer> observers;

    public String getLabel() { return this.label; }
    public ArrayList<Observer> getObservers() { return this.observers; }
    public HashSet<LightComponent> getDevices() { return this.devices; }

    public LightDeviceHub (String label) {
        this.devices = new HashSet<>();
        this.observers = new ArrayList<>();

        this.label = label;
    }

    public boolean addDevice(LightComponent device) {
        notifyObservers(device.getClass().getSimpleName() + " on " + this.label + " was added");
        return this.devices.add(device);
    }

    public boolean removeDevice(LightComponent device) {
        notifyObservers(device.getClass().getSimpleName() + " on " + this.label + " was removed");
        return this.devices.remove(device);
    }

    @Override
    public Boolean turnOn() {
        for (LightComponent device : devices) {
            if (!device.turnOn()) {
                notifyObservers(device.getClass().getSimpleName() + " on " + this.label + " failed to turn on");
                return Boolean.FALSE;
            }
        }

        notifyObservers("All devices on " + this.label + " were turned on");
        return Boolean.TRUE;
    }

    @Override
    public Boolean turnOff() {
        for (LightComponent device : devices) {
            if (!device.turnOff()) {
                notifyObservers(device.getClass().getSimpleName() + " on " + this.label + " failed to turn off");
                return Boolean.FALSE;
            }
        }

        notifyObservers("All devices on " + this.label + " were turned off");
        return Boolean.TRUE;
    }
    
    @Override
    public Boolean adjustBrightness(float level) {
        for (LightComponent device : devices) {
            if (!device.adjustBrightness(level)) {
                notifyObservers("The brightness of " + device.getClass().getSimpleName() + " on " + this.label + " failed to adjust to " + (level * 100) + "%");
                return Boolean.FALSE;
            }
        }
        
        notifyObservers("The brightness of devices on " + this.label + " was adjusted to " + (level * 100) + "%");
        return Boolean.TRUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LightDeviceHub that = (LightDeviceHub) o;
        return this.label.equals(that.label);
    }

    @Override
    public int hashCode() {
        return this.label.hashCode();
    }

    @Override
    public void attach(Observer o) {
        if (o != null && !this.observers.contains(o)) {
            this.observers.add(o);
        }
    }
    
    @Override
    public void detach(Observer o) {
        this.observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : this.observers) {
            observer.update(message);
        }
    }
}

