package app;

import app.commands.*;


import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private final static Map<Operation, Command> KNOWN_COMMANDS = new HashMap<>();

    static {
        KNOWN_COMMANDS.put(Operation.ADD, new ZipAddCommand());
        KNOWN_COMMANDS.put(Operation.CREATE, new ZipCreateCommand());
        KNOWN_COMMANDS.put(Operation.CONTENT, new ZipContentCommand());
        KNOWN_COMMANDS.put(Operation.REMOVE, new ZipRemoveCommand());
        KNOWN_COMMANDS.put(Operation.EXTRACT, new ZipExtractCommand());
        KNOWN_COMMANDS.put(Operation.EXIT, new ZipExitCommand());

    }

    public static void executeCommand(Operation operation) throws Exception {
        KNOWN_COMMANDS.get(operation).execute();
    }
}
