package com.terrencemurray.app.commands;
// 816038951

import java.time.Instant;

import com.terrencemurray.app.lights.LightComponent;
import com.terrencemurray.app.user.User;

/**
 * Abstract base class for all light control commands using the Command Pattern.
 * Encapsulates operations on light components with undo support and execution tracking.
 * 
 * @author Terrence Murray
 * @version 1.0
 */
public abstract class Command {
    /** The light component this command operates on */
    protected LightComponent light;
    
    /** Previous state for undo operations */
    protected Object from;
    
    /** Target state for the command */
    protected Object to;

    /** When the command was executed */
    protected Instant executedAt;
    
    /** When the command was undone */
    protected Instant undoneAt;

    /** User who executed this command */
    protected User executedBy;

    /** @return the user who executed this command */
    public User getExecutedBy() { return this.executedBy; }
    
    /** @return when this command was executed */
    public Instant getExecutedAt() { return this.executedAt; }
    
    /** @return when this command was undone */
    public Instant getUndoneAt() { return this.undoneAt; }

    /**
     * Creates a command for the specified light component.
     * @param light the light component to operate on
     */
    public Command(LightComponent light) {
        this.light = light;
    }

    /** Performs the actual command execution logic */
    protected abstract void doExecute();
    
    /** Performs the actual command undo logic */
    protected abstract void doUndo();

    /**
     * Executes this command and records the timestamp.
     */
    public void execute() {
        this.executedAt = Instant.now();
        this.doExecute();
    };

    /**
     * Undoes this command and records the timestamp.
     */
    public void undo() {
        this.undoneAt = Instant.now();
        this.doUndo();
    }
    
    /**
     * Sets the user executing this command for tracking.
     * @param user the user executing the command
     * @return this command for method chaining
     */
    public Command by(User user) {
        this.executedBy = user;
        return this;
    }
}