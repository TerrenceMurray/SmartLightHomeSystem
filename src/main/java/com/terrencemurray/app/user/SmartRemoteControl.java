package com.terrencemurray.app.user;
// 816038951

import com.terrencemurray.app.commands.Command;

/**
 * Smart remote control for executing light commands.
 * Implements the Command Pattern by storing and executing commands.
 * 
 * @author Terrence Murray
 * @version 1.0
 */
public class SmartRemoteControl {
    /** Currently loaded command */
    private Command command;

    /**
     * Sets the command to be executed.
     * @param command the command to load into the remote
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Executes the currently loaded command.
     */
    public void pressButton() {
        if (command != null) {
            command.execute();
        } else {
            System.out.println("No command set.");
        }
    }

    /**
     * Undoes the currently loaded command.
     */
    public void pressUndo() {
        if (command != null) {
            command.undo();
        } else {
            System.out.println("No command set.");
        }
    }
}
