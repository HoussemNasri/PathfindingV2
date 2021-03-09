package tech.houssemnasri.impl.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import tech.houssemnasri.api.command.ICommand;

public class CommandGroup {
    protected Stack<ICommand> commands = new Stack<>();

    public CommandGroup(List<ICommand> commands) {
        this.commands.addAll(commands);
    }

    public void push(ICommand command) {
        commands.push(command);
    }

    public ICommand pop() {
        return commands.pop();
    }
}
