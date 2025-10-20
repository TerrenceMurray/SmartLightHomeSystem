package com.terrencemurray.app.utility.observer;

public interface Subject {
    public void attach(Observer o);
    public void detach(Observer o);
    public void notifyObservers(String message);
}
