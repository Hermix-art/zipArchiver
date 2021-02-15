package app.commands;

import app.ConsoleManager;
import app.ZipFileManager;
import app.exceptions.PathIsNotFoundException;

import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleManager.writeMessage("Extracting the files");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleManager.writeMessage("Where the archive files should be extracted?");

            zipFileManager.extractAll(Paths.get(ConsoleManager.readString()));

            ConsoleManager.writeMessage("Extraction completed");
        } catch (PathIsNotFoundException e) {
            ConsoleManager.writeMessage("Path doesn't exist");
        }
    }
}
