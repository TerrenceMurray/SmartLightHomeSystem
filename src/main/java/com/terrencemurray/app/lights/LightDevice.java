package com.terrencemurray.app.lights;
// 816038951

import java.util.HashSet;

import com.terrencemurray.app.utility.observer.Observer;


public class LightDevice implements LightComponent {
    private static int id = 1;
    private String serialNumber;
    private Boolean isActive;
    private float brightness;
    private HashSet<Observer> observers;

    public String getSerialNumber() { return this.serialNumber; }
    public Boolean getIsActive() { return this.isActive; }
    public float getBrightness() { return this.brightness; }

    public LightDevice() {
        this.serialNumber = "236623" + id;
        this.isActive = false;
        this.brightness = 0.5f;
        this.observers = new HashSet<>();
        
        id += 1; // Increment id
    }

    @Override
    public Boolean turnOn() {
        this.isActive = Boolean.TRUE;

        notifyObservers("The light " + this.serialNumber + " was turned on");
        return Boolean.TRUE;
    }

    @Override
    public Boolean turnOff() {
        this.isActive = Boolean.FALSE;
        
        notifyObservers("The light " + this.serialNumber + " was turned off");
        return Boolean.TRUE;
    }

    @Override
    public Boolean adjustBrightness(float level) {
        if (level < 0 || level > 1)
            return Boolean.FALSE;

        this.brightness = level;

        notifyObservers("The brightness of " + this.serialNumber + " was adjusted to " + (level * 100) + "%");
        return Boolean.TRUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LightDevice that = (LightDevice) o;
        return this.serialNumber.equals(that.serialNumber);
    }

    @Override
    public int hashCode() {
        return this.serialNumber.hashCode();
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
