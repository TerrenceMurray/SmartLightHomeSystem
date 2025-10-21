package com.terrencemurray.app.user;
// 816038951

import java.util.ArrayList;
import java.util.Stack;

import com.terrencemurray.app.commands.Command;
import com.terrencemurray.app.notifications.NotificationManager;
import com.terrencemurray.app.utility.SetItem;
import com.terrencemurray.app.utility.observer.Observer;

/**
 * Represents a user in the smart home lighting system.
 * Implements Observer pattern to receive notifications and manages command execution.
 * 
 * @author Terrence Murray
 * @version 1.0
 */
public class User implements Observer, SetItem {
    /** Counter for generating unique user IDs */
    private static int idCounter = 1;
    
    /** Unique user ID */
    private int id;
    
    /** User's name */
    private String name;
    
    /** User's mobile display */
    private MobileAppDisplay display;
    
    /** User's remote control */
    private SmartRemoteControl remote;
    
    /** Stack of executed commands for undo functionality */
    private Stack<Command> commandHistory;
    
    /** Log of all command activities */
    private ArrayList<String> commandLog;

    /**
     * Creates a new user with the specified name.
     * @param name the user's name
     */
    public User(String name) {
        this.id = idCounter++;
        this.name = name;
        this.display = new MobileAppDisplay(this.getName());
        this.commandLog = new ArrayList<>();
        this.commandHistory = new Stack<>();
        this.remote = new SmartRemoteControl();
    }

    /** @return the user's mobile display */
    public MobileAppDisplay getDisplay() { return this.display; }
    
    /** @return the user's name */
    public String getName() { return this.name; }
    
    /** @return the user's unique ID */
    public int getId() { return this.id; }
    
    /** @return the user's remote control */
    public SmartRemoteControl getRemote() { return this.remote; }
    
    /** @return formatted string of all command activities */
    public String getCommandLog() {
        return String.join("\n", this.commandLog);
    }
    
    /**
     * Receives notifications from observed subjects.
     * @param message the notification message
     */
    @Override
    public void update(String message) {
        NotificationManager.Instance().pushNotification(message + " by " + this.name);
    }

    /** @return hash code based on user ID */
    @Override
    public int hashCode() {
        return this.id;
    }

    /**
     * Compares users for equality based on ID.
     * @param o object to compare with
     * @return true if users have same ID
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof User == false)
            throw new IllegalArgumentException("Argument must be of type User");
        
        User u = (User) o;
        return u.id == this.id;
    } 

    /**
     * Executes a command through the remote control and logs the action.
     * @param command the command to execute
     */
    public void clickCommandButton(Command command) {
        final Command taggedCommand = command.by(this);
        this.remote.setCommand(taggedCommand);
        this.remote.pressButton();
        this.commandHistory.push(taggedCommand);
        this.commandLog.add("Executed\t" + command.toString());
    }

    /**
     * Undoes the last executed command and logs the action.
     */
    public void clickUndoButton() {
        if (!this.commandHistory.isEmpty()) {
            Command lastCommand = this.commandHistory.pop();
            this.remote.setCommand(lastCommand);
            this.remote.pressUndo();
            this.commandLog.add("Undid\t\t" + lastCommand.toString());
        } else {
            System.out.println("No commands to undo for user " + this.name);
        }
    }
}
