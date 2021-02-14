package app;

import app.exceptions.PathIsNotFoundException;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final Path sourceFile;
    private final List<Path> allRelativizedPaths;

    public List<Path> getAllRelativizedPaths() {
        return allRelativizedPaths;
    }

    public FileManager(String source)throws IOException {
        sourceFile = Paths.get(source);
        allRelativizedPaths = new ArrayList<>();

        extractPaths(sourceFile);
    }

    private void extractPaths(Path sourcePath) throws IOException {
        if (Files.isRegularFile(sourcePath)) {
            Path newPath = sourceFile.relativize(sourcePath);
            allRelativizedPaths.add(newPath);
        }
        if (Files.isDirectory(sourcePath)) {
            try(DirectoryStream<Path> pathStream = Files.newDirectoryStream(sourcePath)){
                for(Path path : pathStream){
                    extractPaths(path);
                }
            }
        }

    }
}
