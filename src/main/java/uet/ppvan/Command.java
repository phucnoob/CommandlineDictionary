package uet.ppvan;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Command {
    EXIT(0),
    ADD(1),
    DELETE(2),
    UPDATE(3),
    INSERT_FROM_COMMANDLINE(4),
    INSERT_FROM_FILE(5),
    EXPORT_TO_FILE(6),
    SEARCH(7),
    SEARCH_INTERACTIVE(8),
    SHOW_ALL(9);
    
    private int value;
    private Command(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    private static final Map<Integer, Command> reverseLookup = Arrays.stream(Command.values())
            .collect(Collectors.toMap(Command::getValue, Function.identity()));
    
    public static Command from(int value) {
        return reverseLookup.getOrDefault(value, EXIT);
    }
    
}
