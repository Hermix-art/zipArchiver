package app.commands;

import app.ConsoleManager;

public class ZipExitCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleManager.writeMessage("Have a nice day");
    }
}
