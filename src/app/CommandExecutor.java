package app;

import app.commands.Command;
import app.commands.ZipAddCommand;


import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private final static Map<Operation, Command> KNOWN_COMMANDS = new HashMap<>();

    static {
        KNOWN_COMMANDS.put(Operation.ADD, new ZipAddCommand());
    }

    public static void executeCommand(Operation operation) throws Exception {
        KNOWN_COMMANDS.get(operation).execute();
    }
}
