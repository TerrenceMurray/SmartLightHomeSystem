package com.terrencemurray.app.commands;

import java.time.Instant;

import com.terrencemurray.app.lights.LightComponent;
import com.terrencemurray.app.user.User;

public abstract class Command {
    protected LightComponent light;
    
    protected Object from;
    protected Object to;

    protected Instant executedAt;
    protected Instant undoneAt;

    protected User executedBy;

    public User getExecutedBy() { return this.executedBy; }
    public Instant getExecutedAt() { return this.executedAt; }
    public Instant getUndoneAt() { return this.undoneAt; }

    public Command(LightComponent light) {
        this.light = light;
    }

    protected abstract void doExecute();
    protected abstract void doUndo();

    public void execute() {
        this.executedAt = Instant.now();
        this.doExecute();
    };

    public void undo() {
        this.undoneAt = Instant.now();
        this.doUndo();
    }
    
    public Command by(User user) {
        this.executedBy = user;
        return this;
    }
}