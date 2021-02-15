package app;

import app.exceptions.PathIsNotFoundException;
import app.exceptions.WrongZipFileException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
    private final Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception {
        Path parentDir = zipFile.getParent();
        if (Files.notExists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(source))) {

            if (Files.isDirectory(source)) {
                FileManager fileManager = new FileManager(source);
                List<Path> pathsAvailable = fileManager.getAllRelativizedPaths();
                for (Path path : pathsAvailable) {
                    addZipEntry(zipOutputStream, source, path);
                }
            } else if (Files.isRegularFile(source)) {
                addZipEntry(zipOutputStream, source.getParent(), source.getFileName());
            } else {
                throw new PathIsNotFoundException();
            }


        }
    }

    private void addZipEntry(ZipOutputStream outputStream, Path parentPath, Path filePath) throws Exception {
        Path fullPath = parentPath.resolve(filePath);
        try (InputStream inputStream = Files.newInputStream(fullPath)) {
            ZipEntry entry = new ZipEntry(filePath.toString());
            outputStream.putNextEntry(entry);

            copyData(outputStream, inputStream);

            outputStream.closeEntry();
        }
    }

    private void copyData(OutputStream zipOutputStream, InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8 * 1024];
        int a;
        while ((a = inputStream.read(bArr)) > 0) {
            zipOutputStream.write(bArr, 0, a);
        }
    }

    public void extractAll(Path source) throws Exception {
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }
        try (ZipInputStream inputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            if (Files.notExists(source)) {
                Files.createDirectories(source);
            }

            ZipEntry entry = inputStream.getNextEntry();

            while (entry != null) {
                String fileName = entry.getName();
                Path finalFilePath = source.resolve(fileName);

                if (Files.notExists(finalFilePath.getParent())) {
                    Files.createDirectories(finalFilePath.getParent());
                }
                try (OutputStream outputStream = Files.newOutputStream(finalFilePath)) {
                    copyData(outputStream, inputStream);
                }

                entry = inputStream.getNextEntry();
            }
        }

    }


    public void removeFile(Path path) throws Exception {
        removeFiles(Collections.singletonList(path));
    }

    private void removeFiles(List<Path> pathList) throws Exception {
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }

        Path tempFile = Files.createTempFile(null, null);

        try (ZipInputStream inputStream = new ZipInputStream(Files.newInputStream(zipFile));
             ZipOutputStream outputStream = new ZipOutputStream(Files.newOutputStream(tempFile))) {

            ZipEntry entry = inputStream.getNextEntry();

            while (entry != null) {

                Path archievedFile = Paths.get(entry.getName());

                if (!pathList.contains(archievedFile)) {
                    outputStream.putNextEntry(new ZipEntry(entry.getName()));

                    copyData(outputStream, inputStream);

                    outputStream.closeEntry();
                    inputStream.closeEntry();

                } else {
                    ConsoleManager.writeMessage(String.format("File %s was removed form archieve", archievedFile.toString()));
                }
                entry = inputStream.getNextEntry();
            }

        }
        Files.move(tempFile, zipFile, StandardCopyOption.REPLACE_EXISTING);

    }


    public void addFile(Path sourceFile) throws Exception {
        addFiles(Collections.singletonList(sourceFile));
    }
    public void addFiles(List<Path> pathList) throws Exception {
        if(!Files.isRegularFile(zipFile)){
            throw new WrongZipFileException();
        }

        List<Path> listOfArchivedFiles = new ArrayList<>();
        Path tempFile = Files.createTempFile(null,null);

        try(ZipInputStream inputStream = new ZipInputStream(Files.newInputStream(zipFile));
        ZipOutputStream outputStream = new ZipOutputStream(Files.newOutputStream(tempFile))){

            ZipEntry entry = inputStream.getNextEntry();
            while(entry != null){
                String entryName = entry.getName();
                listOfArchivedFiles.add(Paths.get(entryName));

                outputStream.putNextEntry(new ZipEntry(entryName));
                copyData(outputStream,inputStream);
                outputStream.closeEntry();
                inputStream.closeEntry();

                entry = inputStream.getNextEntry();
            }

            for(Path path : pathList){
                if(Files.isRegularFile(path)){
                    if(listOfArchivedFiles.contains(path.getFileName())){
                        ConsoleManager.writeMessage(String.format("Archive already contains the file %s", path.getFileName()));
                    }else{
                        addZipEntry(outputStream, path.getParent(), path.getFileName());
                        ConsoleManager.writeMessage(String.format("File %s was added successfully", path.getFileName()));
                    }

                }else throw new PathIsNotFoundException();
            }

        }
        Files.move(tempFile,zipFile,StandardCopyOption.REPLACE_EXISTING);
    }


}
