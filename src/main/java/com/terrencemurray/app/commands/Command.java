package com.terrencemurray.app.commands;

import java.time.Instant;

import com.terrencemurray.app.lights.LightComponent;
import com.terrencemurray.app.user.User;

public abstract class Command {
    protected LightComponent light;
    
    protected Object from;
    protected Object to;

    protected Instant executedAt;
    protected User executedBy;

    public User getExecutedBy() { return this.executedBy; }
    public Instant getExecutedAt() { return this.executedAt; }

    public Command(LightComponent light) {
        this.light = light;
    }

    protected abstract void doExecute();
    protected abstract void doUndo();

    public void execute() {
        this.record();
        this.doExecute();
    };

    public void undo() {
        this.record();
        this.doUndo();
    }

    private void record() {
        this.executedAt = Instant.now();
    }
    
    public Command by(User user) {
        this.executedBy = user;
        return this;
    }
}