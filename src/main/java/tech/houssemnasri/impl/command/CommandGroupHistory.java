package tech.houssemnasri.impl.command;

import java.util.List;
import java.util.Stack;

public class CommandGroupHistory {
    protected Stack<CommandGroup> commandGroups = new Stack<>();

    public CommandGroupHistory(List<CommandGroup> commandGroups) {
        this.commandGroups.addAll(commandGroups);
    }

    public void push(CommandGroup commandGroup) {
        commandGroups.push(commandGroup);
    }

    public CommandGroup pop() {
        return commandGroups.pop();
    }
}
