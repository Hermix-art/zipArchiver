package app.commands;

import app.ConsoleManager;
import app.ZipFileManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ZipCommand implements Command {

    public ZipFileManager getZipFileManager() throws IOException {
        ConsoleManager.writeMessage("Please provide path to archive");
        Path archivePath = Paths.get(ConsoleManager.readString());
        return new ZipFileManager(archivePath);
    }
}
