package app.commands;

import app.ConsoleManager;
import app.FileProperties;
import app.ZipFileManager;
import app.exceptions.PathIsNotFoundException;

import java.nio.file.Paths;
import java.util.List;

public class ZipCreateCommand extends ZipCommand{
    @Override
    public void execute() throws Exception {
        try {
           ConsoleManager.writeMessage("Zip creation");
           ZipFileManager zipFileManager = getZipFileManager();
           ConsoleManager.writeMessage("Please provide the source file, which is to be archived");
           zipFileManager.createZip(Paths.get(ConsoleManager.readString()));
        } catch (PathIsNotFoundException e) {
            ConsoleManager.writeMessage("Path doesn't exist");
        }
    }
}
