package com.terrencemurray.app.lights;

import java.util.ArrayList;

import com.terrencemurray.app.utility.SetItem;
import com.terrencemurray.app.utility.observer.Observer;
import com.terrencemurray.app.utility.observer.Subject;

public class LightDevice implements LightComponent, SetItem, Subject {
    private static int id = 1;
    private String serialNumber;
    private boolean isActive;
    private float brightness;
    private ArrayList<Observer> observers;

    public String getSerialNumber() { return this.serialNumber; }
    public boolean getIsActive() { return this.isActive; }
    public float getBrightness() { return this.brightness; }

    public LightDevice() {
        this.serialNumber = "236623" + id;
        this.isActive = false;
        this.brightness = 0.5f;
        this.observers = new ArrayList<>();
        
        id += 1; // Increment id
    }

    @Override
    public boolean turnOn() {
        this.isActive = true;

        notifyObservers("The light " + this.serialNumber + " was turned on");
        return true;
    }

    @Override
    public boolean turnOff() {
        this.isActive = false;
        
        notifyObservers("The light " + this.serialNumber + " was turned off");
        return true;
    }

    @Override
    public boolean adjustBrightness(float level) {
        if (level < 0 || level > 1)
            return false;

        this.brightness = level;

        notifyObservers("The brightness of " + this.serialNumber + " was adjusted to " + (level * 100) + "%");
        return true;
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
