package Interpreter.Commands.Fundation;

import Interpreter.Commands.Exceptions.CommandNotFoundException;
import Interpreter.Commands.util.*;
import Interpreter.Commands.util.Number;
import test.MyInterpreter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CommandTranslator {
    private static CommandTranslator instance;

    private Map<String, Class<? extends  Command<?>>> commands;


    private CommandTranslator() {
        this.commands = new HashMap<>();
        this.commands.put("return", ReturnCommand.class);
        this.commands.put("var", CreateVariableCommand.class);
    }

    public static CommandTranslator getInstance() {
        if(instance == null) {
            instance = new CommandTranslator();
        }

        return instance;
    }

    public Class<? extends Command<?>> translate(String commandName) throws CommandNotFoundException {
        if(this.commands.containsKey(commandName)){
            return commands.get(commandName);
        }
        else if(tryParseDouble(commandName)){
            return Number.class;
        }
        throw new CommandNotFoundException("Command: " + commandName + " is not a valid command");
    }

    public Collection<Class<? extends Command<?>>> allCommands() {
        return commands.values();
    }

    public void addCommand(String key, Class<? extends Command<?>> cmd) {
        this.commands.put(key, cmd);
    }

    private boolean tryParseDouble(String s) {
        try{
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
