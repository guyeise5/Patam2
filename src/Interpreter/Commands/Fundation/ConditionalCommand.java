package Interpreter.Commands.Fundation;

import Interpreter.Commands.Exceptions.InvalidArgumentsException;
import Interpreter.Commands.Exceptions.InvalidConditionFormatException;
import jdk.jshell.spi.ExecutionControl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class ConditionalCommand<T> extends Command<T> {

    private List<Command<T>> commands;
    private Condition condition;

    public List<Command<T>> getCommands() {
        return commands;
    }

    public void setCommands(List<Command<T>> commands) {
        this.commands = commands;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public void setArgs(String... args) throws InvalidArgumentsException {
        super.setArgs(args);
        List<String> tmpArgs = Arrays.asList(args);
        tmpArgs.remove(0);
        Optional<String> opt_arguments = tmpArgs.stream().reduce((x,y)->x+y);
        String arguments = null;
        if(opt_arguments.isEmpty()) {
            throw new InvalidArgumentsException("Conditional command syntax error");
        }

        arguments=opt_arguments.get();

        // TODO: 30/11/2020 Finish this implementations

    }


}
