package Interpreter.Commands.Fundation;

import Interpreter.Commands.Exceptions.CommandNotFoundException;
import Interpreter.Commands.Exceptions.InvalidArgumentsException;

import java.lang.reflect.InvocationTargetException;

public abstract class Command<T> {

    private String commandName;

    public Command() {
        super();
    }

    public Command(String commandName) {
        setCommandName(commandName);
    }

    private String[] args;

    public abstract  T execute();
    public String getName() {
        return this.commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public static Command parse(String s) throws InvalidArgumentsException, CommandNotFoundException {
        String[] args = s.split(" ");
        try {
            Command ret = CommandTranslator.getInstance().translate(args[0]).getDeclaredConstructor().newInstance();
            ret.setArgs(args);
            return ret;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String... args) throws InvalidArgumentsException {
        this.args = args;
    }

}
