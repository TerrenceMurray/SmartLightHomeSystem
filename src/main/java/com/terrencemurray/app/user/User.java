package com.terrencemurray.app.user;

import java.util.ArrayList;
import java.util.Stack;

import com.terrencemurray.app.commands.Command;
import com.terrencemurray.app.notifications.NotificationManager;
import com.terrencemurray.app.utility.observer.Observer;

public class User implements Observer {
    private static int idCounter = 1;
    private int id;
    private String name;
    private MobileAppDisplay display;
    private SmartRemoteControl remote;
    private Stack<Command> commandHistory;
    private ArrayList<String> commandLog;

    public User(String name) {
        this.id = idCounter++;
        this.name = name;
        this.display = new MobileAppDisplay(this.getName());
        this.commandLog = new ArrayList<>();
        this.commandHistory = new Stack<>();
        this.remote = new SmartRemoteControl();
    }

    public MobileAppDisplay getDisplay() { return this.display; }
    public String getName() { return this.name; }
    public int getId() { return this.id; }
    public SmartRemoteControl getRemote() { return this.remote; }
    public String getCommandLog() {
        return String.join("\n", this.commandLog);
    }
    
    @Override
    public void update(String message) {
        NotificationManager.Instance().pushNotification(message + " by " + this.name);
    }

    public void clickCommandButton(Command command) {
        this.remote.setCommand(command);
        this.remote.pressButton();
        this.commandHistory.push(command);
        this.commandLog.add("Executed: " + command.toString());
    }

    public void clickUndoButton() {
        if (!this.commandHistory.isEmpty()) {
            Command lastCommand = this.commandHistory.pop();
            this.remote.setCommand(lastCommand);
            this.remote.pressUndo();
            this.commandLog.add("Undid: " + lastCommand.toString());
        } else {
            System.out.println("No commands to undo for user " + this.name);
        }
    }

    
}
