package app.commands;

import app.ConsoleManager;
import app.FileProperties;
import app.ZipFileManager;
import app.exceptions.PathIsNotFoundException;

import java.util.List;

public class ZipContentCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleManager.writeMessage("Looking through the archive content");
            ZipFileManager zipFileManager = getZipFileManager();
            List<FileProperties> pathList = zipFileManager.getFilesList();
            ConsoleManager.writeMessage("Archive content: ");
            for (FileProperties p : pathList) {
                ConsoleManager.writeMessage(p.toString());
            }

        } catch (PathIsNotFoundException e) {
            ConsoleManager.writeMessage("Path doesn't exist");
        }

    }
}
