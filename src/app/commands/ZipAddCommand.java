package app.commands;

import app.ConsoleManager;
import app.ZipFileManager;
import app.exceptions.PathIsNotFoundException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipAddCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        try {
            ConsoleManager.writeMessage("Adding new file to archive");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleManager.writeMessage("Please provide path to file, which is to be added");
            Path path = Paths.get(ConsoleManager.readString());

            zipFileManager.addFile(path);
        } catch (PathIsNotFoundException e) {
            ConsoleManager.writeMessage("Path doesn't exist");
        }
    }
}
