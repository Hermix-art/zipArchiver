package app;

import app.exceptions.WrongZipFileException;

import java.io.IOException;

public class Archiver {
    public static void main(String[] args) {

        Operation operation = null;
        do {
            try {
                operation = getOperationFromUser();
                CommandExecutor.executeCommand(operation);
            } catch (WrongZipFileException e) {
                ConsoleManager.writeMessage("Wrong path to zip file");
            } catch (Exception e ){
                ConsoleManager.writeMessage("Unexpected exception");
            }

        } while (operation != Operation.EXIT);

    }

    public static Operation getOperationFromUser()throws IOException {
        ConsoleManager.writeMessage("Hello, please choose the operation");
        ConsoleManager.writeMessage(String.format("\t%s CREATE THE ARCHIVE", Operation.CREATE.ordinal()));
        ConsoleManager.writeMessage(String.format("\t%s ADD TO ARCHIVE", Operation.ADD.ordinal()));
        ConsoleManager.writeMessage(String.format("\t%s REMOVE FROM ARCHIVE", Operation.REMOVE.ordinal()));
        ConsoleManager.writeMessage(String.format("\t%s EXTRACT THE ARCHIVE", Operation.EXTRACT.ordinal()));
        ConsoleManager.writeMessage(String.format("\t%s LOOK THROUGH THE ARCHIVE CONTENT", Operation.CONTENT.ordinal()));
        ConsoleManager.writeMessage(String.format("\t%s EXIT THE PROGRAM", Operation.EXIT.ordinal()));
        return Operation.values()[ConsoleManager.readInt()];
    }

}
