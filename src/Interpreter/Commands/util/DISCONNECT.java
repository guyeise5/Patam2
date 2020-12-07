package Interpreter.Commands.util;

import Interpreter.Commands.Exceptions.*;
import Interpreter.Commands.Fundation.Command;

import java.lang.reflect.InvocationTargetException;

public class DISCONNECT extends Command<Void> {
    @Override
    public Void execute() throws CommandNotFoundException, InstantiationException, InvocationTargetException, NoSuchMethodException, InvalidArgumentsException, IllegalAccessException, InterpreterException, InvalidConditionFormatException, NoCommandsLeftException, CalculateException {
        OPENDATASERVER.shouldRun.set(false);
        return null;
    }
}
