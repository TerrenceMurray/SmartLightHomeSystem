package com.terrencemurray.app.user;
// 816038951

import com.terrencemurray.app.commands.Command;

public class SmartRemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        if (command != null) {
            command.execute();
        } else {
            System.out.println("No command set.");
        }
    }

    public void pressUndo() {
        if (command != null) {
            command.undo();
        } else {
            System.out.println("No command set.");
        }
    }
}
