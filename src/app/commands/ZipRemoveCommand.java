package app.commands;

import app.ConsoleManager;
import app.ZipFileManager;
import app.exceptions.PathIsNotFoundException;

import java.nio.file.Paths;

public class ZipRemoveCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleManager.writeMessage("Removing the file");
            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleManager.writeMessage("Which file is to be removed");

            zipFileManager.removeFile(Paths.get(ConsoleManager.readString()));

        } catch (PathIsNotFoundException e) {
            ConsoleManager.writeMessage("Path doesn't exist");
        }
    }
}
